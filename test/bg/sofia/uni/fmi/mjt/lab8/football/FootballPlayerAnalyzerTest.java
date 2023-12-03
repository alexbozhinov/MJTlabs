package bg.sofia.uni.fmi.mjt.lab8.football;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

class FootballPlayerAnalyzerTest {

    private FootballPlayerAnalyzer analyzer;

    @BeforeEach
    public void setUp() {
        String csvData =
            "name;full_name;birth_date;age;height_cm;weight_kgs;positions;nationality;overall_rating;potential;value_euro;wage_euro;preferred_foot" +
                System.lineSeparator() +
                "L. Messi;Lionel Andrés Messi Cuccittini;6/24/1987;31;170.18;72.1;CF,RW,ST;Argentina;94;94;110500000;565000;Left" +
                System.lineSeparator() +
                "C. Eriksen;Christian  Dannemann Eriksen;2/14/1992;27;154.94;76.2;CAM,RM,CM;Denmark;88;89;69500000;205000;Right" +
                System.lineSeparator() +
                "P. Pogba;Paul Pogba;3/15/1993;25;190.5;83.9;CM,CAM;France;88;91;73000000;255000;Right" +
                System.lineSeparator() +
                "L. Insigne;Lorenzo Insigne;6/4/1991;27;162.56;59;LW,ST;Italy;88;88;62000000;165000;Right" +
                System.lineSeparator() +
                "K. Mbappé;Kylian Mbappé;12/20/1998;20;152.4;73;RW,ST,RM;France;88;95;81000000;100000;Right";

        analyzer = new FootballPlayerAnalyzer(new StringReader(csvData));
    }

    @Test
    void testGetAllPlayers() {
        List<Player> allPlayers = analyzer.getAllPlayers();

        Assertions.assertEquals(5, allPlayers.size(), "Different number of players in list than expected");
        Assertions.assertEquals("L. Messi", allPlayers.get(0).name(), "Player not in list");
        Assertions.assertEquals("C. Eriksen", allPlayers.get(1).name(), "Player not in list");
        Assertions.assertEquals("P. Pogba", allPlayers.get(2).name(), "Player not in list");
        Assertions.assertEquals("L. Insigne", allPlayers.get(3).name(), "Player not in list");
        Assertions.assertEquals("K. Mbappé", allPlayers.get(4).name(), "Player not in list");
    }

    @Test
    void testGetAllNationalities() {
        Set<String> allNationalities = analyzer.getAllNationalities();

        Assertions.assertEquals(4, allNationalities.size(), "Different number of distinct countries");
        Assertions.assertTrue(allNationalities.contains("Argentina"), "Country not in set");
        Assertions.assertTrue(allNationalities.contains("Denmark"), "Country not in set");
        Assertions.assertTrue(allNationalities.contains("France"), "Country not in set");
        Assertions.assertTrue(allNationalities.contains("Italy"), "Country not in set");
    }

    @Test
    void testGetHighestPaidPlayerByNationality() {

        Player highestPaid = analyzer.getHighestPaidPlayerByNationality("France");

        Assertions.assertEquals("P. Pogba", highestPaid.name(), "Pogba's wage is bigger than Mbappe's");
    }

    @Test
    void testGetHighestPaidPlayerByNationalityNullNationality() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> analyzer.getHighestPaidPlayerByNationality(null),
            "Nationality is not null");
    }

    @Test
    void testGetHighestPaidPlayerByNationalityNoSuchNationality() {
        Assertions.assertThrows(NoSuchElementException.class, () -> analyzer.getHighestPaidPlayerByNationality("Spain"),
            "There is spanish footballer in the file");
    }

    @Test
    void testGroupByPosition() {

        Map<Position, Set<Player>> groupedByPosition = analyzer.groupByPosition();

        Assertions.assertEquals(7, groupedByPosition.size(), "The five players have 7 distinct positions");
        Assertions.assertEquals(3, groupedByPosition.get(Position.ST).size(), "Three strikers expected");
        Assertions.assertTrue(groupedByPosition.containsKey(Position.ST), "Expected to have ST position");
        Assertions.assertTrue(groupedByPosition.get(Position.CM).contains(analyzer.getAllPlayers().get(1)),
            "Eriksen is CM");
    }

    @Test
    void testGetTopProspectPlayerForPositionInBudget() {

        Optional<Player> topProspectStriker =
            analyzer.getTopProspectPlayerForPositionInBudget(Position.ST, 1_000_000_000);
        topProspectStriker.ifPresent(player -> Assertions.assertEquals("K. Mbappé", player.name(), "Mbappe expected"));

        Optional<Player> topProspectCentreMidfielder =
            analyzer.getTopProspectPlayerForPositionInBudget(Position.CM, 1_000_000_000);
        topProspectCentreMidfielder.ifPresent(
            player -> Assertions.assertEquals("P. Pogba", player.name(), "P. Pogba expected"));

        Optional<Player> topProspectRightWing = analyzer.getTopProspectPlayerForPositionInBudget(Position.RW, 1_000);
        Assertions.assertFalse(topProspectRightWing.isPresent(), "Budget too low to find any player");
    }

    @Test
    void testGetTopProspectPlayerNullPosition() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> analyzer.getTopProspectPlayerForPositionInBudget(null, 1_000_000_000),
            "Position argument is not null");
    }

    @Test
    void testGetTopProspectPlayerNegativeBudget() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> analyzer.getTopProspectPlayerForPositionInBudget(Position.RW, -50_000_000),
            "Budget argument is not negative");
    }

    @Test
    void testGetSimilarPlayers() {

        Set<Player> similarPlayers = analyzer.getSimilarPlayers(analyzer.getAllPlayers().get(4));

        Assertions.assertEquals(2, similarPlayers.size(), "Similar players more or less than expected");
        Assertions.assertTrue(similarPlayers.contains(analyzer.getAllPlayers().get(1)), "Eriksen is similar to Mbappe");
        Assertions.assertTrue(similarPlayers.contains(analyzer.getAllPlayers().get(3)), "Insigne is similar to Mbappe");
    }

    @Test
    void testGetSimilarPlayersToNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> analyzer.getSimilarPlayers(null),
            "Player is not null");
    }

    @Test
    void testGetPlayersByFullNameKeyword() {

        Set<Player> playersByKeyword = analyzer.getPlayersByFullNameKeyword("en");

        Assertions.assertEquals(2, playersByKeyword.size(), "Diffrent number of players in set than expected");
        Assertions.assertTrue(playersByKeyword.contains(analyzer.getAllPlayers().get(1)), "Eriksen expected");
        Assertions.assertTrue(playersByKeyword.contains(analyzer.getAllPlayers().get(3)), "Lorenzo expected");
    }

    @Test
    void testGetPlayersByFullNameKeywordNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> analyzer.getPlayersByFullNameKeyword(null),
            "Keyword is not null");
    }
}
