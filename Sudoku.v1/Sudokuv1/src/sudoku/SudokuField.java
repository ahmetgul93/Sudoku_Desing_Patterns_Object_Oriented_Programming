package sudoku;

import java.io.Serializable;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class SudokuField implements Serializable,CellInterface{
    
    private final int blockSize;
    private final int fieldSize;
    private CellInterface[][] field;

    public SudokuField(final int blocks) 
    {
        blockSize = blocks;
        fieldSize = blockSize * blockSize;
        field = new CellInterface[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; ++i)
        {
            for (int j = 0; j < fieldSize; ++j) {
                field[i][j] = new SudokuCell();
            }
        }
    }

    public int blockSize() {
        return blockSize;
    }

    public int fieldSize() {
        return fieldSize;
    }

    public int variantsPerCell() {
        return fieldSize;
    }

    public int numberOfCells() {
        return fieldSize * fieldSize;
    }

    public void clearOneCell(final int row, final int column) {
        field[row - 1][column - 1].clear();
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                field[i][j].clear();
            }
        }
    }

    public void resetOneCell(final int row, final int column) 
    {
        field[row - 1][column - 1].reset();
    }

    @Override
    public void reset() 
    {
        for (int i = 0; i < fieldSize; ++i) 
        {
            for (int j = 0; j < fieldSize; ++j) 
            {
                field[i][j].reset();
            }
        }
    }

    public boolean isFilledOneCell(final int row, final int column) 
    {
        return field[row - 1][column - 1].isFilled();
    }

    @Override
    public boolean isFilled() 
    {
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize; ++j) {
                if (!((SudokuCell)field[i][j]).isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int get(final int row, final int column) 
    {
        return ((SudokuCell)field[row - 1][column - 1]).get();
    }

    public void set(final int number, final int row, final int column) 
    {
        ((SudokuCell)field[row - 1][column - 1]).set(number);
    }

    public void tryNumber(final int number, final int row, final int column) 
    {
        ((SudokuCell)field[row - 1][column - 1]).tryNumber(number);
    }

    public boolean numberHasBeenTried(final int number, final int row, final int column) 
    {
        return ((SudokuCell)field[row - 1][column - 1]).isTried(number);
    }

    public int numberOfTriedNumbers(final int row, final int column) 
    {
        return ((SudokuCell)field[row - 1][column - 1]).numberOfTried();
    }

    public boolean checkNumberBox(final int number, final int row, final int column)
    {
        int r = row, c = column;
        if (r % blockSize == 0) {
            r -= blockSize - 1;
        } else {
            r = (r / blockSize) * blockSize + 1;
        }
        if (c % blockSize == 0) {
            c -= blockSize - 1;
        } else {
            c = (c / blockSize) * blockSize + 1;
        }
        for (int i = r; i < r + blockSize; ++i) {
            for (int j = c; j < c + blockSize; ++j) {
                if (((SudokuCell)field[i - 1][j - 1]).isFilled() && (((SudokuCell)field[i - 1][j - 1]).get() == number)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkNumberRow(final int number, final int row) 
    {
        for (int i = 0; i < fieldSize; ++i) {
            if (((SudokuCell)field[row - 1][i]).isFilled() && ((SudokuCell)field[row - 1][i]).get() == number) {
                return false;
            }
        }
        return true;
    }

    public boolean checkNumberColumn(final int number, final int column) 
    {
        for (int i = 0; i < fieldSize; ++i) {
            if (((SudokuCell)field[i][column - 1]).isFilled() && ((SudokuCell)field[i][column - 1]).get() == number) {
                return false;
            }
        }
        return true;
    }

    public boolean checkNumberField(final int number, final int row, final int column) 
    {
        return (checkNumberBox(number, row, column)
                && checkNumberRow(number, row)
                && checkNumberColumn(number, column));
    }

    public Index nextCell(final int row, final int column) 
    {
        int r = row, c = column;
        if (c < fieldSize) {
            ++c;
        } else {
            c = 1;
            ++r;
        }
        return new Index(r, c);
    }

    public int getRandomIndex() 
    {
        return (int) (Math.random() * 10) % fieldSize + 1;
    }
    
    public void generateFullField(final int row, final int column) 
    {
        if (!this.isFilledOneCell(this.fieldSize(), this.fieldSize())) 
        {
            while (this.numberOfTriedNumbers(row, column) < this.variantsPerCell()) 
            {
                int candidate = 0;
                do {
                    candidate = this.getRandomIndex();
                } while (this.numberHasBeenTried(candidate, row, column));
                if (this.checkNumberField(candidate, row, column)) 
                {
                    this.set(candidate, row, column);
                    Index nextCell = this.nextCell(row, column);
                    if (nextCell.getRow() <= this.fieldSize()
                            && nextCell.getColumn() <=this.fieldSize()) {
                        generateFullField(nextCell.getRow(), nextCell.getColumn());
                    }
                } else {
                    this.tryNumber(candidate, row, column);
                }
            }
            if (!this.isFilledOneCell(this.fieldSize(), this.fieldSize())) 
            {
                this.resetOneCell(row, column);
            }
        }
    }
}
