package sudoku;

import java.util.*;

public class SequentialVerifier implements VerifierInterface {

    @Override
    public List<DuplicateResult> verify(SudokuBoard board) {
        List<DuplicateResult> found = new ArrayList<>();

        for (int r = 0; r < 9; r++) {
            Map<Integer, List<Integer>> occurrences = new HashMap<>();
            for (int c = 0; c < 9; c++) {
                int val = board.get(r, c);
              
                occurrences.computeIfAbsent(val, k -> new ArrayList<>()).add(c + 1);
            }
            int rowIndex = r + 1;
            for (Map.Entry<Integer, List<Integer>> e : occurrences.entrySet()) {
                List<Integer> posList = e.getValue();
                if (posList.size() > 1) {
                    found.add(new DuplicateResult(DuplicateResult.RegionType.ROW, rowIndex, e.getKey(), posList));
                }
            }
        }

        for (int c = 0; c < 9; c++) {
            Map<Integer, List<Integer>> occurrences = new HashMap<>();
            for (int r = 0; r < 9; r++) {
                int val = board.get(r, c);
                occurrences.computeIfAbsent(val, k -> new ArrayList<>()).add(r + 1);
            }
            int colIndex = c + 1;
            for (Map.Entry<Integer, List<Integer>> e : occurrences.entrySet()) {
                List<Integer> posList = e.getValue();
                if (posList.size() > 1) {
                    found.add(new DuplicateResult(DuplicateResult.RegionType.COL, colIndex, e.getKey(), posList));
                }
            }
        }

        for (int box = 0; box < 9; box++) {
            Map<Integer, List<Integer>> occurrences = new HashMap<>();

            int boxRowStart = (box / 3) * 3;
            int boxColStart = (box % 3) * 3;

            int localPos = 0;
            for (int dr = 0; dr < 3; dr++) {
                for (int dc = 0; dc < 3; dc++) {
                    localPos++;
                    int r = boxRowStart + dr;
                    int c = boxColStart + dc;
                    int val = board.get(r, c);
                    occurrences.computeIfAbsent(val, k -> new ArrayList<>()).add(localPos);
                }
            }

            int boxIndex = box + 1;
            for (Map.Entry<Integer, List<Integer>> e : occurrences.entrySet()) {
                List<Integer> posList = e.getValue();
                if (posList.size() > 1) {
                    found.add(new DuplicateResult(DuplicateResult.RegionType.BOX, boxIndex, e.getKey(), posList));
                }
            }
        }

        return found;
    }
}