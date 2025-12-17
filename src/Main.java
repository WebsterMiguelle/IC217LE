import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Load Data
        Pokemon.loadCSV("src\\pokedex.csv");
        Handler game = new Handler();
        Scanner scanner = new Scanner(System.in);
        Random rng = new Random();

        // 2. Starter Pokemon
        System.out.println("...Booting Bill's PC System...");
        System.out.println("System: Here is a starter for you.");
        game.generateRandomEncounter(25); // Pikachu starter

        while(true) {
            System.out.println("\n=================================");
            System.out.println("      POKEMON STORAGE OS v1.0    ");
            System.out.println("=================================");
            System.out.println("[1] Explore (Find Random Pokemon)");
            System.out.println("[2] Access Bill's PC (View Box)");
            System.out.println("[3] Auto-Build Team (Top 6)");
            System.out.println("[4] System Status (Count)");
            System.out.println("[0] Shut Down");
            System.out.print("cmd:> ");

            String choice = scanner.next();

            switch (choice) {
                case "1":
                    // Event: Random Encounter
                    System.out.println("\nSearching for wild Pokemon...");
                    try { Thread.sleep(800); } catch (Exception e){}

                    // 30% chance to find nothing, 70% chance to find something
                    if(rng.nextDouble() > 0.3) {
                        // Random ID between 1 and 151 (Gen 1)
                        int randomID = rng.nextInt(151) + 1;
                        game.generateRandomEncounter(randomID);
                    } else {
                        System.out.println("... No Pokemon found nearby.");
                    }
                    break;

                case "2":
                    // Event: PC Interaction with Scrolling
                    handlePCInteraction(game, scanner);
                    break;

                case "3":
                    // Event: Auto Team Builder
                    handleTeamBuilder(game, scanner);
                    break;

                case "4":
                    System.out.println("Total Pokemon Stored: " + game.getTotalCount());
                    break;

                case "0":
                    System.out.println("Shutting down...");
                    return;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    // --- Sub-Menu: PC Scrolling System ---
    private static void handlePCInteraction(Handler game, Scanner scan) {
        System.out.println("\n--- SORT VIEW BY ---");
        System.out.println("[0] Hp");
        System.out.println("[1] Atk");
        System.out.println("[2] Def");
        System.out.println("[3] SpA");
        System.out.println("[4] SpD");
        System.out.println("[5] Spe");
        System.out.println("[6] Lvl");
        System.out.print("Choice: ");
        int statChoice = 0;
        try { statChoice = Integer.parseInt(scan.next()); } catch (Exception e) {}

        // Default to 6 (Level) if they choose 0, otherwise map correctly
        // Handler uses: 0:HP, 1:Atk, ..., 5:Spe, 6:Lvl
        int indexToUse = statChoice;

        int page = 1;
        int pageSize = 5;

        boolean browsing = true;
        while(browsing) {
            game.printPage(indexToUse, page, pageSize);

            System.out.println("\n[N]ext Page | [P]rev Page | [B]ack");
            String nav = scan.next().toUpperCase();

            if(nav.equals("N")) page++;
            else if(nav.equals("P") && page > 1) page--;
            else if(nav.equals("B")) browsing = false;
        }
    }

    // --- Sub-Menu: Team Builder ---
    private static void handleTeamBuilder(Handler game, Scanner scan) {
        System.out.println("\n--- AUTO TEAM BUILDER ---");
        System.out.println("Build team based on which stat?");
        System.out.println("[0] HP");
        System.out.println("[1] Atk");
        System.out.println("[2] Def");
        System.out.println("[3] SpA");
        System.out.println("[4] SpD");
        System.out.println("[5] Spe");
        System.out.println("[6] Lvl");
        System.out.print("Choice: ");

        int stat = 0; // Default Atk
        try { stat = Integer.parseInt(scan.next()); } catch (Exception e){}

        System.out.println("\nGenerating Optimal Team...");

        game.printPage(stat, 1, 6);
    }
}
