package sudoku;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SudokuBoard {

    private final int[][] board = new int[9][9];

    public SudokuBoard(int[][] data) {
        if (data == null || data.length != 9) {
            throw new IllegalArgumentException("Board must have 9 rows");
        }
        for (int i = 0; i < 9; i++) {
            if (data[i] == null || data[i].length != 9) {
                throw new IllegalArgumentException("Each row must have 9 columns");
            }
            System.arraycopy(data[i], 0, board[i], 0, 9);
        }
    }

    public int get(int r, int c) {
        return board[r][c];
    }

    public static SudokuBoard fromCSV(String pathStr) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(pathStr));
        int[][] data = new int[9][9];
        int row = 0;

        for (String line : lines) {
            if (line == null) continue;
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            String[] tokens = trimmed.split("\\s*,\\s*|\\s+");
            if (tokens.length < 9) {
                throw new IllegalArgumentException("Each non-empty row must have at least 9 entries");
            }

            for (int col = 0; col < 9; col++) {
                int val;
                try {
                    val = Integer.parseInt(tokens[col]);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Non-integer found at row " + (row + 1) + " col " + (col + 1));
                }
                if (val < 1 || val > 9) {
                    throw new IllegalArgumentException("Values must be in range 1..9 (row " + (row + 1) + ", col " + (col + 1) + ")");
                }
                data[row][col] = val;
            }

            row++;
            if (row == 9) break;
        }

        if (row != 9) {
            throw new IllegalArgumentException("File must contain exactly 9 non-empty rows");
        }

        return new SudokuBoard(data);
    }
    public int[][] asArrayCopy() {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
}
