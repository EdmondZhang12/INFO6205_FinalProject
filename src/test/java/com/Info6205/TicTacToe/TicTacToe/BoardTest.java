package com.Info6205.TicTacToe.TicTacToe;

import com.Info6205.Util.PrivateMethodTester;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {
    Board board;

    @Test
    void initialize() {
        board = new Board();
        PrivateMethodTester pmt = new PrivateMethodTester(board);
        pmt.invokePrivate("initialize");
        boolean isInitialize = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(board.board[row][col] != Board.State.Blank) {
                    isInitialize = false;
                }
            }
        }
        assertTrue(isInitialize);
    }

    @Test
    void reset() {
        board = new Board();
        board.reset();
        assertFalse(board.isGameOver());
        assertEquals(board.playersTurn, Board.State.X);
        assertEquals(board.moveCount, 0);
        assertEquals(board.winner, Board.State.Blank);
    }

    @Test
    void move() {
        board = new Board();
        board.playersTurn = Board.State.X;
        boolean moveActionSuccess = board.move(0);
        boolean moveActionFalse = board.move(0);
        assertFalse(moveActionFalse);
        assertTrue(moveActionSuccess);
        assertEquals(board.board[0][0], Board.State.X);
        assertEquals(board.playersTurn, Board.State.O);

        for(int i = 1; i < 7; i++) {
            board.move(i);
        }
        assertTrue(board.isGameOver());
        assertEquals(board.winner,Board.State.X);
    }

    @Test
    void isGameOver() {
        board = new Board();
        assertFalse(board.isGameOver());
    }

    @Test
    void toArray() {
        board = new Board();
        board.toArray();
        boolean isClone = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(board.board[row][col] != Board.State.Blank) {
                    isClone = false;
                }
            }
        }
        assertTrue(isClone);
    }

    @Test
    void getTurn() {
        board = new Board();
        board.toArray();
        assertEquals(board.getTurn(),Board.State.X);
    }

    @Test
    void checkRow() {
        board = new Board();
        board.move(0);
        board.move(3);
        board.move(1);
        board.move(4);
        board.move(2);
        PrivateMethodTester pmt = new PrivateMethodTester(board);
        pmt.invokePrivate("checkRow",0);
        assertTrue(board.isGameOver());
    }

    @Test
    void checkColumn() {
        board = new Board();
        board.move(0);
        board.move(2);
        board.move(3);
        board.move(4);
        board.move(6);
        PrivateMethodTester pmt = new PrivateMethodTester(board);
        pmt.invokePrivate("checkColumn",0);
        assertTrue(board.isGameOver());
    }

    @Test
    void checkDiagonalFromTopLeft() {
        board = new Board();
        board.move(0);
        board.move(2);
        board.move(4);
        board.move(1);
        board.move(8);
        PrivateMethodTester pmt = new PrivateMethodTester(board);
        pmt.invokePrivate("checkDiagonalFromTopLeft",2,2);
        assertTrue(board.isGameOver());
    }


    @Test
    void checkDiagonalFromTopRight() {
        board = new Board();
        board.move(2);
        board.move(0);
        board.move(4);
        board.move(1);
        board.move(6);
        PrivateMethodTester pmt = new PrivateMethodTester(board);
        pmt.invokePrivate("checkDiagonalFromTopRight",0,2);
        assertTrue(board.isGameOver());
    }

    @Test
    void getDeepCopy() {
        board = new Board();
        board.move(2);
        Board boardCopied = board.getDeepCopy();
        assertEquals(boardCopied.toString(),board.toString());
    }

    @Test
    void getMoveCount() {
        board = new Board();
        board.move(2);
        assertEquals(board.getMoveCount(),1);
    }

    @Test
    void getWinner() {
        board = new Board();
        board.move(2);
        board.move(0);
        board.move(4);
        board.move(1);
        board.move(6);
        assertEquals(board.getWinner(),Board.State.X);
    }




}