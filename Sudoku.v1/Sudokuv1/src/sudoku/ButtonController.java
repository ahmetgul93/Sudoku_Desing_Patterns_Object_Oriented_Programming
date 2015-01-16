/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author USER
 */
public class ButtonController extends JPanel {

    private JButton btnCheck;
    private JButton btnHint;
    private JButton btnNew;
    private JButton btnReset;
    private JRadioButton rBtnEasy;
    private JRadioButton rBtnHard;
    private ButtonGroup btnGroup;
    private SudokuBoard sudokuBoard;
    private GridBagConstraints gbc;

    public ButtonController(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        gbc = new GridBagConstraints();

        btnCheck = new JButton("Check");
        btnHint = new JButton("Hint");
        btnNew = new JButton("New");
        btnReset = new JButton("Reset");
        setRadioButtons();
    }

    public String getSelectedRadio() {
        Enumeration<AbstractButton> allRadioButton = btnGroup.getElements();
        while (allRadioButton.hasMoreElements()) {
            JRadioButton temp = (JRadioButton) allRadioButton.nextElement();
            if (temp.isSelected()) {
                return temp.getText();
            }
        }
        return null;
    }

    private void setMenu() {
        setBorder(new EmptyBorder(4, 4, 4, 4));
        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    private void checkButtonAction() {
        btnCheck.setFocusable(false);
        
        btnCheck.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = true;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (!sudokuBoard.getFields()[i][j].getText().equals(String.valueOf(sudokuBoard.getSolution()[i][j]))) {
                            flag = false;
                            sudokuBoard.getFields()[i][j].setBackground(Color.RED);
                        } else {
                            if (sudokuBoard.getFields()[i][j].getBackground().getRGB() != -986896) {
                                sudokuBoard.getFields()[i][j].setBackground(Color.GREEN);
                            }
                        }
                    }
                }
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "Yanlış çözdünüz..", "Wrong Solution", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Tebrikler, doğru çözdünüz..", "True Solution", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        add(btnCheck, gbc);
        gbc.gridy++;
    }

    private void hintButtonAction() {
        btnHint.setFocusable(false);
        btnHint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = false;
                List<Integer> positions = new ArrayList();
                for (int i = 0; i < 81; i++) {
                    positions.add(i);
                }
                Collections.shuffle(positions);
                while (flag == false && positions.size() > 0) {
                    int position = positions.remove(0);

                    int x = position / 9;
                    int y = position % 9;

                    if (sudokuBoard.getFields()[x][y].getText().equals("")) {
                        sudokuBoard.getFields()[x][y].setText(String.valueOf(sudokuBoard.getSolution()[x][y]));
                        flag = true;
                    }
                }
            }
        });
        add(btnHint, gbc);
        gbc.gridy++;
    }

    private void newButtonAction() {
        btnNew.setFocusable(false);
        btnNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuBoard.createGame();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if(sudokuBoard.getGame()[i][j] == 0 )
                        {
                            sudokuBoard.getFields()[i][j].setText("");
                            sudokuBoard.getFields()[i][j].setBackground(Color.WHITE);
                            sudokuBoard.getFields()[i][j].setEditable(true);     
                        }
                        else
                        {
                            sudokuBoard.getFields()[i][j].setText(String.valueOf(sudokuBoard.getGame()[i][j]));
                            sudokuBoard.getFields()[i][j].setBackground(new Color(-986896));
                            sudokuBoard.getFields()[i][j].setEditable(false);
                        }
                    }
                }
            }
        });
        add(btnNew, gbc);
        gbc.gridy++;
    }

    private void resetButtonAction() {
        btnReset.setFocusable(false);
        btnReset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        sudokuBoard.getFields()[i][j].setText(sudokuBoard.getGame()[i][j] == 0 ? "" : String.valueOf(sudokuBoard.getGame()[i][j]));
                        sudokuBoard.getFields()[i][j].setBackground(sudokuBoard.getGame()[i][j] == 0 ? Color.WHITE : sudokuBoard.getFields()[i][j].getBackground());
                    }
                }
            }
        });
        add(btnReset, gbc);
        gbc.gridy++;
    }

    private void setRadioButtons() {
        rBtnEasy = new JRadioButton("Easy");
        rBtnHard = new JRadioButton("Hard");
        btnGroup = new ButtonGroup();
        btnGroup.add(rBtnEasy);
        btnGroup.add(rBtnHard);

        setSize(100,200);
        setLayout( new FlowLayout());
        
        rBtnEasy.setSelected(true);
        add(rBtnEasy, gbc);
        gbc.gridy++;
        add(rBtnHard, gbc);
        gbc.gridy++;
        
    }

    public ButtonController createController() {
        setMenu();
        checkButtonAction();
        hintButtonAction();
        newButtonAction();
        resetButtonAction();

        return this;
    }
}
