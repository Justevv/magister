package forex.processing;

import lombok.Data;

@Data
public class SimpleGrid {
    private float maxGrid = 0; //максимум сетки
    private float minGrid = 2; //минимум сетки
    private int pulseCount = 0; //счетчик импульсов
    private int rollbackCount = 0;  //счетчик откатов
    private boolean firstDay = true;         //флаг первого дня(для сброса в понедельник)
    private boolean maxMax = false;            //флаг ожидания пробоя МО после повторения максимума
    private int tempRec = 0;
    private float recLow = 0;

}
