package com.Info6205.TicTacToe.TicTacToe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {
    Training train = new Training();

    @Test
    void randomAction() {
        Board board = new Board();
        train.randomAction(board);
        System.out.println(train.getMenaces());

    }

    @Test
    void greedyAction() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void giveReward() {
        Board board = new Board();
        train.randomAction(board);

//        assertTrue();

    }

    @Test
    void chooseAction() {
        Board board = new Board();
        train.chooseAction(board);
        System.out.println(board.getMoveCount());
//        assertNotEquals(0,board.getMoveCount());

    }

    @Test
    void main() {
    }

    @Test
    void constructor() {
        Training train = new Training();
        assertNotNull(train);
    }

    @Test
    void preTraining() {
        train.preTraining();
        assertNotEquals(0,train.getMenaces().size());
    }
}