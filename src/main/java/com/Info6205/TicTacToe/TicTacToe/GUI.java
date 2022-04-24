package com.Info6205.TicTacToe.TicTacToe;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    private JPanel MainPanel;
    private JButton PVP;
    private JButton PVE;
    private JLabel TicTacToe;
    private JButton EXIT;

    public GUI(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        this.pack();
        PVP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                GameInterfacePvP.main();
            }
        });
        PVE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                GameInterfacePvE.main();
            }
        });
        EXIT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e3) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new GUI("TicTacToe");
        frame.setVisible(true);
    }
}
