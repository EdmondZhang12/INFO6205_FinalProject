package com.Info6205.TicTacToe.ArtificialIntelligence;

import com.Info6205.TicTacToe.TicTacToe.Board;

/**
 * Uses various algorithms to play Tic Tac Toe.
 */
public class Algorithms {

    /**
     * Algorithms cannot be instantiated.
     */
    private Algorithms() {}

    /**
     * Play a random move.
     * @param board     the Tic Tac Toe board to play on
     */
    public static void random (Board board) {
        Random.run(board);
    }

    /**
     * Play using the MiniMax Algorithm.
     * @param board     the Tic Tac Toe board to play on
     */
    public static void miniMax (Board board) {
        MiniMax.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    /**
     * Play using the MiniMax algorithm. Include a depth limit.
     * @param board     the Tic Tac Toe board to play on
     * @param ply       the maximum depth
     */

}
