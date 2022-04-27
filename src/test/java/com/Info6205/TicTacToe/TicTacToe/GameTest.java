package com.Info6205.TicTacToe.TicTacToe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Board board;
    Game game;

    @Test
    void boardFilledUp() {
        game = new Game();
        boolean result = game.boardFilledUp();
        assertTrue(result == false);
    }
}
