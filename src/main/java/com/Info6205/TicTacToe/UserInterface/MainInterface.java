package com.Info6205.TicTacToe.UserInterface;

import com.Info6205.TicTacToe.ClientAndServer.TicTacToeClient;
import com.Info6205.TicTacToe.TicTacToe.Training;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainInterface extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private CardLayout card;
    private JPanel mainPanel, cardPane;
    private Training training;

    public MainInterface() {
        mainPanel = new JPanel();
        cardPane = new JPanel();
        card = new CardLayout();
        training = new Training();
        addCarePanel();
        setWindowProperties();
        addButton();
        this.add(cardPane);
    }

    private void addCarePanel() {
        cardPane.setLayout(card);
        cardPane.add(mainPanel, "main");
        cardPane.add(new TicTacToeClient("pve" ,"11", this, training), "MenacePlay");
        cardPane.add(new OnlineMenaceInterface(this),"Challenge");
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
        addAcceptChallengeBtn();
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
                training = training.run();
                JOptionPane pane = new JOptionPane();
                JOptionPane.showMessageDialog( pane, "Training is over");
            }
        });
        mainPanel.add(trainButton);
    }

    private void addOnlineButton() {
        JButton onlineButton = new JButton("Play Online");
        onlineButton.addActionListener(e -> {
            cardPane.add(new TicTacToeClient("pvp" ,"11", this, training), "OnlinePlay");
            card.show(cardPane,"OnlinePlay");
        });
        mainPanel.add(onlineButton);
    }

    private void addAcceptChallengeBtn() {
        JButton acceptChallenge = new JButton("Challenge");
        acceptChallenge.addActionListener(e -> {
            card.show(cardPane,"Challenge");
        });
        mainPanel.add(acceptChallenge);
    }

    public void returnMainPanel () {
        card.show(cardPane,"main");
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
