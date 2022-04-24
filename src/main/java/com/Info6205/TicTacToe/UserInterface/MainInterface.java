package com.Info6205.TicTacToe.UserInterface;

import com.Info6205.TicTacToe.ClientAndServer.TicTacToeClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainInterface extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private CardLayout card;
    private JPanel mainPanel, cardPane;

    public MainInterface() {
        mainPanel = new JPanel();
        cardPane = new JPanel();
        card = new CardLayout();
        addCarePanel();
        setWindowProperties();
        addButton();
        this.add(cardPane);
    }

    private void addCarePanel() {
        cardPane.setLayout(card);
        cardPane.add(mainPanel, "main");
        cardPane.add(new TicTacToeClient("pve" ,"11"), "MenacePlay");
    }

    /**
     * Set the size, title, visibility etc...
     */
    private void setWindowProperties() {
        setResizable(false);
        pack();
        setTitle("Ava's Tic Tac Toe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setSize(WIDTH,HEIGHT);
    }

    private void addButton() {
        addPlayButton();
        addTrainButton();
        addOnlineButton();
    }

    private void addPlayButton() {
        JButton playButton = new JButton("Play With Menace");
        playButton.addActionListener(e -> card.show(cardPane,"MenacePlay"));
        mainPanel.add(playButton);
    }

    private void addTrainButton() {
        JButton trainButton = new JButton("Train Menace");
        trainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement logging and auto training here
                JOptionPane pane = new JOptionPane();
                JOptionPane.showMessageDialog( pane, "Training is over, Trainning result is ...");
            }
        });
        mainPanel.add(trainButton);
    }

    private void addOnlineButton() {
        JButton onlineButton = new JButton("Play Online");
        onlineButton.addActionListener(e -> {
            cardPane.add(new TicTacToeClient("pvp" ,"11"), "OnlinePlay");
            card.show(cardPane,"OnlinePlay");
        });
        mainPanel.add(onlineButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
