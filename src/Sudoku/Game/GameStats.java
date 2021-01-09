package Sudoku.Game;

public class GameStats {
    private int lives;
    private int hints;

    public GameStats(int hints) {
        this.lives = 3;
        this.hints = hints;
    }

    public void subtractLive() {
        this.lives--;
    }

    public void subtractHint() {
        this.hints--;
    }

    public int getLives() {
        return this.lives;
    }

    public int getHints() {
        return this.hints;
    }
}
