package com.Info6205.TicTacToe.TicTacToe;

import java.util.*;
import com.Info6205.TicTacToe.ArtificialIntelligence.Random;



public class Training {
    private Hashtable<List<Integer>,List<Integer>> matchboxs;
    private Hashtable<List<Integer>,Hashtable> menaces;
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
        while(!board.isGameOver()){
//            get current state of chess
            Integer initialState[]={0,0,0,0,0,0,0,0,0};
            List<Integer> s1 = new ArrayList(Arrays.asList(initialState));
            List<Integer> s2 = new ArrayList(Arrays.asList(initialState));

            List<Integer> currentState = getChessState(board,s1);
            Random.run(board);
            List<Integer> nextState = getChessState(board,s2);
            matchboxs.put(currentState,nextState);

        }
        System.out.println("steps result:" + matchboxs);
        return matchboxs;
    }

    public void updateStatus(Hashtable steps){
        Enumeration<List<Integer>> e = steps.keys();
        while(e.hasMoreElements()) {
            List<Integer> key  = e.nextElement();
            System.out.println("key:" + key + "value: " + steps.get(key));
            Hashtable<List<Integer>,Integer>beads;
            if(menaces.containsKey(key)){
                beads =  menaces.get(key);
                if(beads.containsKey(steps.get(key))){
                    int i =  beads.get(steps.get(key)) + 1;
                    beads.put((List<Integer>) steps.get(key),i);
                }
                else
                    beads.put((List<Integer>) steps.get(key),1);
            }
            else{
                beads = new Hashtable();
                beads.put((List<Integer>) steps.get(key),1);
                menaces.put(key,beads);
            }
        }
        System.out.println("menaces: " + menaces);
    }
    public static void main(String []args) {
        System.out.println("Let's play one training!");
        Training test = new Training();
        for(int i =0; i < 1000 ;i++){
            Board board = new Board();
            System.out.println("Training begins...");
            Hashtable steps = test.OneTraining(board);
            System.out.println("training:" + i);
            test.updateStatus(steps);
            steps.clear();
        }
        System.out.println("Done!");
    }
}
