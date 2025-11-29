package sudoku;

import java.util.*;
import java.util.concurrent.*;

public class ThreeModeVerifier implements VerifierInterface {

    @Override
    public List<DuplicateResult> verify(SudokuBoard board) throws InterruptedException {
        List<DuplicateResult> shared = Collections.synchronizedList(new ArrayList<>());

        ExecutorService exec = Executors.newFixedThreadPool(3);
        List<Callable<Void>> tasks = new ArrayList<>();
        tasks.add(() -> {
            for (int r = 0; r < 9; r++) {
                Map<Integer, List<Integer>> occ = new HashMap<>();
                for (int c = 0; c < 9; c++) {
                    int v = board.get(r, c);
                    occ.computeIfAbsent(v, k -> new ArrayList<>()).add(c + 1);
                }
                int rowIndex = r + 1;
                for (Map.Entry<Integer, List<Integer>> e : occ.entrySet()) {
                    if (e.getValue().size() > 1) {
                        shared.add(new DuplicateResult(DuplicateResult.RegionType.ROW, rowIndex, e.getKey(), e.getValue()));
                    }
                }
            }
            return null;
        });

        tasks.add(() -> {
            for (int c = 0; c < 9; c++) {
                Map<Integer, List<Integer>> occ = new HashMap<>();
                for (int r = 0; r < 9; r++) {
                    int v = board.get(r, c);
                    occ.computeIfAbsent(v, k -> new ArrayList<>()).add(r + 1);
                }
                int colIndex = c + 1;
                for (Map.Entry<Integer, List<Integer>> e : occ.entrySet()) {
                    if (e.getValue().size() > 1) {
                        shared.add(new DuplicateResult(DuplicateResult.RegionType.COL, colIndex, e.getKey(), e.getValue()));
                    }
                }
            }
            return null;
        });
        tasks.add(() -> {
            for (int box = 0; box < 9; box++) {
                Map<Integer, List<Integer>> occ = new HashMap<>();
                int rowStart = (box / 3) * 3;
                int colStart = (box % 3) * 3;
                int local = 0;
                for (int dr = 0; dr < 3; dr++) {
                    for (int dc = 0; dc < 3; dc++) {
                        local++;
                        int r = rowStart + dr;
                        int c = colStart + dc;
                        int v = board.get(r, c);
                        occ.computeIfAbsent(v, k -> new ArrayList<>()).add(local);
                    }
                }
                int boxIndex = box + 1;
                for (Map.Entry<Integer, List<Integer>> e : occ.entrySet()) {
                    if (e.getValue().size() > 1) {
                        shared.add(new DuplicateResult(DuplicateResult.RegionType.BOX, boxIndex, e.getKey(), e.getValue()));
                    }
                }
            }
            return null;
        });

        exec.invokeAll(tasks); 
        exec.shutdown();
        exec.awaitTermination(10, TimeUnit.SECONDS); 

        return shared;
    }
}
