package forex.processing;

import java.util.Date;

public class Grid {
    private Date buyDataValue;
    private Double buyMaxGrid;
    private Double buyMinGrid;
    private Integer buyPulseCount;
    private Integer buyRollbackCount;
    private double sizeGrid;  //Массив размера сетки

    public double getSizeGrid() {
        return sizeGrid;
    }

    public void setSizeGrid(double sizeGrid) {
        this.sizeGrid = sizeGrid;
    }

    public Date getBuyDataValue() {
        return buyDataValue;
    }

    public void setBuyDataValue(Date buyDataValue) {
        this.buyDataValue = buyDataValue;
    }

    public Double getBuyMaxGrid() {
        return buyMaxGrid;
    }

    public void setBuyMaxGrid(Double buyMaxGrid) {
        this.buyMaxGrid = buyMaxGrid;
    }

    public Double getBuyMinGrid() {
        return buyMinGrid;
    }

    public void setBuyMinGrid(Double buyMinGrid) {
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

    public Grid(Date buyDataValue, Double buyMaxGrid, Double buyMinGrid, Integer buyPulseCount, Integer buyRollbackCount) {
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
