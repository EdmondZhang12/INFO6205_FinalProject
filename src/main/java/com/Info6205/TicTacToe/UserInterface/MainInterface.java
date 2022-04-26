package com.Info6205.TicTacToe.UserInterface;

import com.Info6205.TicTacToe.ClientAndServer.TicTacToeClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainInterface extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 800;

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
        cardPane.add(new TicTacToeClient("pve" ,"11", this), "MenacePlay");
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
        addExitButton();
    }

    private void addPlayButton() {
        JButton playButton = new JButton("Play With Menace");
        playButton.setBorder(BorderFactory.createRaisedBevelBorder());
        playButton.setPreferredSize(new Dimension(400,170));
        playButton.addActionListener(e -> card.show(cardPane,"MenacePlay"));
        mainPanel.add(playButton);
    }

    private void addTrainButton() {
        JButton trainButton = new JButton("Train Menace");
        trainButton.setBorder(BorderFactory.createRaisedBevelBorder());
        trainButton.setPreferredSize(new Dimension(400,170));
        setVisible(true);
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
        onlineButton.setBorder(BorderFactory.createRaisedBevelBorder());
        onlineButton.setPreferredSize(new Dimension(400,170));
        onlineButton.addActionListener(e -> {
//            if(! netUtil.isLoclePortUsing(12345)) {
//                JOptionPane pane = new JOptionPane();
//                JOptionPane.showMessageDialog( pane, "Please open the server if you wanna play online");
//            } else {
                cardPane.add(new TicTacToeClient("pvp" ,"11", this), "OnlinePlay");
                card.show(cardPane,"OnlinePlay");
//            }
        });
        mainPanel.add(onlineButton);
    }

    private void addExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setBorder(BorderFactory.createRaisedBevelBorder());
        exitButton.setPreferredSize(new Dimension(400,170));
        exitButton.addActionListener(e -> System.exit(0));
        mainPanel.add(exitButton);
    }

    public void returnMainPanel () {
        card.show(cardPane,"main");
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
