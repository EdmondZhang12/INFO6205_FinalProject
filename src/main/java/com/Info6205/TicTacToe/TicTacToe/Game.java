package com.Info6205.TicTacToe.TicTacToe;


import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Game {
    // a board of 9 squares
    private Player[] board = {
            null, null, null,
            null, null, null,
            null, null, null};

    //current player
    public Player currentPlayer;

    // winner
    public boolean hasWinner() {
        return
                (board[0] != null && board[0] == board[1] && board[0] == board[2])
                        ||(board[3] != null && board[3] == board[4] && board[3] == board[5])
                        ||(board[6] != null && board[6] == board[7] && board[6] == board[8])
                        ||(board[0] != null && board[0] == board[3] && board[0] == board[6])
                        ||(board[1] != null && board[1] == board[4] && board[1] == board[7])
                        ||(board[2] != null && board[2] == board[5] && board[2] == board[8])
                        ||(board[0] != null && board[0] == board[4] && board[0] == board[8])
                        ||(board[2] != null && board[2] == board[4] && board[2] == board[6]);
    }

    // no empty squares
    public boolean boardFilledUp() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == null) {
                return false;
            }
        }
        return true;
    }

    // thread when player tries a move
    public synchronized boolean legalMove(int location, Player player) {
        if (player == currentPlayer && board[location] == null) {
            board[location] = currentPlayer;
            currentPlayer = currentPlayer.opponent;
            currentPlayer.otherPlayerMoved(location);
            return true;
        }
        return false;
    }

    public class Player extends Thread {
        char mark;
        Player opponent;
        Socket socket;
        private Scanner input; // input from client
        private Formatter output; // output to client
        // thread handler to initialize stream fields
        public Player(Socket socket, char mark) {
            this.socket = socket;
            this.mark = mark;
            try {
                input = new Scanner(socket.getInputStream());
                output = new Formatter(socket.getOutputStream());
                output.format("%s\n", mark); // send player's mark
                output.flush(); // flush output
                if (mark == 'X') {
                    output.format("%s\n%s", "Player X connected", "Waiting for another player\n");
                    output.flush(); // flush output
                    //output.println("MESSAGE Your move");
                }
            } catch (IOException e) {
                System.out.println("Player died: " + e);
            }
        }
        //Accepts notification of who the opponent is.
        public void setOpponent(Player opponent) {
            this.opponent = opponent;
        }


        //Handles the otherPlayerMoved message.
        public void otherPlayerMoved(int location) {
            output.format("Opponent moved\n");
            output.format("%d\n", location); // send location of move
            output.flush(); // flush output
            output.format(hasWinner() ? "DEFEAT\n" : boardFilledUp() ? "TIE\n" : "");
            output.flush();
        }

        public void run() {
            try {
                // Tell the first player that it is his/her turn.
                if (mark == 'X') {
                    output.format("Other player connected. Your move.\n");
                    output.flush(); // flush output
                }else if (mark == 'O'){
                    output.format("Player O connected, please wait\n");
                    output.flush(); // flush output
                }

                // Repeatedly get commands from the client and process them.
                while (true) {
                    int location = 0; // initialize move location
                    if (input.hasNext()) {
                        // get move location
                        location = input.nextInt();
                    }
                    if (legalMove(location, this)) {
                        output.format("Valid move.\n"); // notify client
                        output.flush(); // flush output
                        output.format(hasWinner() ? "VICTORY\n" : boardFilledUp() ? "TIE\n" : "");
                        output.flush();
                    } else {
                        output.format("Invalid move, try again\n");
                        output.flush(); // flush output
                    }
                }
            } finally {
                try {socket.close();} catch (IOException e) {}
            }
        }


    }
}
