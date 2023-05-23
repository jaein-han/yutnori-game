package org.cis1200.윷놀이;

import javax.swing.*;
import java.awt.*;

import static org.cis1200.윷놀이.YutNoriGameBoard.SAVE_FILE;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * This game adheres to a Model-View-Controller design framework.
 */

public class RunYutNori implements Runnable {

    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // top-level frame in which game components live
        final JFrame frame = new JFrame("YutNori");

        // coordinate where the game will be on the screen when opened
        frame.setLocation(300, 300);
        String instructions =
                "윷놀이 (pronounced 'yutnori') is a traditional Korean board game" + "\n" +
                "often played to celebrate the new year. This is a virtual and" + "\n" +
                "slightly modified version of the original game." + "\n" +
                "The game is played with two players taking turns to throw the 윷," + "\n" +
                "which are sticks that serve a role analogous to dice in Monopoly." + "\n" +
                "Each player has a token, which is represented here as circles." + "\n" +
                "Player 1's token is blue and Player 2's token is pink. You can" + "\n" +
                "click the 'Throw' button to throw the 윷." + "\n" +
                "There are 5 possibilities that can result from a throw: 도, 개, 걸," + "\n" +
                "윷, and 모. In real life, the result is determined by counting how" + "\n" +
                "many of the sticks (which have a round side and a flat side) land" + "\n" +
                "flat side up. 1 stick flat side up corresponds to 도, 2 sticks is" + "\n" +
                "개, 3 sticks is 걸, 4 sticks is 윷, and 5 sticks is 모. These names" + "\n" +
                "are called 'casts.' Each cast name is also associated with a number" + "\n" +
                "that indicates how many spaces a token can advance on the board. 도" + "\n" +
                "allows the token to advance 1 space, 개 is 2 spaces, 걸 is 3 spaces,"  + "\n" +
                "윷 is 4 spaces, and 모 is 5 spaces. The tokens can only travel along" + "\n" +
                "the blue or red squares." + "\n" +
                "The tokens move counterclockwise, and the player who gets their token" + "\n" +
                "back to the starting space is the winner. If the token lands on the" + "\n" +
                "red space, it can take the diagonal shortcut, which may help the player" + "\n" +
                "reach the end faster." + "\n" +
                "You can save a certain game state by clicking the 'Save' button. You" + "\n" +
                "can resume from that game state by clicking the 'Load' button. You can" + "\n" +
                "also undo the moves by clicking the 'Undo' button. However, if you use" + "\n" +
                "the undo button when your token has taken a shortcut to the first" + "\n" +
                "column of the board, your shortcut will be lost and the token will undo" + "\n" +
                "its previous move without going through the shortcut." + "\n" +
                "And lastly, don't forget to have fun!";
        JOptionPane.showMessageDialog(null, instructions);

        // status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.NORTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        final JLabel throw_status = new JLabel("Setting up...");
        status_panel.add(throw_status);

        // game board
        final YutNoriGameBoard yutNoriGameBoard = new YutNoriGameBoard(status, throw_status);
        frame.add(yutNoriGameBoard, BorderLayout.CENTER);

        // throw button
        final JPanel throw_control_panel = new JPanel();
        frame.add(throw_control_panel, BorderLayout.EAST);

        final JButton throwButton = new JButton("Throw");
        throwButton.addActionListener(e -> yutNoriGameBoard.playTurn());
        throw_control_panel.add(throwButton);

        // undo button
        final JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            int index = 0;
            yutNoriGameBoard.undo(index);
        }
        );
        throw_control_panel.add(undoButton);

        // reset, save, and load buttons
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.SOUTH);

        // save and load buttons
        final JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> yutNoriGameBoard.save(SAVE_FILE));
        control_panel.add(saveButton);

        final JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> yutNoriGameBoard.load(SAVE_FILE));
        control_panel.add(loadButton);

        // reset button
        /* Note here that when we add an action listener to the reset button, we
        define it as an anonymous inner class that is an instance of
        ActionListener with its actionPerformed() method overridden. When the
        button is pressed, actionPerformed() will be called. */
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> yutNoriGameBoard.reset());
        control_panel.add(reset);

        // put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // start the game
        yutNoriGameBoard.reset();
    }

}