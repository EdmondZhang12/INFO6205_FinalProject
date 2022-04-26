package com.Info6205.TicTacToe.TicTacToe;

import com.Info6205.Util.PrivateMethodTester;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;


    @Test
    void reset() {
        board = new Board();
        board.reset();
        assertEquals(0,board.moveCount);
    }

    @Test
    void move() {
        PrivateMethodTester pr = new PrivateMethodTester(board);

    }

    @Test
    void isGameOver() {
        board = new Board();
        board.isGameOver();
    }

    @Test
    void toArray() {
        board = new Board();
        board.toArray();
        assertEquals(0,board.moveCount);

    }

    @Test
    void getTurn() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void getAvailableMoves() {
    }

    @Test
    void getDeepCopy() {
    }

    @Test
    void testToString() {
    }

}