package com.Info6205.TicTacToe.TicTacToe;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {
    Training train = new Training();
    @Test
    void randomAction() {
        Hashtable steps = train.randomAction(new Board());
        assertNotEquals(0,steps.size());
    }

    @Test
    void greedyAction() {
        Hashtable steps = train.greedyAction(new Board());
        assertNotEquals(0,steps.size());
    }

    @Test
    void updateStatus() {
        Hashtable<List<Integer>, Hashtable> premenaces = train.getMenaces();
        Integer[] arr1 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] arr2 = {0, 0, 0, 0, 1, 0, 0, 0, 0};
        List<Integer> current = new ArrayList(Arrays.asList(arr1));
        List<Integer> next = new ArrayList(Arrays.asList(arr2));
        Hashtable steps = new Hashtable();
        steps.put(current,next);
        double p1 = (double) premenaces.get(current).get(next);
        train.updateStatus(steps,1.0);
        Hashtable<List<Integer>, Hashtable> newmenaces = train.getMenaces();
        double p2 = (double) newmenaces.get(current).get(next);
        assertNotEquals(p1,p2);
    }

    @Test
    void giveReward() {
        Board board = new Board();
        train.chooseAction(board);
        double reward = train.giveReward(board);
        boolean boo = (reward == 1 || reward == 0 || reward == 0.1);
        assertTrue(boo);
    }

    @Test
    void chooseAction() {
//        train.preTraining();
        Board board = new Board();
        train.chooseAction(board);
        assertNotEquals(0,board.getMoveCount());
    }

    @Test
    void checkWin() {
    }

    @Test
    void bestMoveFromTraining() {
    }

    @Test
    void run() {
    }


    @Test
    void constructor() {
        Training train = new Training();
        assertNotNull(train);
    }

    @BeforeEach
    void preTraining() {
        train.preTraining();
        assertNotEquals(0,train.getMenaces().size());
    }

    @Test
    void getMenaces() {
        assertNotEquals(0,train.getMenaces().size());
    }
}