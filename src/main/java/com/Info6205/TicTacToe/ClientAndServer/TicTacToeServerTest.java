package com.Info6205.TicTacToe.ClientAndServer;

import javax.swing.*;

public class TicTacToeServerTest {

    public static void main(String[] args) {
        TicTacToeServer application = new TicTacToeServer();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.execute();
    }

}
