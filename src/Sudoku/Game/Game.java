package Sudoku.Game;

import Sudoku.Board.Board;
import Sudoku.Board.Difficulty;
import Sudoku.Cli;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Game {
    private GameStats gameStats;
    private Board board;

    public Game() {
        Cli.setupCli();
    }

    public void start() {
        File[] fileList = getFilesInSavesDirectory();
        List<String> options = new ArrayList<>(Collections.singletonList("New Game"));
        if (fileList.length > 1) {
            options.add("Continue");
        }
        Cli.printLogo();
        if (Cli.promptMenu(options) == "New Game") {
            newGame();
        } else {
            continueGame(fileList);
        }
    }

    public void newGame() {
        List<String> options = new ArrayList<>(Arrays.asList("Easy", "Medium", "Hard"));
        switch (Cli.promptMenu(options)) {
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

    public void continueGame(File[] fileList) {
        for (int i = 0; i < fileList.length; i++) {
            Cli.println(i + ". " + fileList[i].getName().replace(".txt", ""));
        }
        int userSelection = Cli.prompIntValue("Index", 0, fileList.length);
        this.loadGameFromFile(fileList[userSelection]);
        playGame();
    }

    public void playGame() {
        List<String> options = new ArrayList<>(Arrays.asList("Guess", "Erase", "Hint", "Check", "Save", "Exit"));
        Cli.printGameInterface(board.getBoard(), gameStats);
        switch (Cli.promptMenu(options)) {
            case "Guess":
                guess();
                break;
            case "Erase":
                erase();
                break;
            case "Hint":
                hint();
                break;
            case "Check":
                check();
                break;
            case "Save":
                save();
                break;
            case "Exit":
                System.exit(1);
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

    private void erase() {
        int row = Cli.prompIntValue("Row", 1, 9);
        int column = Cli.prompIntValue("Column", 1, 9);
        this.board.inputValue(row - 1, column - 1, 0);
        playGame();
    }

    private void hint() {
        if (this.gameStats.getHints() > 0) {
            if (this.board.isAnyEmpty()) {
                this.gameStats.subtractHint();
                this.board.solveRandomFiled();
            }
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

    private void save() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy:HH:mm");
        String fileName = simpleDateFormat.format(new Date()) + " LIVES: " + this.gameStats.getLives() + " HINTS: " + this.gameStats.getHints() + ".txt";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File dir = new File("saves");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileName);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            stringBuilder.append(this.gameStats.getLives());
            stringBuilder.append("\n");
            stringBuilder.append(this.gameStats.getHints());
            stringBuilder.append("\n");
            stringBuilder.append(this.board.mapFieldValuesToString("value"));
            stringBuilder.append("\n");
            stringBuilder.append(this.board.mapFieldValuesToString("solved"));
            stringBuilder.append("\n");
            stringBuilder.append(this.board.mapFieldValuesToString("isMutable"));
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playGame();
    }

    private void gameOver() {
        Cli.printGameInterface(board.getBoard(), gameStats);
        Cli.println("GAME OVER");
    }

    private void victory() {
        Cli.printGameInterface(board.getBoard(), gameStats);
        Cli.println("VICTORY");
    }

    private File[] getFilesInSavesDirectory() {
        File dir = new File("saves");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.listFiles();
    }

    private void loadGameFromFile(File file) {
        try {
            Scanner reader = new Scanner(file);
            int lives = Integer.parseInt(reader.nextLine());
            int hints = Integer.parseInt(reader.nextLine());
            this.gameStats = new GameStats(lives, hints);
            this.board = new Board(Difficulty.EASY);
            reader.nextLine();
            ArrayList<ArrayList<Integer>> valueArray = readArrayFromScanner(reader);
            reader.nextLine();
            ArrayList<ArrayList<Integer>> solvedArray = readArrayFromScanner(reader);
            reader.nextLine();
            ArrayList<ArrayList<Integer>> isMutableArray = readArrayFromScanner(reader);
            this.board.mapArraysToFieldValues(valueArray, solvedArray, isMutableArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<ArrayList<Integer>> readArrayFromScanner(Scanner scanner) {
        ArrayList<ArrayList<Integer>> array = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(scanner.nextInt());
            }
            array.add(row);
        }
        return array;
    }
}