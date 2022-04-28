package com.Info6205.TicTacToe.ClientAndServer;

import com.Info6205.TicTacToe.TicTacToe.Game;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *  @Description
 *  The server of online playing
 *  before you play online, have to start it
 *  @author Shijie Zhang
 */
public class TicTacToeServer {

    /**
     * Run the Server
     * @throws IOException
     */
    public void run() throws IOException {
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

    public static void main(String[] args) throws IOException {
        TicTacToeServer server = new TicTacToeServer();
        server.run();
    }
}
