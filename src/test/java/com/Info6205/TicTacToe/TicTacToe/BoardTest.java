package com.Info6205.TicTacToe.TicTacToe;

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
        board = new Board();
        boolean result1 = board.move(1);
        assertTrue(result1 == true);
    }

    @Test
    void isGameOver() {
        board = new Board();
        board.isGameOver();
        assertEquals(false,board.isGameOver());
    }

    @Test
    void ToString() {
        board = new Board();
        board.toString();
        assertEquals("- - - \n" +
                              "- - - \n" +
                              "- - - ",board.toString());
    }

}