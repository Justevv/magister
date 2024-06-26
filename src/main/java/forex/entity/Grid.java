package forex.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Grid {
    private LocalDateTime buyDataValue;
    private int buyMaxGrid;
    private int buyMinGrid;
    private int buyPulseCount;
    private int buyRollbackCount;
    private int sizeGrid;
    private List<Integer> steps;
    private MaximumRollback maximumRollback;
    private MaximumLevel maximumLevel;
    private MaximumDrawdown maximumDrawdown;
    private EMAIntersect emaIntersect;
    private boolean m3Ok;
    private List<Order> orders;

    public Grid(LocalDateTime buyDataValue, int buyMaxGrid, int buyMinGrid, int buyPulseCount, int buyRollbackCount) {
        this.buyDataValue = buyDataValue;
        this.buyMaxGrid = buyMaxGrid;
        this.buyMinGrid = buyMinGrid;
        this.buyPulseCount = buyPulseCount;
        this.buyRollbackCount = buyRollbackCount;
        this.sizeGrid = (buyMaxGrid - buyMinGrid);
        maximumDrawdown = new MaximumDrawdown();
        maximumLevel = new MaximumLevel();
        maximumRollback = new MaximumRollback();
        emaIntersect = new EMAIntersect();
        steps = new ArrayList<>();
        orders = new ArrayList<>();
    }

}
