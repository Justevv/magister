package forex.processing;

import lombok.Data;

import java.util.Calendar;

@Data
public class Grid {
    private Calendar buyDataValue;
    private float buyMaxGrid;
    private float buyMinGrid;
    private int buyPulseCount;
    private int buyRollbackCount;
    private float sizeGrid;

    public Grid(Calendar buyDataValue, float buyMaxGrid, float buyMinGrid, int buyPulseCount, int buyRollbackCount) {
        this.buyDataValue = buyDataValue;
        this.buyMaxGrid = buyMaxGrid;
        this.buyMinGrid = buyMinGrid;
        this.buyPulseCount = buyPulseCount;
        this.buyRollbackCount = buyRollbackCount;
        this.sizeGrid = (buyMaxGrid - buyMinGrid) * 100000;
    }

}
