package bg.sofia.uni.fmi.mjt.lab8.football;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        try {
            Path filePath =
                Paths.get(
                    "C:\\Users\\abozhinov\\IdeaProjects\\MJTlabs\\src\\bg\\sofia\\uni\\fmi\\mjt\\lab8\\football\\dataset.csv");

            try (Reader reader = Files.newBufferedReader(filePath)) {
                FootballPlayerAnalyzer analyzer = new FootballPlayerAnalyzer(reader);

                List<Player> allPlayers = analyzer.getAllPlayers();
                Set<String> allNationalities = analyzer.getAllNationalities();
                Player highestPaidPlayer = analyzer.getHighestPaidPlayerByNationality("Argentina");
                Map<Position, Set<Player>> playersByPosition = analyzer.groupByPosition();
                final int budget = 1000000000;
                Optional<Player> topProspectPlayer =
                    analyzer.getTopProspectPlayerForPositionInBudget(Position.ST, budget);

                System.out.println("All Players: " + allPlayers);
                System.out.println("All Nationalities: " + allNationalities);
                System.out.println("Highest Paid Player from Argentina: " + highestPaidPlayer);
                System.out.println("Players Grouped by Position: " + playersByPosition);
                System.out.println(
                    "Top Prospect Player for ST position with 1,000,000 budget: " + topProspectPlayer.orElse(null));
            }
        } catch (IOException e) {
            System.err.println("Error loading dataset: " + e.getMessage());
        }
    }
}
