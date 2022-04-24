package com.Info6205.TicTacToe.ClientAndServer;

import javax.swing.*;

public class TicTacToeClientTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToeClient("pvp" ,"11"));
    }
}
