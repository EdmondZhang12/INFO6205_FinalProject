package com.Info6205.TicTacToe.TicTacToe;

import java.util.*;
import com.Info6205.TicTacToe.ArtificialIntelligence.Random;
import jdk.net.SocketFlow;


public class Training {
    private Hashtable<List<Integer>,List<Integer>> matchboxs;
    private Hashtable<List<Integer>,Hashtable> menaces;
    private final double lr = 0.2;
    private final double decay_gamma = 0.9;
//    private Stack steps;

    public Training () {
        matchboxs = new Hashtable<>();
        menaces = new Hashtable<List<Integer>,Hashtable>();

//        steps = new Stack();
    }

    private List<Integer> getChessState(Board board,List<Integer> StateList){
        Hashtable ChessNow= board.getOccupiedMoves();
        Iterator<Integer> StateSet = ChessNow.keySet().iterator();

        while(StateSet.hasNext()){
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

    public Hashtable OneTraining(Board board){
        Hashtable steps =  new Hashtable<>();
        while(!board.isGameOver()){
//            get current state of chess
            Integer initialState[]={0,0,0,0,0,0,0,0,0};
            List<Integer> s1 = new ArrayList(Arrays.asList(initialState));
            List<Integer> s2 = new ArrayList(Arrays.asList(initialState));

            List<Integer> currentState = getChessState(board,s1);
            Random.run(board);
            List<Integer> nextState = getChessState(board,s2);
            steps.put(currentState,nextState);
        }
        System.out.println("steps result:" + steps);
        return steps;
    }

    public void updateStatus(Hashtable steps,double reward){
        Enumeration<List<Integer>> e = steps.keys();
        while(e.hasMoreElements()) {
            List<Integer> key  = e.nextElement();
//            System.out.println("key:" + key + "value: " + steps.get(key));
            Hashtable<List<Integer>,Double>beads;
            if(menaces.containsKey(key)){
                beads =  menaces.get(key);
                if(beads.containsKey(steps.get(key))){
//                    int i =  beads.get(steps.get(key)) + 1;
                    double i = beads.get(steps.get(key));
                    i += lr * (decay_gamma * reward - i);
                    beads.put((List<Integer>) steps.get(key),i);
                }
                else
                    beads.put((List<Integer>) steps.get(key), (double) 0);
            }
            else{
                beads = new Hashtable();
                beads.put((List<Integer>) steps.get(key),(double) 0);
                menaces.put(key,beads);
            }
        }
    }

    /*
    X is the training model, 1 is rewarded if it wins, 0 is rewarded if it loses, 0.1 is rewarded if there's a draw
     */
    public double giveReward(Board board){
        double reward;
        if(board.getWinner().equals(Board.State.X))
            reward = 1;
        if(board.getWinner().equals(Board.State.O))
            reward = 0;
        else
            reward = 0.1;
        return reward;
    }


    public static void main(String []args) {
        System.out.println("Training begins...");
        Training test = new Training();
        for(int i =0; i < 100 ;i++){
            Board board = new Board();
            System.out.println("training:" + i);
            Hashtable steps = test.OneTraining(board);
            double reward = test.giveReward(board);
            test.updateStatus(steps,reward);
            steps.clear();
        }
        System.out.println("Done!");
//        Integer firststep[]={0,0,0,0,0,0,0,0,0};
//        List<Integer> s1 = new ArrayList(Arrays.asList(firststep));
//        System.out.println(test.menaces.get(s1));
    }
}
