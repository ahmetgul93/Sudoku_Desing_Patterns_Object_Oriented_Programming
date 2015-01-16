/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author USER
 */
public class HardGame implements GameStrategy {

    private int[][] game;

    public HardGame(int[][] game) {
        this.game = game;
    }

    @Override
    public int[][] getGame() {
        return game;
    }

    @Override
    public void generateGame() {
        List<Integer> positions = new ArrayList();

        for (int i = 0; i < 80; i++) {
            positions.add(i);
        }

        Collections.shuffle(positions);
        generateGame(positions);
    }

    private void generateGame(List<Integer> positions) {
        while (positions.size() > 0) {
            int position = positions.remove(0);

            int x = position / 9;
            int y = position % 9;

            int temp = game[x][y];
            game[x][y] = 0;
            if (!isValid()) {
                game[x][y] = temp;
            }
        }
    }

    private boolean isValid() {
        return isValid(0, new int[]{0});
    }

    private boolean isValid(int index, int[] numberOfSolutions) {
        if (index > 80) {
            return ++numberOfSolutions[0] == 1;
        }

        int x = index / 9;
        int y = index % 9;

        if (game[x][y] == 0) {
            List<Integer> numbers = new ArrayList();
            for (int i = 1; i <= 9; i++) {
                numbers.add(i);
            }

            while (numbers.size() > 0) {
                int number = getNextPossibleNumber(x, y, numbers);
                if (number == -1) {
                    break;
                }
                game[x][y] = number;

                if (!isValid(index + 1, numberOfSolutions)) {
                    game[x][y] = 0;
                    return false;
                }
                game[x][y] = 0;
            }
        } else if (!isValid(index + 1, numberOfSolutions)) {
            return false;
        }

        return true;
    }

    private int getNextPossibleNumber(int x, int y, List<Integer> numbers) {
        while (numbers.size() > 0) {
            int number = numbers.remove(0);
            if (isPossibleX(x, number)
                    && isPossibleY(y, number)
                    && isPossibleBlock(x, y, number)) {
                return number;
            }
        }
        return -1;
    }

    private boolean isPossibleX(int row, int number) {
        for (int i = 0; i < 9; ++i) {
            if (game[row][i] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean isPossibleY(int column, int number) {
        for (int i = 0; i < 9; ++i) {
            if (game[i][column] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean isPossibleBlock(int row, int column, int number) {
        int[] locationPoints = getLocation(row, column);

        for (int m = locationPoints[0]; m < locationPoints[0] + 3; m++) {
            for (int n = locationPoints[1]; n < locationPoints[1] + 3; n++) {
                if (game[m][n] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] getLocation(int x, int y) {
        int[] locationPoints = new int[2];
        if (x == 0 || x == 1 || x == 2) {
            locationPoints[0] = 0;
        } else if (x == 3 || x == 4 || x == 5) {
            locationPoints[0] = 3;
        } else {
            locationPoints[0] = 6;
        }

        if (y == 0 || y == 1 || y == 2) {
            locationPoints[1] = 0;
        } else if (y == 3 || y == 4 || y == 5) {
            locationPoints[1] = 3;
        } else {
            locationPoints[1] = 6;
        }

        return (locationPoints);
    }
}


