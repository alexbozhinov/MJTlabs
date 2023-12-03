package bg.sofia.uni.fmi.mjt.lab8.football;

public enum Foot {
    LEFT("LEFT"),
    RIGHT("RIGHT");


    private final String foot;

    Foot(String foot) {
        this.foot = foot;
    }

    public String getFoot() {
        return foot;
    }
}
