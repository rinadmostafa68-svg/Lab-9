package sudoku;

import java.util.*;

public interface VerifierInterface {
    List<DuplicateResult> verify(SudokuBoard board) throws InterruptedException;
}
