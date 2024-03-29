package com.Info6205.TicTacToe.UserInterface;

import com.Info6205.TicTacToe.ClientAndServer.TicTacToeClient;
import com.Info6205.TicTacToe.TicTacToe.Training;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *  @Description
 *  The interface of initial game interface
 *  @author Shijie Zhang and Yucong Liu
 */
public class MainInterface extends JFrame implements ActionListener {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 770;

    private CardLayout card;
    private JPanel mainPanel, cardPane;
    private Training training;

    /**
     * construct the mainInterface
     */
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

    /**
     * Add careLayout
     * then can switch JPanel by card
     */
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

    /**
     * Add the Buttons, like playBtn, TrainBtn etc...
     */
    private void addButton() {
        addLabel1();
        addPlayButton();
        addLabel2();
        addTrainButton();
        addLabel3();
        addOnlineButton();
        addLabel4();
        addExitButton();
    }

    private void addLabel1() {
        JLabel label1 = new JLabel("                                                                                                    ");
        JLabel label2 = new JLabel("                                                                                                    ");
        JLabel label3 = new JLabel("                                                                                                    ");
        JLabel label4 = new JLabel("                                                                                                     TicTacToe                                                                                                     ");
        Font font = new Font(" ", Font.PLAIN, 30);
        label4.setFont(font);
        JLabel label5 = new JLabel("                                                                                                    ");
        JLabel label6 = new JLabel("                                                                                                    ");
        JLabel label7 = new JLabel("                                                                                                    ");
        mainPanel.add(label1);
        mainPanel.add(label2);
        mainPanel.add(label3);
        mainPanel.add(label4);
        mainPanel.add(label5);
        mainPanel.add(label6);
        mainPanel.add(label7);
    }

    private void addLabel2() {
        JLabel label1 = new JLabel("                                                                                                    ");
        JLabel label2 = new JLabel("                                                                                                    ");
        JLabel label3 = new JLabel("                                                                                                    ");
        JLabel label4 = new JLabel("                                                                                                    ");
        mainPanel.add(label1);
        mainPanel.add(label2);
        mainPanel.add(label3);
        mainPanel.add(label4);
    }

    private void addLabel3() {
        JLabel label1 = new JLabel("                                                                                                     ");
        JLabel label2 = new JLabel("                                                                                                     ");
        JLabel label3 = new JLabel("                                                                                                     ");
        JLabel label4 = new JLabel("                                                                                                     ");
        mainPanel.add(label1);
        mainPanel.add(label2);
        mainPanel.add(label3);
        mainPanel.add(label4);
    }

    private void addLabel4() {
        JLabel label1 = new JLabel("                                                                                                     ");
        JLabel label2 = new JLabel("                                                                                                     ");
        JLabel label3 = new JLabel("                                                                                                     ");
        JLabel label4 = new JLabel("                                                                                                     ");
        mainPanel.add(label1);
        mainPanel.add(label2);
        mainPanel.add(label3);
        mainPanel.add(label4);
    }

    /**
     * Create the Button for playing with menace
     * and Add it to MainJPanel
     */
    private void addPlayButton() {
        JButton playButton = new JButton("Play With Menace");
        playButton.setBorder(BorderFactory.createRaisedBevelBorder());
        playButton.setPreferredSize(new Dimension(400,100));
        Font font = new Font(" ", Font.PLAIN, 30);
        playButton.setFont(font);
        playButton.addActionListener(e -> {
            int i = selectMarkOptionDialog();
            cardPane.add(new TicTacToeClient("pve" ,"11", this, training,i), "MenacePlay");
            card.show(cardPane,"MenacePlay");
        });
        mainPanel.add(playButton);
    }

    /**
     * Create the Button for making train to menace
     * and Add it to MainJPanel
     */
    private void addTrainButton() {
        JButton trainButton = new JButton("Train Menace");
        trainButton.setBorder(BorderFactory.createRaisedBevelBorder());
        trainButton.setPreferredSize(new Dimension(400,100));
        Font font = new Font(" ", Font.PLAIN, 30);
        trainButton.setFont(font);
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

    /**
     * Create the Button for playing online
     * and Add it to MainJPanel
     */
    private void addOnlineButton() {
        JButton onlineButton = new JButton("Play Online");
        onlineButton.setBorder(BorderFactory.createRaisedBevelBorder());
        onlineButton.setPreferredSize(new Dimension(400,100));
        Font font = new Font(" ", Font.PLAIN, 30);
        onlineButton.setFont(font);
        onlineButton.addActionListener(e -> {
            cardPane.add(new TicTacToeClient("pvp" ,"11", this, training, -1), "OnlinePlay");
            card.show(cardPane,"OnlinePlay");
        });
        mainPanel.add(onlineButton);
    }

    /**
     * Create the Button for quit the game
     * and Add it to MainJPanel
     */
    private void addExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setBorder(BorderFactory.createRaisedBevelBorder());
        exitButton.setPreferredSize(new Dimension(400,100));
        Font font = new Font(" ", Font.PLAIN, 30);
        exitButton.setFont(font);
        exitButton.addActionListener(e -> System.exit(0));
        mainPanel.add(exitButton);
    }


    /**
     * return to the initial game interface
     */
    public void returnMainPanel () {
        card.show(cardPane,"main");
    }

    /**
     * return the result for which mark gamer selected
     */
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
