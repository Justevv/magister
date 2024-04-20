package forex.entity;

import lombok.Data;

@Data
public class SimpleGrid {
    private int maxGrid = 0; //максимум сетки
    private int minGrid = 20000; //минимум сетки
    private int pulseCount = 0; //счетчик импульсов
    private int rollbackCount = 0;  //счетчик откатов
    private boolean maxMax = false;            //флаг ожидания пробоя МО после повторения максимума
    private int tempRec = 0;
    private int recLow = 0;

}
