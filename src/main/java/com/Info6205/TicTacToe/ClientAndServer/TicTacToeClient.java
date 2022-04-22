package com.Info6205.TicTacToe.ClientAndServer;

import com.Info6205.TicTacToe.ArtificialIntelligence.Algorithms;
import com.Info6205.TicTacToe.TicTacToe.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeClient extends JFrame implements Runnable{

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    /**
     * The distance away from the center of a cell that will register
     * as a click.
     */
    private static final int DISTANCE = 100;
    private final Board board;
    private final Panel panel;
    private BufferedImage imageBackground, imageX, imageO;
    public  Mode mode;
    private JTextArea displayArea; // JTextArea to display output
    private JTextField idField; // textfield to display player's mark

    // parameter for server
    private Socket connection; // connection to server
    private Scanner input; // input from server
    private Formatter output; // output to server
    private final String ticTacToeHost; // host name for server
    private String myMark; // this client's mark
    private boolean myTurn; // determines which client's turn it is
    private final String X_MARK = "X"; // mark for first client
    private final String O_MARK = "O"; // mark for second client
    /**
     * The center location of each of the cells is stored here.
     * Used for identifying which cell the player has clicked on.
     */
    private Point[] cells;

    /**
     * Construct the Window.
     *  @param playMode the game mode (Player vs. Player or Player vs. AI)
     * @param host
     */
    public TicTacToeClient(String playMode, String host) {
        myTurn = true;
        board = new Board();
        loadCells();
        panel = createPanel();
        setWindowProperties();
        loadImages();
        if(playMode.equals("pvp")) {
            // if playMode is PvP, run the thread
            this.mode = Mode.PvP;
            displayArea4pvp(panel);
            startClient();
        } else {
            this.mode = Mode.PvE;
        }
        // set name of server
        this.ticTacToeHost = host;
        // Set up the gameInterface
    }

    /**
     * Create a new display for pvp mode
     */
    private void displayArea4pvp(Panel panel) {
        // 下面显示内容
        displayArea = new JTextArea(4, 20); // set up JTextArea
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.PAGE_END);

        JPanel panel2 = new JPanel(); // set up panel to contain boardPanel
        panel2.add(panel, BorderLayout.CENTER); // add board panel
        add(panel, BorderLayout.CENTER); // add container panel
        idField = new JTextField(); // set up textfield
        idField.setEditable(false);
        add(idField, BorderLayout.NORTH);
    }

    /**
     * start the client thread
     */
    private void startClient() {
        // connect to server and get streams
        try {
            // make connection to server
            connection = new Socket(InetAddress.getByName(ticTacToeHost), 12345);

            // get streams for input and output
            input = new Scanner(connection.getInputStream());
            output = new Formatter(connection.getOutputStream());
        } catch (IOException ioException) {
            System.out.println(ioException.toString());
        }

        // create and start worker thread for this client
        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this); // execute client
    }

    /**
     * The Player’s run method controls the information that is sent to and
     * received from the client.
     */
    @Override
    public void run() {
        myMark = input.nextLine(); // get player's mark (X or O)
        SwingUtilities.invokeLater(() -> {
            // display player's mark
            idField.setText("You are player \"" + myMark + "\"");
        });

        myTurn = (myMark.equals(X_MARK)); // determine if client's turn

        // receive messages sent to client and output them
        while (true) {
            if (input.hasNextLine()) {
                processMessage(input.nextLine());
            }
        }
    }

    // process messages sent to the client
    private void processMessage(String message) {
        // valid move occurred
        switch (message) {
            case "Valid move.":
                displayMessage("Valid move, please wait.\n");
                break;
            case "Opponent moved":
                int location = input.nextInt(); // get move location
                input.nextLine(); // skip newline after int location
                // repaint panel
                board.move(location);
                panel.repaint();
                displayMessage("Opponent moved. Your turn.\n");
                myTurn = true; // now this client's turn
                break;
            case "DEFEAT":
            case "TIE":
            case "VICTORY":
                //  Game is over, display the results and stop game
                displayMessage(message + "\n"); // display the message
                myTurn = false;
                break;
            default:
                //  Game is over, display the results and stop game
                displayMessage(message + "\n"); // display the message
                break;

        }
    }

    // manipulate displayArea in event-dispatch thread
    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(() -> {
            displayArea.append(messageToDisplay); // updates output
        });
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

    /**
     * Load the locations of the center of each of the cells.
     */
    private void loadCells() {
        cells = new Point[9];

        cells[0] = new Point(109, 109);
        cells[1] = new Point(299, 109);
        cells[2] = new Point(489, 109);
        cells[3] = new Point(109, 299);
        cells[4] = new Point(299, 299);
        cells[5] = new Point(489, 299);
        cells[6] = new Point(109, 489);
        cells[7] = new Point(299, 489);
        cells[8] = new Point(489, 489);
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
    }

    /**
     * Create the panel that will be used for drawing Tic Tac Toe to the screen.
     *
     * @return the panel with the specified dimensions and mouse listener
     */
    private Panel createPanel() {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.addMouseListener(new MyMouseAdapter());
        return panel;
    }

    /**
     * Load the image of the background and the images of the X and O
     */
    private void loadImages() {
        imageBackground = getImage("background");
        imageX = getImage("x");
        imageO = getImage("o");
    }

    public enum Mode {PvP, PvE}

    /**
     * Used for drawing Tic Tac Toe to the screen.
     */
    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintTicTacToe((Graphics2D) g);
        }

        /**
         * The main painting method that paints everything.
         *
         * @param g the Graphics object that will perform the panting
         */
        private void paintTicTacToe(Graphics2D g) {
            setProperties(g);
            paintBoard(g);
            paintWinner(g);
        }

        /**
         * Set the rendering hints of the Graphics object.
         *
         * @param g the Graphics object to set the rendering hints on
         */
        private void setProperties(Graphics2D g) {
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
        Board.State[][] boardArray = board.toArray();
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
    }

    /**
     * For detecting mouse clicks.
     */
    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);

            if (board.isGameOver()) {
                if(mode == Mode.PvP) {
                    System.exit(1);
                }
                board.reset();
                panel.repaint();
            } else if (myTurn) {
                playerMove(e);
            }

        }

        /**
         * Plays the move that the user clicks, if the move is valid.
         *
         * @param e the MouseEvent that the user performed
         */
        private void playerMove(MouseEvent e) {
            int move = getMove(e.getPoint());
            if (!board.isGameOver() && move != -1) {
                boolean validMove = board.move(move);
                if (validMove) {
                    if(mode == Mode.PvE) {
                        Algorithms.miniMax(board);
                    } else if(mode == Mode.PvP) {
                        output.format("%d\n", move);
                        output.flush();
                        myTurn = false; // not my turn any more
                    }
                }
                panel.repaint();
            }
        }

        /**
         * Translate the mouse click position to an index on the board.
         *
         * @param point the location of where the player pressed the mouse
         * @return the index on the Tic Tac Toe board (-1 if invalid click)
         */
        private int getMove(Point point) {
            for (int i = 0; i < cells.length; i++) {
                if (distance(cells[i], point) <= DISTANCE) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Distance between two points. Used for determining if the player has pressed
         * on a cell to play a move.
         *
         * @param p1 the first point (intended to be the location of the cell)
         * @param p2 the second point (intended to be the location of the mouse click)
         * @return the distance between the two points
         */
        private double distance(Point p1, Point p2) {
            double xDiff = p1.getX() - p2.getX();
            double yDiff = p1.getY() - p2.getY();

            double xDiffSquared = xDiff * xDiff;
            double yDiffSquared = yDiff * yDiff;

            return Math.sqrt(xDiffSquared + yDiffSquared);
        }
    }

}
