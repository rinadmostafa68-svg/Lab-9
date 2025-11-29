package sudoku;

import java.util.*;

public class DuplicateResult {

    public enum RegionType { ROW, COL, BOX }

    private final RegionType type;
    private final int regionIndex;       
    private final int value;             
    private final List<Integer> positions; 

    public DuplicateResult(RegionType type, int regionIndex, int value, List<Integer> positions) {
        this.type = type;
        this.regionIndex = regionIndex;
        this.value = value;
        this.positions = new ArrayList<>(positions);
    }

    public RegionType getType() {
        return type;
    }

    public int getRegionIndex() {
        return regionIndex;
    }

    public int getValue() {
        return value;
    }

    public List<Integer> getPositions() {
        return Collections.unmodifiableList(positions);
    }

    @Override
    public String toString() {
        return type.name() + " " + regionIndex + ", #" + value + ", " + positions;
    }
}