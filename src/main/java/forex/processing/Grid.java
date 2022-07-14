package forex.processing;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Grid {
    private LocalDateTime buyDataValue;
    private float buyMaxGrid;
    private float buyMinGrid;
    private int buyPulseCount;
    private int buyRollbackCount;
    private float sizeGrid;
    private int step;

    public Grid(LocalDateTime buyDataValue, float buyMaxGrid, float buyMinGrid, int buyPulseCount, int buyRollbackCount) {
        this.buyDataValue = buyDataValue;
        this.buyMaxGrid = buyMaxGrid;
        this.buyMinGrid = buyMinGrid;
        this.buyPulseCount = buyPulseCount;
        this.buyRollbackCount = buyRollbackCount;
        this.sizeGrid = (buyMaxGrid - buyMinGrid) * 100000;
    }

}
