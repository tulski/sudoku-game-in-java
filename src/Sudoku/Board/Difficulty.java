package Sudoku.Board;

public enum Difficulty {
//    EASY(36, 10),
    EASY(80, 10),
    MEDIUM(31,5),
    HARD(26,3);

    private final int numOfFieldsToShow;
    private final int numOfHints;

    private  Difficulty(int numOfFieldsToShow, int numOfHints) {
        this.numOfFieldsToShow = numOfFieldsToShow;
        this.numOfHints = numOfHints;
    }

    public int getNumOfFieldsToShow() {
        return this.numOfFieldsToShow;
    }

    public int getNumOfHints() {
        return this.numOfHints;
    }
}
