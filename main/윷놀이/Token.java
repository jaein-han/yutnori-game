package org.cis1200.윷놀이;

public class Token {

    private int xPos;
    private int yPos;
    private boolean player1;
    private boolean onBoard;

    public Token() {
        this.xPos = 0;
        this.yPos = 5;
        this.player1 = true;
        this.onBoard = true;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean getOnBoard() {
        return onBoard;
    }

    public void setX(int pos) {
        this.xPos = pos;
    }

    public void setY(int pos) {
        this.yPos = pos;
    }

    public void setPlayer2() {
        player1 = false;
    }

    public void move(int numMoves) {
        int oldXPos = xPos;
        int oldYPos = yPos;

        if (xPos == yPos && xPos != 0) { // for when the token is on the special diagonal
            xPos -= numMoves;
            yPos -= numMoves;
            if (xPos < 0 || yPos < 0) { // if the token moves beyond the diagonal
                xPos = 0;
                yPos = numMoves - oldXPos;
                if (yPos >= 5) {
                    onBoard = false; // this is when the game ends!
                    yPos = 5;
                }
            }
        } else if (yPos == 5) { // for when the token is on the last row of the board
            xPos += numMoves;
            if (xPos > 5) {
                xPos = 5;
                yPos -= numMoves - (5 - oldXPos);
            }
        } else if (xPos == 5) { // for when the token is on the last column of the board
            yPos -= numMoves;
            if (yPos < 0) {
                yPos = 0;
                xPos -= numMoves - oldYPos;
            }
        } else if (yPos == 0) { // for when the token is on the first row of the board
            xPos -= numMoves;
            if (xPos < 0) {
                xPos = 0;
                yPos += numMoves - oldXPos;
            }
        } else if (xPos == 0) { // for when the token is on the first column of board
            yPos += numMoves;
            if (yPos >= 5) {
                onBoard = false; // this is when the game ends!
                yPos = 5;
            }
        }
    }

    public void moveBack(int moves) {
        int oldXPos = xPos;
        int oldYPos = yPos;

        if (xPos == yPos && xPos != 0) { // for when the token is on the special diagonal
            xPos += moves;
            yPos += moves;
            if (xPos > 5 || yPos > 5) { // if the token moves beyond the diagonal
                xPos = 5 - (moves - (5 - oldXPos));
                yPos = 5;
                if (xPos < 0) {
                    xPos = 0;
                }
            }
        } else if (yPos == 5) { // for when the token is on the last row of the board
            xPos -= moves;
            if (xPos < 0) {
                xPos = 0;
            }
        } else if (xPos == 5) { // for when the token is on the last column of the board
            yPos += moves;
            if (yPos > 5) {
                yPos = 5;
                xPos = 5 - (moves - (5 - oldYPos));
            }
        } else if (yPos == 0) { // for when the token is on the first row of the board
            xPos += moves;
            if (xPos > 5) {
                xPos = 5;
                yPos += moves - (5 - oldXPos);
            }
        } else if (xPos == 0) { // for when the token is on the first column of board
            yPos -= moves;
            if (yPos < 0) {
                yPos = 0;
                xPos = moves - oldYPos;
            }
        }
    }

}
