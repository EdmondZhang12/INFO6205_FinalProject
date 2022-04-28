package com.Info6205.TicTacToe.UserInterface;

import com.Info6205.TicTacToe.ClientAndServer.TicTacToeClient;
import com.Info6205.TicTacToe.TicTacToe.Training;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainInterface extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 770;

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
        playButton.addActionListener(e -> {
            int i = selectMarkOptionDialog();
            System.out.println(i);
            cardPane.add(new TicTacToeClient("pve" ,"11", this, training,i), "MenacePlay");
            card.show(cardPane,"MenacePlay");
        });
        mainPanel.add(playButton);
    }

    private void addTrainButton() {
        JButton trainButton = new JButton("Train Menace");
        trainButton.setBorder(BorderFactory.createRaisedBevelBorder());
        trainButton.setPreferredSize(new Dimension(400,170));
        setVisible(true);
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
        onlineButton.setBorder(BorderFactory.createRaisedBevelBorder());
        onlineButton.setPreferredSize(new Dimension(400,170));
        onlineButton.addActionListener(e -> {
            cardPane.add(new TicTacToeClient("pvp" ,"11", this, training, -1), "OnlinePlay");
            card.show(cardPane,"OnlinePlay");
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

    private int selectMarkOptionDialog () {
        Object options[] = {'X', 'O'};
        int i = JOptionPane.showOptionDialog(null, "Select your mark to play, you cannot change during the game",
                "Mark Selection",JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        return i;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
