package forex.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Strategy {
    CLASSIC("Classic", OpenPrice.FIBONACCI1000, OpenStrategy.CLASSIC),
    MD61SL38("61% МП sl 38", OpenPrice.FIBONACCI0618, OpenStrategy.MAXIMUM_DRAWDOWN_61),
    MD61SL0("61% МП sl 0", OpenPrice.FIBONACCI0618, OpenStrategy.MAXIMUM_DRAWDOWN_61),
    MD38SL2000("38 мп sl -200ПП", OpenPrice.FIBONACCI0382, OpenStrategy.MAXIMUM_DRAWDOWN_38),
    ACSL38CLASSIC1("AC sl 38 classic sl +1", OpenPrice.FIBONACCI0618, OpenStrategy.AC),
    ACSL0CLASSIC1("AC sl 0 classic sl +1", OpenPrice.FIBONACCI0618, OpenStrategy.AC),
    MR38SL0ACSL1CLASSIC62("38 МО sl 0 ac sl +1 classic sl .618", OpenPrice.FIBONACCI0382, OpenStrategy.MAXIMUM_ROLLBACK_38),
    MD38SLO("38 мп sl 0", OpenPrice.FIBONACCI0382, OpenStrategy.MAXIMUM_DRAWDOWN_38);
    @Getter
    private final String description;
    @Getter
    private final OpenPrice openPrice;
    @Getter
    private final OpenStrategy openStrategy;

}
