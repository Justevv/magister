package forex.entity;

import forex.constant.Constant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClosePriceType {
    FIBONACCI_1618(Constant.FIBONACCI_1618),
    FIBONACCI_1000(Constant.FIBONACCI_1000),
    FIBONACCI_0618(Constant.FIBONACCI_0618),
    FIBONACCI_0382(Constant.FIBONACCI_0382),
    FIBONACCI_0(Constant.FIBONACCI_0),
    FIBONACCI_0_MINUS_2000(0);
    @Getter
    private final float value;

}
