/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

/**
 *
 * @author USER
 */
public interface GameStrategy {
    public void generateGame();
    public int[][] getGame();
}
