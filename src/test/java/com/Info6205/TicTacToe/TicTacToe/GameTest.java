package com.Info6205.TicTacToe.TicTacToe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @Test
    void hasWinner() {
        game = new Game();
        assertFalse(game.hasWinner());
    }

    @Test
    void boardFilledUp() {
        game = new Game();
        assertFalse(game.boardFilledUp());
    }


}
