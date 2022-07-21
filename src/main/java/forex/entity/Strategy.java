package forex.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Strategy {
    CLASSIC("Classic", OpenStrategy.CLASSIC),
    MD61SL38("61% МП sl 38", OpenStrategy.MAXIMUM_DRAWDOWN_61),
    MD61SL0("61% МП sl 0", OpenStrategy.MAXIMUM_DRAWDOWN_61),
    MD38SL2000("38 мп sl -200ПП", OpenStrategy.MAXIMUM_DRAWDOWN_38),
    ACSL38CLASSIC1("AC sl 38 classic sl +1", OpenStrategy.AC),
    ACSL0CLASSIC1("AC sl 0 classic sl +1", OpenStrategy.AC),
    MR38SL0ACSL1CLASSIC62("38 МО sl 0 ac sl +1 classic sl .618", OpenStrategy.MAXIMUM_ROLLBACK_38),
    MR38SL0ACSL1("38 МО sl 0 ac sl +1", OpenStrategy.MAXIMUM_ROLLBACK_38),
    MR38SL0("38 МО sl 0", OpenStrategy.MAXIMUM_ROLLBACK_38),
    MD38SLO("38 мп sl 0", OpenStrategy.MAXIMUM_DRAWDOWN_38);
    @Getter
    private final String description;
    @Getter
    private final OpenStrategy openStrategy;

}
