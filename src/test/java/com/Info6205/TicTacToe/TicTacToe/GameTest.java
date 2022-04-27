package com.Info6205.TicTacToe.TicTacToe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GameTest {

    Game game;

    @Test
    void hasWinner() {
        game = new Game();
        game.hasWinner();
        assertEquals(1,game.currentPlayer);
    }

    @Test
    void boardFilledUp() {
    }

    @Test
    void legalMove() {
    }

    @Test
    void Player() {
    }

    @Test
    void setOpponent() {
    }

    @Test
    void otherPlayerMoved() {
    }

    @Test
    void run() {
    }
}
