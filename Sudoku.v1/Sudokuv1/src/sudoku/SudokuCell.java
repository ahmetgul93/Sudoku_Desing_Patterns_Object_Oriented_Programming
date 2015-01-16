package sudoku;


import java.io.Serializable;
import java.util.HashSet;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class SudokuCell implements Serializable,CellInterface{
    
    private int value;
    private boolean filled;
    private HashSet<Integer> tried;

    public SudokuCell() {
        filled = false;
        tried = new HashSet();
    }

    @Override
    public boolean isFilled() {
        return filled;
    }

    public int get() {
        return value;
    }

    public void set(final int number) {
        filled = true;
        value = number;
        tried.add(number);
    }

    @Override
    public void clear() {
        value = 0;
        filled = false;
    }

    @Override
    public void reset() {
        clear();
        tried.clear();
    }

    public boolean isTried(final int number) {
        return tried.contains(number);
    }

    public void tryNumber(final int number) {
        tried.add(number);
    }

    public int numberOfTried() {
        return tried.size();
    }
}
