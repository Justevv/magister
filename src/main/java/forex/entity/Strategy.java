package forex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Strategy {
    CLASSIC("Classic", OpenStrategy.FIBONACCI1000),
    MD61SL38("61% МП sl 38", OpenStrategy.FIBONACCI0618),
    MD61SL0("61% МП sl 0", OpenStrategy.FIBONACCI0618),
    MD38SL2000("38 мп sl -200ПП", OpenStrategy.FIBONACCI0382),
    ACSL38CLASSIC1("AC sl 38 classic sl +1", OpenStrategy.FIBONACCI0618),
    ACSL0CLASSIC1("AC sl 0 classic sl +1", OpenStrategy.FIBONACCI0618),
    MR38SL0ACSL1CLASSIC62("38 МО sl 0 ac sl +1 classic sl .618", OpenStrategy.FIBONACCI0382),
    MD38SLO("38 мп sl 0", OpenStrategy.FIBONACCI0382);
    @Getter
    private String description;
    @Getter
    private OpenStrategy strategy;

}
