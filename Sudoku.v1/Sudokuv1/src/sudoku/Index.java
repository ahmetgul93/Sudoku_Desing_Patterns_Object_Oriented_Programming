/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author USER
 */
public class Index {
    private int row;
    private int column;
    
    public Index(int row,int column)
    {
        this.row = row;
        this.column=column;
    }
    
    public int getRow()
    {
       return row;
    }
    public int getColumn()
    {
        return column;
    }
    
    public void setRow(int i)
    {
        this.row=i;
    }
    
    public void setColumn(int j)
    {
        this.column=j;
    }
}

