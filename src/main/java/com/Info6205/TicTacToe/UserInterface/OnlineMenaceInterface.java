package com.Info6205.TicTacToe.UserInterface;

import javax.swing.*;
import java.awt.*;

public class OnlineMenaceInterface extends JPanel  {
    JComboBox algorCombo;
    MainInterface frame;
    JButton startBtn, returnBtn;

    String[] algorArray = { "学生", "军人", "工人" };

    public OnlineMenaceInterface (MainInterface mi) {
        this.frame = mi;
        setLayout(null);
        addAlgorCombo();
        addReturnBtn();
        addStartBtn();
    }

    private void addAlgorCombo() {
        algorCombo = new JComboBox();
        for(String algor : algorArray) {
            algorCombo.addItem(algor);
        }

        algorCombo.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        algorCombo.setBounds(130, 330, 280, 25);
        this.add(algorCombo);
    }

    private void addReturnBtn() {
        JButton returnBtn = new JButton("Back <");
        returnBtn.addActionListener(e -> {
            frame.returnMainPanel();
        });
        this.add(returnBtn);
    }

    private void addStartBtn() {
        JButton startBtn = new JButton("Accept Challenge");
        startBtn.addActionListener(e -> {
            frame.returnMainPanel();
        });
        this.add(startBtn);
    }










}
