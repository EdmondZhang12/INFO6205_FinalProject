package com.Info6205.TicTacToe.UserInterface;

import com.Info6205.TicTacToe.TicTacToe.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GameInterface extends JPanel {
    private BufferedImage imageBackground, imageX, imageO;
    private Board board;
    private Board.State[][] boardArray;

    public GameInterface() {
        board = new Board();
        boardArray = board.toArray();
        loadImages();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintTicTacToe((Graphics2D) g);
    }

    /**
     * The main painting method that paints everything.
     *
     * @param g the Graphics object that will perform the panting
     */
    public void paintTicTacToe(Graphics2D g) {
        setProperties(g);
        paintBoard(g);
        paintWinner(g);
    }

    /**
     * Set the rendering hints of the Graphics object.
     *
     * @param g the Graphics object to set the rendering hints on
     */
    public void setProperties(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(imageBackground, 0, 0, null);

        // The first time a string is drawn it tends to lag.
        // Drawing something trivial at the beginning loads the font up.
        // Better to lag at the beginning than during the middle of the game.
        g.drawString("", 0, 0);
    }

    /**
     * Paints the background image and the X's and O's.
     *
     * @param g the Graphics object that will perform the panting
     */
    private void paintBoard(Graphics2D g) {

        int offset = 20;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (boardArray[y][x] == Board.State.X) {
                    g.drawImage(imageX, offset + 190 * x, offset + 190 * y, null);
                } else if (boardArray[y][x] == Board.State.O) {
                    g.drawImage(imageO, offset + 190 * x, offset + 190 * y, null);
                }
            }
        }
    }

    /**
     * Paints who won to the screen.
     *
     * @param g the Graphics object that will perform the panting
     */
    private void paintWinner(Graphics2D g) {
        if (board.isGameOver()) {
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));

            String s;

            if (board.getWinner() == Board.State.Blank) {
                s = "Draw";
            } else {
                s = board.getWinner() + " Wins!";
            }

            g.drawString(s, 300 - getFontMetrics(g.getFont()).stringWidth(s) / 2, 315);

        }
    }

    /**
     * Load the image of the background and the images of the X and O
     */
    private void loadImages() {
        imageBackground = getImage("background");
        imageX = getImage("x");
        imageO = getImage("o");
    }

    /**
     * Helper method for grabbing the images from the disk.
     *
     * @param path the name of the image
     * @return the image that was grabbed
     */
    private static BufferedImage getImage(String path) {

        BufferedImage image;
        try {
            path = "./src/main/java/com/Info6205/TicTacToe/Img/" + path + ".png";
            File file = new File(path);
            image = ImageIO.read(file);
        } catch (IOException ex) {
            throw new RuntimeException("Image could not be loaded.");
        }
        return image;
    }

    public Board getBoard() {
        return this.board;
    }
}
