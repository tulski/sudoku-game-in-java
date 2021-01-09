package Sudoku.Game;

import Sudoku.Board.Board;
import Sudoku.Board.Difficulty;
import Sudoku.Cli;

public class Game {
    private GameStats gameStats;
    private Board board;

    public Game() {
        Cli.setupCli();
    }

    public void start() {
        if (Cli.promptStartMenu() ==  "New Game") {
            newGame();
        } else {
            continueGame();
        }
    }

    public void newGame() {
        switch (Cli.promptDifficultyMenu()) {
            case "Easy":
                this.board = new Board(Difficulty.EASY);
                this.gameStats = new GameStats(Difficulty.EASY.getNumOfHints());
                break;
            case "Medium":
                this.board = new Board(Difficulty.MEDIUM);
                this.gameStats = new GameStats(Difficulty.MEDIUM.getNumOfHints());
                break;
            case "Hard":
                this.board = new Board(Difficulty.HARD);
                this.gameStats = new GameStats(Difficulty.HARD.getNumOfHints());
                break;
        }

        playGame();
    }

    public void continueGame() {
        // TODO
        this.board = new Board(Difficulty.EASY);
        this.gameStats = new GameStats(Difficulty.EASY.getNumOfHints());
        playGame();
    }

    public void playGame() {
        Cli.printGameInterface(board.getBoard(), gameStats);
        switch (Cli.promptPlayMenu()) {
            case "Guess":
                guess();
                break;
            case "Hint":
                hint();
                break;
            case "Check":
                check();
                break;
            case "Save and exit":
                break;
        }
    }

    private void guess() {
        int row = Cli.prompIntValue("Row", 1, 9);
        int column = Cli.prompIntValue("Column", 1, 9);
        int value = Cli.prompIntValue("Value", 1, 9);
        this.board.inputValue(row - 1, column - 1, value);
        playGame();
    }

    private void hint() {
        if (this.gameStats.getHints() > 0) {
            this.gameStats.subtractHint();
            this.board.solveRandomFiled();
        }
        playGame();
    }

    private void check() {
        if (!this.board.isSolved()) {
            this.gameStats.subtractLive();
            if (this.gameStats.getLives() > 0) {
                playGame();
            } else {
                gameOver();
            }
        } else {
            victory();
        }
    }

    private void gameOver() {
        Cli.printGameInterface(board.getBoard(), gameStats);
        Cli.println("GAME OVER");
    }

    private void victory() {
        Cli.printGameInterface(board.getBoard(), gameStats);
        Cli.println("VICTORY");
    }
}
