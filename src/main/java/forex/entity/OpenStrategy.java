package forex.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OpenStrategy {
    CLASSIC(OpenPrice.FIBONACCI1000),
    AC(OpenPrice.FIBONACCI0618),
    MAXIMUM_ROLLBACK_38(OpenPrice.FIBONACCI0382),
    MAXIMUM_ROLLBACK_61(OpenPrice.FIBONACCI0618),
    MAXIMUM_DRAWDOWN_38(OpenPrice.FIBONACCI0382),
    MAXIMUM_DRAWDOWN_61(OpenPrice.FIBONACCI0618);
    @Getter
    private final OpenPrice openPrice;
}
