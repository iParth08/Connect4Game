package core.player;

public enum UserCommand {
    RESTART(),
    QUIT(),
    HELP(),
    INVALID(),
    DROP();

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
