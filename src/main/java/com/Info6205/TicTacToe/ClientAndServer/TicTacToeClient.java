package com.Info6205.TicTacToe.ClientAndServer;

import com.Info6205.TicTacToe.TicTacToe.Board;
import com.Info6205.TicTacToe.TicTacToe.Training;
import com.Info6205.TicTacToe.UserInterface.GameInterface;
import com.Info6205.TicTacToe.UserInterface.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  @Description
 *  the client of online playing
 *  each client represent a player
 *  @author Shijie Zhang
 */
public class TicTacToeClient extends JPanel implements Runnable{

    /**
     * The distance away from the center of a cell that will register
     * as a click.
     */
    private static final int DISTANCE = 100;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private Board board;
    private final JPanel panel;
    public  Mode mode;
    private JTextArea displayArea;
    private JTextField idField;

    // parameter for server
    private Socket connection;
    private Scanner input;
    private Formatter output;
    private final String ticTacToeHost;
    private String myMark;
    private boolean myTurn;
    private final String X_MARK = "X";
    private final String O_MARK = "O";
    /**
     * The center location of each of the cells is stored here.
     * Used for identifying which cell the player has clicked on.
     */
    private Point[] cells;
    private MainInterface frame;
    private Training training;
    private int mark;

    /**
     * Construct the Window.
     * @param playMode the game mode (Player vs. Player or Player vs. AI)
     * @param host
     * @param mainInterface
     */
    public TicTacToeClient(String playMode, String host, MainInterface mainInterface, Training training, int mark) {
        this.cells = loadCells();
        this.mark = mark;
        board = new Board();
        panel = createPanel();
        add(panel, BorderLayout.CENTER);

        if(playMode.equals("pvp")) {
            // if playMode is PvP, run the thread
            this.mode = Mode.PvP;
            displayArea4pvp();
            startClient();
        } else {
            this.mode = Mode.PvE;
            if(mark == 1){
                boolean validMove = board.move(4);
            }
            myTurn = true;
        }
        addReturnBtn();
        // set name of server
        this.ticTacToeHost = host;
        this.frame = mainInterface;
        this.training = training;
    }

    /**
     * Create a new Btn for return
     */
    private void addReturnBtn() {
        JButton returnBtn = new JButton("Back <");
        returnBtn.setBorder(BorderFactory.createRaisedBevelBorder());
        returnBtn.setPreferredSize(new Dimension(200,50));
        Font font = new Font(" ", Font.PLAIN, 30);
        returnBtn.setFont(font);

        returnBtn.addActionListener(e -> {
            if(!board.isGameOver() ) {
                JOptionPane pane = new JOptionPane();
                int dialogResult = JOptionPane.showConfirmDialog(pane,  "Are you sure? You will lose this one ","Back to Menu.", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.NO_OPTION)
                    return;
            }
            frame.returnMainPanel();
            board.reset();
            panel.repaint();
        });
        if(mode == Mode.PvE) {
            this.add(returnBtn,BorderLayout.CENTER);
        }
    }

    /**
     * Create a new display for pvp mode
     */
    private void displayArea4pvp() {
        // set up displayArea
        displayArea = new JTextArea(4, 20); // set up JTextArea
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);
        // set up textfield
        idField = new JTextField();
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
        // get player's mark (X or O)
        try {
            myMark = input.nextLine();
        } catch (Exception e){
            JOptionPane pane = new JOptionPane();
            JOptionPane.showMessageDialog( pane, "Have to run the server if wanna play online");
            frame.returnMainPanel();
        }
        idField.setText("You are player \"" + myMark + "\"");
        this.repaint();

        myTurn = (myMark.equals(X_MARK)); // determine if client's turn

        // receive messages sent to client and output them
        while (true) {
            if (input.hasNextLine()) {
                processMessage(input.nextLine());
            }
        }
    }

    /**
     * process messages sent to the client
     * @param message the meesge from client
     */
    private void processMessage(String message) {

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
                displayMessage(message + "\n"); // display the message
                break;

        }
    }

    /**
     * manipulate displayArea in event-dispatch thread
     * @param messageToDisplay the message want to display in Area
     */
    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(() -> {
            displayArea.append(messageToDisplay); // updates output
        });
    }

    /**
     * Load the locations of the center of each of the cells.
     */
    private Point[] loadCells() {
        Point[] cells = new Point[9];
        cells[0] = new Point(109, 109);
        cells[1] = new Point(299, 109);
        cells[2] = new Point(489, 109);
        cells[3] = new Point(109, 299);
        cells[4] = new Point(299, 299);
        cells[5] = new Point(489, 299);
        cells[6] = new Point(109, 489);
        cells[7] = new Point(299, 489);
        cells[8] = new Point(489, 489);
        return cells;
    }


    /**
     * Create the panel that will be used for drawing Tic Tac Toe to the screen.
     *
     * @return the panel with the specified dimensions and mouse listener
     */
    private JPanel createPanel() {
        GameInterface gameInterface = new GameInterface();
        gameInterface.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gameInterface.addMouseListener(new MyMouseAdapter());
        board = gameInterface.getBoard();
        return gameInterface;
    }

    public enum Mode {PvP, PvE}

    /**
     * For detecting mouse clicks.
     */
    public class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);
            if (board.isGameOver()) {
                if(mode == Mode.PvP) {
                    JOptionPane pane = new JOptionPane();
                    int dialogResult = JOptionPane.showConfirmDialog(pane,"Back to Menu?","Game over.", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION)
                        frame.returnMainPanel();
                    else
                        System.exit(0);
                    }
                board.reset();
                if(mark == 1) {
                    boolean validMove = board.move(4);
                }
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
                    if(mode == Mode.PvE && !board.isGameOver()) {
                        //Algorithms.miniMax(board);
                        int nextStep = training.BestMoveFromTraining(board,training);
                        validMove = board.move(nextStep);
                        if(!validMove) {
                            System.out.println("-------unvalid------");
                        }
                    } else if(mode == Mode.PvP) {
                        output.format("%d\n", move);
                        output.flush();
                        myTurn = false;
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
