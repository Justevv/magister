package forex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Statistic implements Comparable<Statistic> {
    private Strategy strategy;
    private int year;


    @Override
    public int compareTo(Statistic o) {
        if (year > o.getYear()) {
            return strategy.compareTo(o.getStrategy());
        } else if (year == o.getYear()) {
            return strategy.compareTo(o.getStrategy());
        } else if (year < o.getYear()) {
            return strategy.compareTo(o.getStrategy());
        }
        return 0;
    }

}
