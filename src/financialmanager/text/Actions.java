package financialmanager.text;

public enum Actions {
    UPDATE("update"),
    INSERT("insert");

    private String actions;

    Actions(String actions) {
        this.actions = actions;
    }

    public String getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return actions + "";
    }
}
