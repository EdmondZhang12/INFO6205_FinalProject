package com.Info6205.TicTacToe.ClientAndServer;

import com.Info6205.TicTacToe.TicTacToe.Game;

import java.net.ServerSocket;

public class TicTacToeServer {

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(12345);
        System.out.println("Tic Tac Toe Server is Running");
        try {
            while (true) {
                Game game = new Game();
                Game.Player playerX = game.new Player(listener.accept(), 'X');
                Game.Player playerO = game.new Player(listener.accept(), 'O');
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.currentPlayer = playerX;
                playerX.start();
                playerO.start();
            }
        } finally {
            listener.close();
        }
    }
}
