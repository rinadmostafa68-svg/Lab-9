package sudoku;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String path;
        int mode;
 //***************************************************** if CLI
        if (args.length == 2) {
            path = args[0];

            try {
                mode = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                System.err.println("Mode must be 0, 3, or 27.");
                return;
            }

            runVerifier(path, mode);
            return;
        }
//**********************************************************if input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter CSV file path: ");
        path = sc.nextLine().trim();

        System.out.print("Enter mode (0 = sequential, 3 = three-thread, 27 = twenty-seven-thread): ");
        mode = sc.nextInt();

        System.out.println("\n        Sudoku Verifier\n");

        runVerifier(path, mode);

        sc.close();
    }

    private static void runVerifier(String path, int mode) {
        try {
            SudokuBoard board = SudokuBoard.fromCSV(path);
            VerifierInterface verifier = VerifierFactory.getVerifier(mode);

            List<DuplicateResult> results = verifier.verify(board);

            System.out.println(Output.formatResults(results));

        } catch (IOException ioe) {
            System.err.println("File error: " + ioe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.err.println("Invalid input: " + iae.getMessage());
        } catch (InterruptedException ie) {
            System.err.println("Verification interrupted.");
            Thread.currentThread().interrupt();
        }
    }
}
