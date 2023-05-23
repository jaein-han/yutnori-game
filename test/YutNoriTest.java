package org.cis1200.윷놀이;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YutNoriTest {

    @Test
    public void testTokenMoveGeneral() {
        Throw testThr = new Throw();
        testThr.setCast(0, 1, 0, 0);
        String castName = testThr.getCastName(testThr.getNumFlats());

        // this token should be at (0, 5) by default
        Token testToken = new Token();
        testToken.move(3);

        assertEquals(3, testToken.getXPos());
        assertEquals(testToken.getXPos(), testThr.getMoves(castName));
    }

    @Test
    public void testTokenMoveGoesPastCornerSpace() {
        Throw testThr = new Throw();
        testThr.setCast(1, 1, 1, 1);

        Token testToken = new Token();
        testToken.setX(3);
        testToken.setY(5);
        testToken.move(5);

        assertEquals(5, testToken.getXPos());
        assertEquals(2, testToken.getYPos());
    }

    @Test
    public void testTokenMoveTakesShortcut() {
        Throw testThr = new Throw();
        testThr.setCast(0, 0, 0, 1);

        Token testToken = new Token();
        testToken.setX(5);
        testToken.setY(5);
        testToken.move(3);

        assertEquals(2, testToken.getXPos());
        assertEquals(2, testToken.getYPos());
    }

    @Test
    public void testTokenMoveGoesOverflowsFromShortcut() {
        Throw testThr = new Throw();
        testThr.setCast(0, 0, 0, 0);

        Token testToken = new Token();
        testToken.setX(2);
        testToken.setY(2);
        testToken.move(4);

        assertEquals(0, testToken.getXPos());
        assertEquals(2, testToken.getYPos());
    }

    @Test
    public void testTokenMoveBackGeneral() {
        Token testToken = new Token();
        testToken.setX(4);
        testToken.setY(5);
        testToken.moveBack(3);

        assertEquals(1, testToken.getXPos());
        assertEquals(5, testToken.getYPos());
    }

    @Test
    public void testTokenMoveBackGoesPastCornerSpace() {
        Token testToken = new Token();
        testToken.setX(3);
        testToken.setY(0);
        testToken.moveBack(4);

        assertEquals(5, testToken.getXPos());
        assertEquals(2, testToken.getYPos());
    }

    @Test
    public void testTokenMoveBackAvoidsShortcut() {
        Token testToken = new Token();
        testToken.setX(0);
        testToken.setY(1);
        testToken.moveBack(2);

        assertEquals(1, testToken.getXPos());
        assertEquals(0, testToken.getYPos());
    }

}
