package Sudoku.Board;

import java.io.Serializable;

public class Field implements Serializable {
    private final int solved;
    private int value;
    private boolean isMutable;

    public Field(int solved) {
        this.solved = solved;
        this.value = 0;
        this.isMutable = true;
    }

    public Field(int value, int solved, boolean isMutable) {
        this.value = value;
        this.solved = solved;
        this.isMutable = isMutable;
    }

    public boolean isEmpty() {
        return this.value == 0;
    }

    public void solve() {
        this.value = solved;
        this.isMutable = false;
    }

    public boolean isSolved() {
        if (!this.isMutable) return true;
        else return this.solved == this.value;
    }

    public void setValue(int value) {
        if (isMutable) {
            this.value = value;
        }
    }

    public int getSolved() {
        return this.solved;
    }

    public int getValue() {
        return this.value;
    }

    public boolean getIsMutable() {
        return this.isMutable;
    }

}
