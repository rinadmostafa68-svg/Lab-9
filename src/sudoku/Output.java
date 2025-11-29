package sudoku;

import java.util.*;
import java.util.stream.*;

public class Output {


    public static String formatResults(List<DuplicateResult> results) {
        if (results == null || results.isEmpty()) {
            return "        VALID";
        }
        Map<DuplicateResult.RegionType, List<DuplicateResult>> grouped = new HashMap<>();
        for (DuplicateResult r : results) {
            grouped.computeIfAbsent(r.getType(), k -> new ArrayList<>()).add(r);
        }

        StringBuilder out = new StringBuilder();

        List<DuplicateResult> rows = grouped.getOrDefault(DuplicateResult.RegionType.ROW, Collections.emptyList());
        sortByRegionThenValue(rows);
        for (DuplicateResult r : rows) {
            out.append(r.toString()).append(System.lineSeparator());
        }

        out.append("------------------------------------------").append(System.lineSeparator());

        List<DuplicateResult> cols = grouped.getOrDefault(DuplicateResult.RegionType.COL, Collections.emptyList());
        sortByRegionThenValue(cols);
        for (DuplicateResult r : cols) {
            out.append(r.toString()).append(System.lineSeparator());
        }

        out.append("------------------------------------------").append(System.lineSeparator());

        List<DuplicateResult> boxes = grouped.getOrDefault(DuplicateResult.RegionType.BOX, Collections.emptyList());
        sortByRegionThenValue(boxes);
        for (DuplicateResult r : boxes) {
            out.append(r.toString()).append(System.lineSeparator());
        }

        String s = out.toString().trim();
        return s;
    }

    private static void sortByRegionThenValue(List<DuplicateResult> list) {
        Collections.sort(list, new Comparator<DuplicateResult>() {
            @Override
            public int compare(DuplicateResult a, DuplicateResult b) {
                int cmp = Integer.compare(a.getRegionIndex(), b.getRegionIndex());
                if (cmp != 0) return cmp;
                return Integer.compare(a.getValue(), b.getValue());
            }
        });
    }

    public static void printResults(List<DuplicateResult> results) {
        System.out.println(formatResults(results));
    }
}