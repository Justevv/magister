package forex.entity;

public enum CloseStrategy {
    FIBONACCI1618,
    FIBONACCI1000,
    FIBONACCI0618,
    FIBONACCI0382,
    FIBONACCI0,
    FIBONACCI0MINUS2000;

    private String description;

//    CloseStrategy(String description) {
//        this.description = description;
//    }

    public String getDescription() {
        return description;
    }
}
