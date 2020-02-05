package forex.processing;

import java.util.Calendar;

public class Grid {
    private Calendar buyDataValue;
    private float buyMaxGrid;
    private float buyMinGrid;
    private int buyPulseCount;
    private int buyRollbackCount;
    private double sizeGrid;  //Массив размера сетки

    public double getSizeGrid() {
        return sizeGrid;
    }

    public void setSizeGrid(double sizeGrid) {
        this.sizeGrid = sizeGrid;
    }

    public Calendar getBuyDataValue() {
        return buyDataValue;
    }

    public void setBuyDataValue(Calendar buyDataValue) {
        this.buyDataValue = buyDataValue;
    }

    public float getBuyMaxGrid() {
        return buyMaxGrid;
    }

    public void setBuyMaxGrid(float buyMaxGrid) {
        this.buyMaxGrid = buyMaxGrid;
    }

    public float getBuyMinGrid() {
        return buyMinGrid;
    }

    public void setBuyMinGrid(float buyMinGrid) {
        this.buyMinGrid = buyMinGrid;
    }

    public Integer getBuyPulseCount() {
        return buyPulseCount;
    }

    public void setBuyPulseCount(Integer buyPulseCount) {
        this.buyPulseCount = buyPulseCount;
    }

    public Integer getBuyRollbackCount() {
        return buyRollbackCount;
    }

    public void setBuyRollbackCount(Integer buyRollbackCount) {
        this.buyRollbackCount = buyRollbackCount;
    }

    public Grid(Calendar buyDataValue, float buyMaxGrid, float buyMinGrid, int buyPulseCount, int buyRollbackCount) {
        this.buyDataValue = buyDataValue;
        this.buyMaxGrid = buyMaxGrid;
        this.buyMinGrid = buyMinGrid;
        this.buyPulseCount = buyPulseCount;
        this.buyRollbackCount = buyRollbackCount;
        this.sizeGrid = (buyMaxGrid - buyMinGrid) * 100000;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "buyDataValue=" + buyDataValue +
                ", buyMaxGrid=" + buyMaxGrid +
                ", buyMinGrid=" + buyMinGrid +
                ", buyPulseCount=" + buyPulseCount +
                ", buyRollbackCount=" + buyRollbackCount +
                ", sizeGrid=" + sizeGrid +
                '}';
    }
}
