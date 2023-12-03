package bg.sofia.uni.fmi.mjt.lab8.football.utils;

public enum PlayerDataIndex {
    NAME_INDEX(0),
    FULL_NAME_INDEX(1),
    BIRTH_DATE_INDEX(2),
    AGE_INDEX(3),
    HEIGHT_CM_INDEX(4),
    WEIGHT_KG_INDEX(5),
    POSITIONS_INDEX(6),
    NATIONALITY_INDEX(7),
    OVERALL_RATING_INDEX(8),
    POTENTIAL_INDEX(9),
    VALUE_EURO_INDEX(10),
    WAGE_EURO_INDEX(11),
    PREFERRED_FOOT_INDEX(12);


    private final int playerDataIndex;

    PlayerDataIndex(int playerDataIndex) {
        this.playerDataIndex = playerDataIndex;
    }

    public int getPlayerDataIndex() {
        return playerDataIndex;
    }
}
