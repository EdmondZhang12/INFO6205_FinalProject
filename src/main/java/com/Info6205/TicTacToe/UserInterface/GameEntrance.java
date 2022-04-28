package com.Info6205.TicTacToe.UserInterface;

import javax.swing.*;
/**
 *  @Description
 *  Game entrance
 *  @author Shijie Zhang
 */
public class GameEntrance {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainInterface());
    }
}
