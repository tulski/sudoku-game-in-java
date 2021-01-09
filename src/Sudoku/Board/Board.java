package Sudoku.Board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Board implements Serializable {
    // template, right solved sudoku board
    private final int[][] solvedArray = {
            {2, 9, 5, 7, 4, 3, 8, 6, 1},
            {4, 3, 1, 8, 6, 5, 9, 2, 7},
            {8, 7, 6, 1, 9, 2, 5, 4, 3},
            {3, 8, 7, 4, 5, 9, 2, 1, 6},
            {6, 1, 2, 3, 8, 7, 4, 9, 5},
            {5, 4, 9, 2, 1, 6, 7, 3, 8},
            {7, 6, 3, 5, 2, 4, 1, 8, 9},
            {9, 2, 8, 6, 7, 1, 3, 5, 4},
            {1, 5, 4, 9, 3, 8, 6, 7, 2}
    };
    private ArrayList<ArrayList<Field>> board;
    private final Difficulty difficulty;

    public Board(Difficulty difficulty) {
        this.difficulty = difficulty;
        generateBoard();
    }

    public ArrayList<ArrayList<Field>> getBoard() {
        return this.board;
    }

    private void generateBoard() {
        shuffleBoard(this.solvedArray, 20);

        ArrayList<ArrayList<Field>> userBoard = new ArrayList<ArrayList<Field>>();
        for (int i = 0; i < 9; i++) {
            ArrayList<Field> row = new ArrayList<Field>();
            for (int j = 0; j < 9; j++) {
                Field field = new Field(this.solvedArray[i][j]);
                row.add(field);
            }
            userBoard.add(row);
        }

        this.board = userBoard;

        for (int i = 0; i < this.difficulty.getNumOfFieldsToShow(); i++) {
            solveRandomFiled();
        }
    }

    private void shuffleBoard(int[][] arrayToShuffle, int shuffles) {
        int n = new Random().nextInt(2 + 1) * 3;
        boolean m = new Random().nextBoolean();

        for (int i = 0; i < shuffles; i++) {
            switch (new Random().nextInt(5 + 1)) {
                case 0:
                    swapRows(arrayToShuffle, n + 0, m ? n + 1 : n + 2);
                    break;
                case 1:
                    swapRows(arrayToShuffle, n + 1, m ? n + 0 : n + 2);
                    break;
                case 2:
                    swapRows(arrayToShuffle, n + 2, m ? n + 0 : n + 1);
                    break;
                case 3:
                    swapColumns(arrayToShuffle, n + 0, m ? n + 1 : n + 2);
                    break;
                case 4:
                    swapColumns(arrayToShuffle, n + 1, m ? n + 0 : n + 2);
                    break;
                case 5:
                    swapColumns(arrayToShuffle, n + 2, m ? n + 0 : n + 1);
                    break;
            }
        }
    }

    private void swapRows(int[][] array, int num1, int num2) {
        int temp = 0;
        for (int i = 0; i < array[0].length; i++) {
            temp = array[i][num1];
            array[i][num1] = array[i][num2];
            array[i][num2] = temp;
        }
    }

    private void swapColumns(int[][] array, int num1, int num2) {
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            temp = array[num1][i];
            array[num1][i] = array[num2][i];
            array[num2][i] = temp;
        }
    }

    public void solveRandomFiled() {
        int randomRow = new Random().nextInt(8 + 1);
        int randomColumn = new Random().nextInt(8 + 1);
        if (this.board.get(randomRow).get(randomColumn).isEmpty()) {
            this.board.get(randomRow).get(randomColumn).solve();
        } else {
            solveRandomFiled();
        }
    }

    public void inputValue(int row, int column, int value) {
        this.board.get(row).get(column).setValue(value);
    }

    public boolean isSolved() {
        for (ArrayList<Field> row : this.board) {
            for (Field field : row) {
                if (!field.isSolved()) return false;
            }
        }
        return false;
    }

}
