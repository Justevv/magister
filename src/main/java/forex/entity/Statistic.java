package forex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistic  {
    private Strategy strategy;
    private int year;

}
