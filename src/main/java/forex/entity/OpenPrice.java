package forex.entity;

import lombok.Getter;

@Getter
public enum OpenPrice {
    FIBONACCI_1000(1),
    FIBONACCI_0618(0.618f),
    FIBONACCI_0382(0.382f);
    private final float value;

    OpenPrice(float value) {
        this.value = value;
    }
}
