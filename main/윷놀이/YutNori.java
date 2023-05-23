package org.cis1200.윷놀이;

import java.util.LinkedList;

/**
 * This class is a model for YutNori.
 */

public class YutNori {

    private int[][] board;
    private Token player1Token;
    private Token player2Token;
    private boolean player1;
    private boolean gameOver;
    private String castName;
    private LinkedList<Integer> throwData;
    private int winner;

    /**
     * This constructor sets up a game state.
     */
    public YutNori() {
        reset();
    }

    public int[][] getBoard() {
        return board;
    }

    public Token getPlayer1Token() {
        return player1Token;
    }

    public Token getPlayer2Token() {
        return player2Token;
    }

    public boolean getCurrentPlayer() {
        return player1;
    }

    public int getCell(int x, int y) {
        return board[y][x];
    }

    public String getCastName() {
        return castName;
    }

    public LinkedList<Integer> getThrowData() {
        return throwData;
    }

    public void setPlayer(boolean bool) {
        this.player1 = bool;
    }

    public void playTurn() {
        if (!gameOver) {

            Throw thr = new Throw();
            this.castName = thr.getCastName(thr.getNumFlats());
            int numMoves = thr.getMoves(castName);

            int move = thr.getMoves(thr.getCastName(thr.getNumFlats()));
            this.throwData.addFirst(move);

            if (player1) {
                player1Token.move(numMoves);
            } else {
                player2Token.move(numMoves);
            }
            player1 = !player1;

        } else {
            winner = checkWinner();
        }

        this.tokenOnBoard();

    }

    public void undo(int moves) {
        if (!gameOver) {
            if (!player1) {
                player1Token.moveBack(moves);
            } else {
                player2Token.moveBack(moves);
            }
            player1 = !player1;
        }
        this.castName = "undo";
        this.tokenOnBoard();
    }

    /**
     * checkWinner checks if the game has reached a win condition.
     * A win condition is reached when one of the tokens is off the board.
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if
     * player 2 has won.
     */
    public int checkWinner() {
        if (player1Token.getOnBoard() && player2Token.getOnBoard()) {
            return 0;
        } else if (!player1Token.getOnBoard()) {
            gameOver = true;
            return 1;
        } else {
            gameOver = true;
            return 2;
        }
    }

    /**
     * tokenOnBoard checks to see where a token is present on the board.
     * It sets a space with Player 1's token as 1 and a space with Player
     * 2's token as 2. It sets a space with both Player 1 and Player 2's
     * tokens as 3. Valid spaces with no token have a 4. Invalid spaces
     * have a 0.
     */
    public void tokenOnBoard() {

        int p1TokenX = this.player1Token.getXPos();
        int p1TokenY = this.player1Token.getYPos();

        int p2TokenX = this.player2Token.getXPos();
        int p2TokenY = this.player2Token.getYPos();

        int[][] newBoard = new int[6][6];
        this.board = newBoard;

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                if (x == p1TokenX && y == p1TokenY && x == p2TokenX && y == p2TokenY) {
                    newBoard[y][x] = 3;
                } else if (x == p1TokenX && y == p1TokenY) {
                    newBoard[y][x] = 1;
                } else if (x == p2TokenX && y == p2TokenY) {
                    newBoard[y][x] = 2;
                } else if (x == 0 || y == 0 || x == 5 || y == 5 || x == y) {
                    newBoard[y][x] = 4;
                }
            }
        }

    }

    public void reset() {
        this.board = new int[6][6];
        this.player1Token = new Token();
        this.player2Token = new Token();
        player2Token.setPlayer2();
        this.player1 = true;
        this.gameOver = false;
        this.castName = "nothing just yet!";
        this.throwData = new LinkedList<>();
        this.winner = 0;
        // sets up necessary integers across the board
        this.tokenOnBoard();
    }

}
