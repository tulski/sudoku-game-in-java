package Sudoku;

import Sudoku.Board.Field;
import Sudoku.Game.GameStats;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.ArrayList;

public class Cli {
    private static TextIO textIO;
    private static TextTerminal terminal;

    public static void setupCli() {
        textIO = TextIoFactory.getTextIO();
        terminal = textIO.getTextTerminal();
        terminal.getProperties().setPromptBold(false);
        terminal.getProperties().setPromptColor(Color.GREEN);
        terminal.setBookmark("clear");
    }

    public static void printGameInterface(ArrayList<ArrayList<Field>> board, GameStats gameStats) {
        terminal.resetToBookmark("clear");
        printLogo();
        printGameStats(gameStats);
        printBoard(board);
    }

    private static void printLogo() {
        terminal.println("\n ┏━━━┓    ┏┓   ┓     \n" +
                " ┃┏━┓┃    ┃┃   ┃     \n" +
                " ┃┗━━┓┓┏┓━┛┃━━┓┃┏┓┓┏┓\n" +
                " ┗━━┓┃┃┃┃┏┓┃┏┓┃┗┛┛┃┃┃\n" +
                " ┃┗━┛┃┗┛┃┗┛┃┗┛┃┏┓┓┗┛┃\n" +
                " ┗━━━┛━━┛━━┛━━┛┛┗┛━━┛\n");
    }

    private static void printBoard(ArrayList<ArrayList<Field>> board) {
        terminal.println("  |1|2|3|4|5|6|7|8|9|");
        for (int i = 0; i < board.toArray().length; i++) {
            terminal.printf(" %d|", i + 1);
            board.get(i).forEach(field -> printField(field));
            terminal.println();
        }
    }

    private static void printGameStats(GameStats gameStats) {
        terminal.getProperties().setPromptColor(Color.RED);
        if (gameStats.getLives() == 1) {
            terminal.print(" ❤     ");
        } else if (gameStats.getLives() == 2) {
            terminal.print(" ❤ ❤   ");
        } else if (gameStats.getLives() == 3) {
            terminal.print(" ❤ ❤ ❤ ");
        } else {
            terminal.print("     ");
        }
        terminal.getProperties().setPromptColor(Color.GREEN);
        terminal.printf("      HINTS:%2d\n", gameStats.getHints());
    }

    private static void printField(Field field) {
        if (!field.getIsMutable()) {
            terminal.getProperties().setPromptBold(true);
            terminal.getProperties().setPromptColor(Color.CYAN);
            terminal.printf("%d", field.getValue());
            terminal.getProperties().setPromptBold(false);
            terminal.getProperties().setPromptColor(Color.GREEN);
        } else if (field.isEmpty()) {
            terminal.print(" ");
        } else {
            terminal.getProperties().setPromptColor(Color.ORANGE);
            terminal.printf("%d", field.getValue());
            terminal.getProperties().setPromptColor(Color.GREEN);
        }
        terminal.print("|");
    }

    public static String promptStartMenu() {
        printLogo();
        return textIO.newStringInputReader()
                .withNumberedPossibleValues("New Game", "Continue")
                .withDefaultValue("New Game")
                .read();
    }

    public static String promptDifficultyMenu() {
        return textIO.newStringInputReader()
                .withNumberedPossibleValues("Easy", "Medium", "Hard")
                .withDefaultValue("Easy")
                .read();
    }

    public static String promptPlayMenu() {
        return textIO.newStringInputReader()
                .withNumberedPossibleValues("Guess", "Hint", "Check", "Save and exit")
                .withDefaultValue("Guess")
                .read();
    }

    public static int prompIntValue(String title, int minVal, int maxVal) {
        return textIO.newIntInputReader()
                .withMinVal(minVal)
                .withMaxVal(maxVal)
                .read(title);
    }

    public static void println(String string) {
         terminal.println(string);
    }
}
