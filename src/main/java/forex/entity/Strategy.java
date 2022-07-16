package forex.entity;

public enum Strategy {
    CLASSIC("Classic"),
    MD61SL38("61% МП sl 38"),
    MD61SL0("61% МП sl 0"),
    MD38SL2000("38 мп sl -200ПП"),
    ACSL38CLASSIC1("AC sl 38 classic sl +1"),
    ACSL0CLASSIC1("AC sl 0 classic sl +1"),
    MR38SL0ACSL1CLASSIC62("38 МО sl 0 ac sl +1 classic sl .618"),
    MD38SLO("38 мп sl 0");

    private String description;

    Strategy(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
