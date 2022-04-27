package com.Info6205.TicTacToe.TicTacToe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {

    @Test
    void bestMoveFromTraining() {
        Board board = new Board();
        Training test = new Training();
        board.move(0);
        board.move(4);
        board.move(2);
        int res = test.BestMoveFromTraining(board,test);
        assertEquals(1,res);

        Board board2 = new Board();
        board2.move(1);
        board2.move(2);
        board2.move(4);
        int res2 = test.BestMoveFromTraining(board2,test);
        assertEquals(7,res2);

        Board board3= new Board();
        board3.move(0);
        board3.move(2);
        board3.move(4);
        int res3 = test.BestMoveFromTraining(board3,test);
        assertEquals(8,res3);

        Board board4= new Board();
        board4.move(2);
        board4.move(3);
        board4.move(4);
        int res4 = test.BestMoveFromTraining(board4,test);
        assertEquals(6,res4);

    }
}