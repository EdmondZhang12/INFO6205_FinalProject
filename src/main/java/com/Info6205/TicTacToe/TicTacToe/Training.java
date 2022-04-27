package com.Info6205.TicTacToe.TicTacToe;

import java.util.*;
import com.Info6205.TicTacToe.ArtificialIntelligence.Random;


public class Training {
    private Hashtable<List<Integer>, List<Integer>> matchboxs;
    private Hashtable<List<Integer>, Hashtable> menaces;
    private final double lr = 0.2;
    private final double decay_gamma = 0.9;
    private final double exp_rate = 0.3;
//    private Stack steps;

    public Training() {
        matchboxs = new Hashtable<>();
        menaces = new Hashtable<List<Integer>, Hashtable>();
//        steps = new Stack();
    }

    private List<Integer> getChessState(Board board, List<Integer> StateList) {
        Hashtable ChessNow = board.getOccupiedMoves();
        Iterator<Integer> StateSet = ChessNow.keySet().iterator();

        while (StateSet.hasNext()) {
            Integer position = StateSet.next();
//            0 means State.Blank 1 means State.X, 2 means,tate.O
            if (ChessNow.get(position) == Board.State.X) {
                StateList.set(position, 1);
            } else {
                StateList.set(position, 2);
            }
        }
        return StateList;
    }

    public void randomAction(Board board) {
        Hashtable steps = new Hashtable<>();
        while (!board.isGameOver()) {

//            get current state of chess
            Integer initialState[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            List<Integer> s1 = new ArrayList(Arrays.asList(initialState));
            List<Integer> s2 = new ArrayList(Arrays.asList(initialState));

            List<Integer> currentState = getChessState(board, s1);
            Random.run(board);
            List<Integer> nextState = getChessState(board, s2);
//            System.out.println("next State:" + nextState + "Current state:" + currentState);
            steps.put(currentState, nextState);
        }
//        System.out.println("steps result:" + steps);
        double reward = giveReward(board);
        updateStatus(steps, reward);
    }

    public void greedyAction(Board board) {
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
//        System.out.println("steps result:" + steps);
        double reward = giveReward(board);
        updateStatus(steps, reward);
    }

    public void updateStatus(Hashtable steps, double reward) {
        Enumeration<List<Integer>> e = steps.keys();
        while (e.hasMoreElements()) {
            List<Integer> key = e.nextElement();
//            System.out.println("key:" + key + "value: " + steps.get(key));
            Hashtable<List<Integer>, Double> beads;
            if (menaces.containsKey(key)) {
                beads = menaces.get(key);
                if (beads.containsKey(steps.get(key))) {
//                    int i =  beads.get(steps.get(key)) + 1;
                    double i = beads.get(steps.get(key));
                    i += lr * (decay_gamma * reward - i);
                    beads.put((List<Integer>) steps.get(key), i);
                } else
                    beads.put((List<Integer>) steps.get(key), (double) 0);
            } else {
                beads = new Hashtable();
                beads.put((List<Integer>) steps.get(key), (double) 0);
                menaces.put(key, beads);
            }
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
//            else{
//                System.out.println("take random action");
//                Hashtable steps = OneTraining(board);
//                double reward = giveReward(board);
//                updateStatus(steps,reward);
//                steps.clear();
//            }
        }
        System.out.println("winner is: " + board.getWinner());
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
        Hashtable choice = option.menaces.get(currentState);
        Iterator choices = choice.keySet().iterator();

        while (choices.hasNext()) {
            currentChoice = (List<Integer>) choices.next();
            Double newValue = (Double) choice.get(currentChoice);
            if (oldValue.compareTo(newValue) < 0) {
                oldValue = newValue;
                bestChoice = currentChoice;
            }
        }
        for (bestMove = 0; bestMove < 9; bestMove++) {
            if (bestChoice.get(bestMove) != currentState.get(bestMove)) {
               break;
            }
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
        //System.out.println("menaces: " + test.menaces);

        //System.out.println(test.menaces);
        Integer firststep[]={0,0,0,0,0,0,0,0,0};
        List<Integer> s1 = new ArrayList(Arrays.asList(firststep));
        //System.out.println("-----------------------------------");
        return test;
    }


    public static void main(String []args) {
        System.out.println("Training begins...");
        Training test = new Training();
        for(int i =0; i < 100000 ;i++){
            if(i % 100 == 0){
                System.out.println("round " + i);
            }
            Board trainboard = new Board();
            test.randomAction(trainboard);
        }
        System.out.println("Initial training Done!" +"\n\t" + "greedy algorithm training begins...");
        for(int i = 0;i < 100000; i++){
            Board board = new Board();
            test.chooseAction(board);
        }
        System.out.println("menaces: " + test.menaces);

        System.out.println(test.menaces);
        Integer firststep[]={0,0,0,0,0,0,0,0,0};
        List<Integer> s1 = new ArrayList(Arrays.asList(firststep));
        System.out.println(test.menaces.get(s1));

//      sample for test BestMoveFromTraining() method
        Board board1 = new Board();
        board1.move(4);
        board1.move(1);
        board1.move(0);
        System.out.println(board1.getOccupiedMoves());
        test.BestMoveFromTraining(board1,test);

        Board board2 = new Board();
        board2.move(4); //x
        board2.move(2);
        board2.move(7);
        System.out.println(board2.getOccupiedMoves());
        test.BestMoveFromTraining(board2,test);
    }

    public Hashtable<List<Integer>, Hashtable> getMenaces() {
        return menaces;
    }
}
