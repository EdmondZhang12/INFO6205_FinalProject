package com.Info6205.TicTacToe.ClientAndServer;

import com.Info6205.TicTacToe.TicTacToe.GameInterface;

import javax.swing.*;

public class Client {
    String mode;

    /**
     *
     * @param playMode 暂时放在这，Mode不应该为Client输入参数
     */
    public Client(String playMode) {
        this.mode = playMode;
        if (mode.equals("pvp")) {
            System.out.println("Game Mode: Player vs. Player");
            SwingUtilities.invokeLater(() -> new GameInterface(GameInterface.Mode.PvP));
        } else if (mode.equals("pve")) {
            System.out.println("Game Mode: Player vs. AI");
            SwingUtilities.invokeLater(() -> new GameInterface(GameInterface.Mode.PvE));
        }
    }

    public static void main(String[] args) {
        Client pve = new Client("pve");
    }


}
