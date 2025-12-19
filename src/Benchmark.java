import java.util.ArrayList;
import java.util.Random;

class Benchmark {
    public static void run(int N) {
        System.out.println("\n=================================");
        System.out.println("   SYSTEM DIAGNOSTICS (N=" + N + ")   ");
        System.out.println("=================================");
        System.out.println("Initializing test environment...");

        // Create a separate Handler for testing to not mess up user data
        Handler testGame = new Handler();
        testGame.setSilent(true); // Disable text output for speed

        ArrayList<Integer> generatedIDs = new ArrayList<>(N);
        Random r = new Random();

        // --- TEST 1: INSERTION ---
        System.out.print("Running INSERTION test... ");
        long start = System.nanoTime();

        for(int i = 0; i < N; i++) {
            // Manually create pokemon to capture the ID for later tests
            int species = r.nextInt(151) + 1;
            Pokemon p = new Pokemon(species, 50, 50, 50, 50, 50, 50, 50);
            p.uniqueID = i; // Simple sequential ID for benchmark
            generatedIDs.add(p.uniqueID);
            testGame.addPokemon(p);
        }

        long end = System.nanoTime();
        double insertTime = (end - start) / 1_000_000.0;
        System.out.printf("DONE. [%.2f ms]%n", insertTime);

        // --- TEST 2: SEARCHING ---
        System.out.print("Running SEARCH test...    ");
        start = System.nanoTime();

        for(int id : generatedIDs) {
            testGame.searchPokemon(id); // Look up every single added Pokemon
        }

        end = System.nanoTime();
        double searchTime = (end - start) / 1_000_000.0;
        System.out.printf("DONE. [%.2f ms]%n", searchTime);

        // --- TEST 3: DELETION ---
        System.out.print("Running DELETION test...  ");
        start = System.nanoTime();

        for(int id : generatedIDs) {
            testGame.releasePokemon(id);
        }

        end = System.nanoTime();
        double deleteTime = (end - start) / 1_000_000.0;
        System.out.printf("DONE. [%.2f ms]%n", deleteTime);

        // --- SUMMARY ---
        System.out.println("\n---------------------------------");
        System.out.println("        RESULTS SUMMARY          ");
        System.out.println("---------------------------------");
        System.out.printf("Insertion Speed: %.0f ops/sec%n", (N / (insertTime/1000.0)));
        System.out.printf("Searching Speed: %.0f ops/sec%n", (N / (searchTime/1000.0)));
        System.out.printf("Deletion Speed:  %.0f ops/sec%n", (N / (deleteTime/1000.0)));
        System.out.println("---------------------------------");
    }
}