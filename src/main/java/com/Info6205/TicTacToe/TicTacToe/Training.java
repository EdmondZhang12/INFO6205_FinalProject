/**
 * created by Cheryl and Xinlin in 2022
 * use greedy algorithm to do the training
 */
package com.Info6205.TicTacToe.TicTacToe;

import java.util.*;
import com.Info6205.TicTacToe.ArtificialIntelligence.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Training {
    private Hashtable<List<Integer>, List<Integer>> matchboxs;
    private Hashtable<List<Integer>, Hashtable> menaces;
    private final double lr = 0.2;
    private final double decay_gamma = 0.9;
    private final double exp_rate = 0.3;
    private final static Logger LOGGER = LoggerFactory.getLogger(Training.class);

    public Training() {
        matchboxs = new Hashtable<>();
        menaces = new Hashtable<List<Integer>, Hashtable>();
    }

    /**
     * 0 means State.Blank 1 means State.X, 2 means,tate.O
     */
    private List<Integer> getChessState(Board board, List<Integer> StateList) {
        Hashtable ChessNow = board.getOccupiedMoves();
        Iterator<Integer> StateSet = ChessNow.keySet().iterator();

        while (StateSet.hasNext()) {
            Integer position = StateSet.next();
            if (ChessNow.get(position) == Board.State.X) {
                StateList.set(position, 1);
            } else {
                StateList.set(position, 2);
            }
        }
        return StateList;
    }

    public Hashtable randomAction(Board board) {
        Hashtable steps = new Hashtable<>();
        while (!board.isGameOver()) {

//            get current state of chess
            Integer initialState[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            List<Integer> s1 = new ArrayList(Arrays.asList(initialState));
            List<Integer> s2 = new ArrayList(Arrays.asList(initialState));

            List<Integer> currentState = getChessState(board, s1);
            Random.run(board);
            List<Integer> nextState = getChessState(board, s2);

            steps.put(currentState, nextState);
        }

        double reward = giveReward(board);
        updateStatus(steps, reward);
        return steps;
    }

    public Hashtable greedyAction(Board board) {
        Hashtable steps = new Hashtable<>();
        Integer[] initialState = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        List<Integer> current = new ArrayList(Arrays.asList(initialState));
        while (!board.isGameOver()) {
            if (menaces.containsKey(current)) {
                double maxValue = 0;
                List<Integer> next = new ArrayList<>();
                Hashtable nextSteps = menaces.get(current);
                Enumeration<List<Integer>> e = nextSteps.keys();
                while (e.hasMoreElements()) {
                    List<Integer> key = e.nextElement();
                    double value = (double) nextSteps.get(key);
                    if (value > maxValue) {
                        next = key;
                        maxValue = value;
                    }
                }
                int nextIndex = -1;
                for (int i = 0; i < next.size(); i++) {
                    if (Objects.equals(current.get(i), next.get(i)))
                        continue;
                    else
                        nextIndex = i;
                }
                board.move(nextIndex);
                steps.put(current, next);
                current = next;
            }
        }
        System.out.println("steps result:" + steps);
        double reward = giveReward(board);
        updateStatus(steps, reward);
        return steps;
    }

    public void updateStatus(Hashtable steps, double reward) {
        Enumeration<List<Integer>> e = steps.keys();
        while (e.hasMoreElements()) {
            List<Integer> key = e.nextElement();

            Hashtable<List<Integer>, Double> beads;
            if (menaces.containsKey(key)) {
                beads = menaces.get(key);
                if (beads.containsKey(steps.get(key))) {

                    double i = beads.get(steps.get(key));
                    i += lr * (decay_gamma * reward - i);
                    beads.put((List<Integer>) steps.get(key), i);
                } else
                    beads.put((List<Integer>) steps.get(key), (double) 0);
            } else {
                beads = new Hashtable();
                beads.put((List<Integer>) steps.get(key), (double) 0);

            }
            menaces.put(key, beads);
        }
    }

    /**
     * X is the training model, 1 is rewarded if it wins, 0 is rewarded if it loses, 0.1 is rewarded if there's a draw
     */
    public double giveReward(Board board) {
        double reward;
        if (board.getWinner().equals(Board.State.X))
            reward = 1;
        if (board.getWinner().equals(Board.State.O))
            reward = 0;
        else
            reward = 0.1;
        return reward;
    }

    /**
     * use ϵ-greedy method to balance between exploration and exploitation.
     * Here we set exp_rate=0.3 , so 70% of the time our agent will take greedy action,
     * which is choosing action based on current estimation of states-value,
     * and 30% of the time our agent will take random action.
     */
    public void chooseAction(Board board) {
        if (Math.random() <= exp_rate) {
            System.out.println("take random action");
            randomAction(board);
        } else {
            System.out.println("take greedy action");
            greedyAction(board);
        }
        System.out.println("winner is: " + board.getWinner());
    }
    public void preTraining(){
        for(int i =0; i < 100000 ;i++){
            if(i % 100 == 0){
                System.out.println("round " + i);
            }
            Board trainboard = new Board();
            randomAction(trainboard);
        }
        System.out.println("Initial training Done!" +"\n\t" + "greedy algorithm training begins...");
    }
    /**
     * Execute the algorithm.
     * @param board        current board
     * @param player       current player, decide which player to predict
     * return the index(int) of not lose immediately move
     */
    public int CheckWin(Board.State player,Board board){
        int bw = board.BOARD_WIDTH;
        int checkPlayerOccupiedMove ;
        int checkPlayerWinMove ;
        int NeedMove;

//      checkRow
        for(int j = 0; j< bw;j++){
            checkPlayerOccupiedMove = 0;
            checkPlayerWinMove = 0;
            NeedMove = 10;
            for (int i = 0; i < bw; i++) {
                if (board.toArray()[j][i] == player){
                    break;
                }
                else if(board.toArray()[j][i] == Board.State.Blank){

                    checkPlayerWinMove++;
                    NeedMove = j * bw + i;
                }
                else{
                    checkPlayerOccupiedMove++;
                }
            }
            if(checkPlayerOccupiedMove==2 && checkPlayerWinMove==1)
                return NeedMove;
        }

        LOGGER.info("checkRow finished");
//      checkColumn
        for(int j = 0; j< bw;j++){
            checkPlayerOccupiedMove = 0;
            checkPlayerWinMove = 0;
            NeedMove = 10;
            for (int i = 0; i < bw; i++) {
                if (board.toArray()[i][j] == player) {
                    break;
                }
                else if(board.toArray()[i][j] == Board.State.Blank){
                    checkPlayerWinMove++;
                    NeedMove = i * bw + j;
                }
                else{
                    checkPlayerOccupiedMove++;
                }
            }
            if(checkPlayerOccupiedMove==2 && checkPlayerWinMove==1)
                return NeedMove;
        }

        LOGGER.info("checkColumn finished");
//      checkDiagonalFromTopLeft
        checkPlayerOccupiedMove = 0;
        checkPlayerWinMove = 0;
        NeedMove = 10;

        checkDiagonalFromTopLeftOK:
        for(int j = 0; j< bw;j++) {
            for (int i = 0; i < bw; i++) {
                if (i == j) {
                        if (board.toArray()[i][i] == player) {
                            break checkDiagonalFromTopLeftOK;
                        }
                        else if(board.toArray()[i][i] == Board.State.Blank){
                            checkPlayerWinMove++;
                            NeedMove = i * bw + i;
                        }
                        else{
                            checkPlayerOccupiedMove++;
                        }
                    }
            }
        }
        if(checkPlayerOccupiedMove==2 && checkPlayerWinMove==1){
            return NeedMove;
        }

        LOGGER.info("checkDiagonalFromTopLeft finished");
//      checkDiagonalFromTopRight
        checkPlayerOccupiedMove = 0;
        checkPlayerWinMove = 0;
        NeedMove = 10;

        checkDiagonalFromTopRightOK:
        for(int j = 0; j < bw; j++) {
            for (int i = 0; i < bw; i++) {
                if (i+j==bw-1){
                    if (board.toArray()[j][i] == player) {
                        break checkDiagonalFromTopRightOK;
                    }
                    else if(board.toArray()[j][i] == Board.State.Blank){
                        checkPlayerWinMove++;
                        NeedMove = j * bw + i;
                    }
                    else{
                        checkPlayerOccupiedMove++;
                    }
                }
            }
        }
        if(checkPlayerOccupiedMove==2 && checkPlayerWinMove==1)
            return NeedMove;
        LOGGER.info("checkDiagonalFromTopRight finished");
        LOGGER.info(board.toString());
        return 10;
    }

    /**
     * Execute the algorithm.
     * @param board        current board
     * @param option       training result,the matchboxes after we trained.
     * return the index(int) of best move
     */
    public int BestMoveFromTraining(Board board, Training option) {
        int bestMove;
        Double oldValue = 0.0;
        List<Integer> currentChoice = new ArrayList<>();
        List<Integer> bestChoice = currentChoice;

        Integer initialState[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        List<Integer> s3 = new ArrayList(Arrays.asList(initialState));
        List<Integer> currentState = this.getChessState(board, s3);

        Board.State player = board.getTurn();
        int DrawMove = this.CheckWin(player,board);

        if(DrawMove!=10){
            LOGGER.info("player is about to lose,use human strategy instead of training result");
            return DrawMove;
        }

        Hashtable choice = option.menaces.get(currentState);
        if(choice == null) {
            HashSet randomchoose = board.getAvailableMoves();
            Iterator i = randomchoose.iterator();
            while (i.hasNext())
                return (int) i.next();
        }
        Iterator choices = choice.keySet().iterator();

        while (choices.hasNext()) {
            currentChoice = (List<Integer>) choices.next();
            Double newValue = (Double) choice.get(currentChoice);
            if (oldValue.compareTo(newValue) < 0) {
                oldValue = newValue;
                bestChoice = currentChoice;
            }
        }
        LOGGER.info("bestChoice:" + bestChoice.toString());
        for (bestMove = 0; bestMove < 9; bestMove++) {
            if (bestChoice.get(bestMove) != currentState.get(bestMove)) {
               break;
            }
        }

        if(DrawMove!=10){
            System.out.println(DrawMove);
            return DrawMove;
        }
        System.out.println(bestMove);
        return bestMove;
    }

    public Training run() {
        System.out.println("Training begins...");
        Training test = new Training();
        for(int i =0; i < 10000 ;i++){
            if(i % 100 == 0){
                System.out.println("round " + i);
            }
            Board trainboard = new Board();
            test.randomAction(trainboard);
        }
        System.out.println("Initial training Done!" +"\n\t" + "greedy algorithm training begins...");
        for(int i = 0;i < 10000; i++){
            Board board = new Board();
            test.chooseAction(board);
        }

        Integer firststep[]={0,0,0,0,0,0,0,0,0};
        List<Integer> s1 = new ArrayList(Arrays.asList(firststep));
        return test;
    }


    public static void main(String []args) {
        Training test = new Training();
        test.preTraining();
        System.out.println(test.getMenaces());
        test.greedyAction(new Board());
        System.out.println(test.getMenaces());

    }

    public Hashtable<List<Integer>, Hashtable> getMenaces() {
        return menaces;
    }
}
