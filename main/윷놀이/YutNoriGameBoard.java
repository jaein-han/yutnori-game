package org.cis1200.윷놀이;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;

/**
 * This class instantiates a YutNori object, which is the model for the game.
 * As the user clicks the "Throw" button, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * YutNori implements a Model-View-Controller framework.
 *
 * In a Model-View-Controller framework, YutNoriGameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class YutNoriGameBoard extends JPanel {

    private YutNori yutNori; // model for the game
    private JLabel status; // current status text
    private JLabel throwStatus;
    private Color color;

    // Game constants
    // these are the dimensions of the pop-up window when the game is started
    public static final int GAME_BOARD_WIDTH = 700 / 2;
    public static final int GAME_BOARD_HEIGHT = 900 / 2;
    public static final String SAVE_FILE = "files/save.csv";

    /**
     * Initializes the game board.
     */
    public YutNoriGameBoard(JLabel statusInit, JLabel thrStatus) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        yutNori = new YutNori(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        throwStatus = thrStatus;

        updateStatus(); // updates the status JLabel
        repaint(); // repaints the game board

    }


    public void save(String filePath) {
        BufferedWriter buffWriter;

        if (filePath != null) {
            try {

                buffWriter = new BufferedWriter(new FileWriter(filePath));

                // saves the state of the board in the file
                for (int y = 0; y < 6; y++) {
                    for (int x = 0; x < 6; x++) {
                        int[][] board = yutNori.getBoard();
                        if (board[y][x] == 0) {
                            buffWriter.write("0;");
                        } else if (board[y][x] == 1) {
                            buffWriter.write("1;");
                        } else if (board[y][x] == 2) {
                            buffWriter.write("2;");
                        } else if (board[y][x] == 3) {
                            buffWriter.write("3;");
                        } else if (board[y][x] == 4) {
                            buffWriter.write("4;");
                        }
                    }
                    buffWriter.newLine();
                }

                // saves the tokens' locations in the file
                buffWriter.write(yutNori.getPlayer1Token().getXPos() + "," +
                        yutNori.getPlayer1Token().getYPos());
                buffWriter.newLine();
                buffWriter.write(yutNori.getPlayer2Token().getXPos() + "," +
                        yutNori.getPlayer2Token().getYPos());

                // separates the tokens' locations from the player status
                buffWriter.newLine();

                // saves the state of the turn in the file
                if (yutNori.getCurrentPlayer()) {
                    buffWriter.write("Player 1");
                } else {
                    buffWriter.write("Player 2");
                }

                // separates the player status from the throwData status
                buffWriter.newLine();

                // saves the throwData LinkedList in the file
                LinkedList<Integer> throwData = yutNori.getThrowData();
                for (Integer move : throwData) {
                    buffWriter.write(move + ",");
                }

                buffWriter.flush();
                buffWriter.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void load(String filePath) {

        // creates a BufferedReader to read the save file
        BufferedReader buffReader;

        if (filePath != null) {
            try {

                buffReader = new BufferedReader(new FileReader(filePath));

                // loads the state of the board in the file
                int[][] board = yutNori.getBoard();
                for (int i = 0; i < 6; i++) {
                    String line = buffReader.readLine();
                    String[] lineWords = line.split(";");
                    for (int x = 0; x < 6; x++) {
                        board[i][x] = Integer.parseInt(lineWords[x]);
                    }
                }

                // loads each player's token location
                String line = buffReader.readLine();
                String[] p1Coords = line.split(",");

                yutNori.getPlayer1Token().setX(Integer.parseInt(p1Coords[0]));
                yutNori.getPlayer1Token().setY(Integer.parseInt(p1Coords[1]));

                line = buffReader.readLine();
                String[] p2Coords = line.split(",");

                yutNori.getPlayer2Token().setX(Integer.parseInt(p2Coords[0]));
                yutNori.getPlayer2Token().setY(Integer.parseInt(p2Coords[1]));


                // loads the state of the turn in the file
                line = buffReader.readLine();
                yutNori.setPlayer(line.equals("Player 1"));

                // loads the throwData LinkedList in the file
                line = buffReader.readLine();

                if (line != null) {
                    String[] lineMoves = line.split(",");
                    LinkedList<Integer> throwData = yutNori.getThrowData();

                    for (int i = 0; i < lineMoves.length; i++) {
                        throwData.add(Integer.valueOf(lineMoves[i]));
                    }
                }

                buffReader.close();

            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        updateStatus();
        throwStatus.setText("|| Previous data restored!");
        repaint();
    }

    public void playTurn() {
        yutNori.playTurn();
        updateStatus();
        repaint();
    }

    public void undo(int index) {
        LinkedList<Integer> throwData = yutNori.getThrowData();
        if (!throwData.isEmpty()) {
            int move = throwData.get(index);

            yutNori.undo(move);
            throwData.remove(index);
        }
        updateStatus();
        repaint();
    }

    public String getCastName() {
        return yutNori.getCastName();
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        yutNori.reset();
        status.setText("Player 1's Turn");
        throwStatus.setText("");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (yutNori.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }

        int winner = yutNori.checkWinner();
        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!!!");
        }

        if (!yutNori.getCurrentPlayer()) {
            throwStatus.setText("|| Player 1 threw " + this.getCastName() + "!");
        } else {
            throwStatus.setText("|| Player 2 threw " + this.getCastName() + "!");
        }

    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /**
         * The Board is drawn here.
         */

        // draw main outline of board and color diagonal cyan
        int xStart = 50 / 2;
        int yStart = 150 / 2;
        int boardDim = 600 / 2;
        int spaceDim = 100 / 2;
        int xEnd = boardDim + xStart;
        int yEnd = boardDim + yStart;


        g.drawRect(xStart, yStart, boardDim, boardDim);

        // draw outline of each space on the board
        for (int y = yStart; y < boardDim + yStart; y += spaceDim) {
            for (int x = xStart; x < boardDim + xStart; x += spaceDim) {
                g.setColor(Color.BLACK);
                g.drawRect(x, y, spaceDim, spaceDim);
                if (x - xStart == y - yStart) {
                    g.setColor(Color.CYAN);
                    g.fillRect(x, y, spaceDim - 1, spaceDim - 1);
                }
            }
        }

        // first row cyan
        for (int x = xStart; x < boardDim + xStart; x += spaceDim) {
            g.setColor(Color.CYAN);
            g.fillRect(x, yStart, spaceDim - 1, spaceDim - 1);
        }

        // first column cyan
        for (int y = yStart; y < boardDim + yStart; y += spaceDim) {
            g.setColor(Color.CYAN);
            g.fillRect(xStart, y, spaceDim - 1, spaceDim - 1);
        }

        // last row cyan
        for (int x = xStart; x < xEnd - spaceDim; x += spaceDim) {
            g.setColor(Color.CYAN);
            g.fillRect(x, yEnd - spaceDim, spaceDim - 1, spaceDim - 1);
        }

        // last column cyan
        for (int y = yStart; y < yEnd - spaceDim; y += spaceDim) {
            g.setColor(Color.CYAN);
            g.fillRect(xEnd - spaceDim, y, spaceDim - 1, spaceDim - 1);
        }

        // special space red
        g.setColor(Color.RED);
        g.fillRect(xEnd - spaceDim, yEnd - spaceDim, spaceDim - 1, spaceDim - 1);


        /**
         * The Tokens are drawn here.
         */
        int tokenRad = 25;

        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                int shiftX = xStart + x * spaceDim;
                int shiftY = yStart + y * spaceDim;
                if (yutNori.getCell(x, y) == 1) {
                    g.setColor(Color.BLUE);
                    g.fillOval(shiftX, shiftY, tokenRad, tokenRad);
                }
                if (yutNori.getCell(x, y) == 2) {
                    g.setColor(Color.MAGENTA);
                    g.fillOval(shiftX + tokenRad, shiftY + tokenRad, tokenRad, tokenRad);
                }
                if (yutNori.getCell(x, y) == 3) {
                    g.setColor(Color.BLUE);
                    g.fillOval(shiftX, shiftY, tokenRad, tokenRad);
                    g.setColor(Color.MAGENTA);
                    g.fillOval(shiftX + tokenRad, shiftY + tokenRad, tokenRad, tokenRad);
                }
            }
        }

    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GAME_BOARD_WIDTH, GAME_BOARD_HEIGHT);
    }

}
