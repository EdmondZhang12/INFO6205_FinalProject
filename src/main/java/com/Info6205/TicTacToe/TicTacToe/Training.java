package com.Info6205.TicTacToe.TicTacToe;

import java.util.*;
import com.Info6205.TicTacToe.ArtificialIntelligence.Random;


public class Training {
    private Hashtable<List<Integer>,List<Integer>> matchboxs;
    private Stack steps;

    public Training () {
        matchboxs = new Hashtable<>();
        steps = new Stack();
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

    public void OneTraining(Board board){
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
    }
    public static void main(String []args) {
        System.out.println("Let's play one training!");
        Board board = new Board();
        Training test = new Training();
        test.OneTraining(board);
    }
}
