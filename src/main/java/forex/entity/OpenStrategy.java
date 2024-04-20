package forex.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OpenStrategy {
    CLASSIC(OpenPrice.FIBONACCI_1000),
    AC(OpenPrice.FIBONACCI_0618),
    MAXIMUM_ROLLBACK_38(OpenPrice.FIBONACCI_0382),
    MAXIMUM_ROLLBACK_61(OpenPrice.FIBONACCI_0618),
    MAXIMUM_DRAWDOWN_38(OpenPrice.FIBONACCI_0382),
    MAXIMUM_DRAWDOWN_61(OpenPrice.FIBONACCI_0618);
    @Getter
    private final OpenPrice openPrice;
}
