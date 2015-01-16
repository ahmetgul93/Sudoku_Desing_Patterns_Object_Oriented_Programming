/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author USER
 */
public class SudokuBoard {

    private int[][] game;
    private int[][] solution;
    private JTextField[][] fields;
    private JFrame frame;
    private ButtonController buttonController;

    public SudokuBoard() {
        game = new int[9][9];
        solution = new int[9][9];
        buttonController = new ButtonController(this);

    }

    public JTextField[][] getFields() {
        return fields;
    }

    public int[][] getSolution() {
        return solution;
    }

    public int[][] getGame() {
        return game;
    }

    public void createGame() {
        SudokuField cozum = new SudokuField(3);
        cozum.generateFullField(1, 1);

        for (int m = 1; m < 10; m++) {
            for (int n = 1; n < 10; n++) {
                solution[m - 1][n - 1] = cozum.get(m, n);
                game[m - 1][n - 1] = solution[m - 1][n - 1];
                System.out.print(" " + solution[m - 1][n - 1]);
            }
            System.out.println(" ");
        }
        
        GameStrategy gameStrategy;
        String radioButton = buttonController.getSelectedRadio();
        if (radioButton.equals("Easy")) {
            gameStrategy = new EasyGame(game);
            gameStrategy.generateGame();
            game = gameStrategy.getGame();
        }
        if (radioButton.equals("Hard")) {
            gameStrategy = new HardGame(game);
            gameStrategy.generateGame();
            game = gameStrategy.getGame();
        }
    }

    public void startGame() {
        createGame();

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }
                frame = new JFrame("Sudoku ver 0.7");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new SubBoard());
                frame.add(buttonController.createController(), BorderLayout.AFTER_LINE_ENDS);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public class SubBoard extends JPanel {

        public SubBoard() {
            setLayout(new GridLayout(3, 3, 2, 2)); //çerçeve
            setBorder(new CompoundBorder(new LineBorder(Color.GRAY, 3), new EmptyBorder(4, 4, 4, 4)));

            Font font1 = new Font("SansSerif", Font.BOLD, 20);

            fields = new JTextField[9][9];
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    fields[row][col] = new JTextField(2);
                    fields[row][col].setFont(font1);
                    fields[row][col].setHorizontalAlignment(JTextField.CENTER);
                    fields[row][col].setText(String.valueOf(game[row][col]).equals(String.valueOf(0)) ? "" : String.valueOf(game[row][col]));
                    fields[row][col].setEditable(String.valueOf(game[row][col]).equals(String.valueOf(0)) ? true : false);
                    fields[row][col].setBackground(game[row][col] == 0 ? Color.WHITE : new Color(-986896));

                    AbstractDocument d = (AbstractDocument) fields[row][col].getDocument();
                    d.setDocumentFilter(new DocumentFilter() {
                        int max = 1;

                        @Override
                        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                            int documentLength = fb.getDocument().getLength();
                            if (documentLength - length + text.length() <= max && (text.equals("1") || text.equals("2") || text.equals("3")|| text.equals("4") 
                     || text.equals("5")|| text.equals("6")|| text.equals("7")|| text.equals("8")|| text.equals("9") || text.equals(""))) {
                                super.replace(fb, offset, length, text.toUpperCase(), attrs);
                            }
                        }
                    });
                }
            }

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int startRow = row * 3;
                    int startCol = col * 3;
                    add(new ChildBoard(3, 3, fields, startRow, startCol));
                }
            }
        }
    }

    public class ChildBoard extends JPanel {

        public ChildBoard(int rows, int cols, JTextField[][] fields, int startRow, int startCol) {
            setBorder(new LineBorder(Color.LIGHT_GRAY, 4));
            setLayout(new GridLayout(rows, cols, 2, 2));
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    add(fields[startRow + row][startCol + col]);
                }
            }
        }
    }
}
