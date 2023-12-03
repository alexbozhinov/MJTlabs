package bg.sofia.uni.fmi.mjt.lab8.football;

import bg.sofia.uni.fmi.mjt.lab8.football.utils.PlayerDataIndex;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public record Player(
    String name,
    String fullName,
    LocalDate birthDate,
    int age,
    double heightCm,
    double weightKg,
    List<Position> positions,
    String nationality,
    int overallRating,
    int potential,
    long valueEuro,
    long wageEuro,
    Foot preferredFoot) {

    public static Player of(String line) {
        String[] playerData = line.split(";");
        String name = playerData[PlayerDataIndex.NAME_INDEX.getPlayerDataIndex()];
        String fullName = playerData[PlayerDataIndex.FULL_NAME_INDEX.getPlayerDataIndex()];
        LocalDate birthDate = LocalDate.parse(playerData[PlayerDataIndex.BIRTH_DATE_INDEX.getPlayerDataIndex()],
            DateTimeFormatter.ofPattern("M/d/yyyy"));
        int age = Integer.parseInt(playerData[PlayerDataIndex.AGE_INDEX.getPlayerDataIndex()]);
        double heightCm = Double.parseDouble(playerData[PlayerDataIndex.HEIGHT_CM_INDEX.getPlayerDataIndex()]);
        double weightKg = Double.parseDouble(playerData[PlayerDataIndex.WEIGHT_KG_INDEX.getPlayerDataIndex()]);
        List<Position> positions = Arrays
            .stream(playerData[PlayerDataIndex.POSITIONS_INDEX.getPlayerDataIndex()].split(","))
            .map(Position::valueOf)
            .toList();
        String nationality = playerData[PlayerDataIndex.NATIONALITY_INDEX.getPlayerDataIndex()];
        int overallRating = Integer.parseInt(playerData[PlayerDataIndex.OVERALL_RATING_INDEX.getPlayerDataIndex()]);
        int potential = Integer.parseInt(playerData[PlayerDataIndex.POTENTIAL_INDEX.getPlayerDataIndex()]);
        long valueEuro = Long.parseLong(playerData[PlayerDataIndex.VALUE_EURO_INDEX.getPlayerDataIndex()]);
        long wageEuro = Long.parseLong(playerData[PlayerDataIndex.WAGE_EURO_INDEX.getPlayerDataIndex()]);
        Foot preferredFoot = Foot
            .valueOf(playerData[PlayerDataIndex.PREFERRED_FOOT_INDEX.getPlayerDataIndex()].strip().toUpperCase());

        return new Player(name, fullName, birthDate, age, heightCm, weightKg, positions, nationality,
            overallRating, potential, valueEuro, wageEuro, preferredFoot);
    }
}
