package com.Info6205.TicTacToe.TicTacToe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GameTest {

    Game game;
    Board board;

    @Test
    void hasWinner() {
        Game game = new Game();
        boolean result = game.hasWinner();
        assertTrue(result == true);
    }


    @Test
    void boardFilledUp() {
        Game game = new Game();
        boolean result = game.boardFilledUp();
        assertTrue(result == true);
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
