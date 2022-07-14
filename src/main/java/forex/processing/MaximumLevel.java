package forex.processing;

import lombok.Data;

@Data
public class MaximumLevel {
    private boolean level100 = false;
    private boolean level138 = false;
    private boolean level161 = false;
    private boolean level261 = false;
}
