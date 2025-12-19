import java.util.Random;

class Handler {
    private final BillsPC masterRegistry;
    private final PCBox[] indices;
    private final String[] statNames = {"HP", "Atk","Def","SpA","SpD","Spe","Lvl"};
    private int totalCaptured = 0;

    private boolean silentMode = false;
    public void setSilent(boolean s) { this.silentMode = s; }

    public Handler() {
        masterRegistry = new BillsPC();
        indices = new PCBox[statNames.length];
        for (int i = 0; i < statNames.length; i++) {
            indices[i] = new PCBox(statNames[i]);
        }
    }

    // --- NEW: Display Specific Pokemon (User Feature) ---
    public void displayPokemon(int uniqueID) {
        Pokemon p = masterRegistry.get(uniqueID);
        if (p != null) {
            System.out.println("\n--- POKEMON FOUND ---");
            System.out.println(p);
            System.out.println("Full Stats: " +
                    "HP:" + p.stats[0] + " Atk:" + p.stats[1] + " Def:" + p.stats[2] +
                    " SpA:" + p.stats[3] + " SpD:" + p.stats[4] + " Spe:" + p.stats[5]);
            System.out.println("---------------------");
        } else {
            System.out.println("Error: Pokemon with ID " + uniqueID + " not found.");
        }
    }

    // --- Benchmark Specific Search (Silent) ---
    public void searchPokemonBenchmark(int uniqueID) {
       if(!silentMode) {
           displayPokemon(uniqueID);
       }
        masterRegistry.get(uniqueID);
    }

    public void generateRandomEncounter(int id) {
        Random r = new Random();
        int lvl = r.nextInt(50) + 5;
        int hp  = (lvl * 2) + r.nextInt(20);
        int atk = (lvl * 2) + r.nextInt(20);
        int def = (lvl * 2) + r.nextInt(20);
        int spa = (lvl * 2) + r.nextInt(20);
        int spd = (lvl * 2) + r.nextInt(20);
        int spe = (lvl * 2) + r.nextInt(20);

        Pokemon wildMon = new Pokemon(id, hp, atk, def, spa, spd, spe, lvl);
        wildMon.uniqueID = uniqueIDGenerator(id);

        addPokemon(wildMon);
    }
    public int uniqueIDGenerator(int id) {
        int uniqueID = 0;
        if(id < 999) uniqueID = (id * 10)  + totalCaptured;
        if(id < 99) uniqueID = (id * 100)  + totalCaptured;
        if(id < 9) uniqueID = (id * 1000)  + totalCaptured;
        return  uniqueID;
    }

    public void addPokemon(Pokemon p) {
        masterRegistry.put(p.uniqueID, p);
        for(int i = 0; i < indices.length; i++) {
            indices[i].insert(p.stats[i], p.uniqueID);
        }
        totalCaptured++;

        if(!silentMode) {
            System.out.println("--> CAUGHT: " + p.name + " (Lvl " + p.stats[6] + ")");
            System.out.println("    " + p);
            System.out.println("    [Sent to Bill's PC]");
        }
    }

    public void releasePokemon(int uniqueID) {
        Pokemon p = masterRegistry.get(uniqueID);
        if(p == null) {
            if(!silentMode) System.out.println("Error: Pokemon with ID " + uniqueID + " not found.");
            return;
        }

        for(int i = 0; i < indices.length; i++) {
            indices[i].delete(p.stats[i], uniqueID);
        }

        masterRegistry.remove(uniqueID);
        totalCaptured--;

        if(!silentMode) System.out.println("Released " + p.name + " (ID: " + uniqueID + ") back to the wild.");
    }

    public void printPage(int statIndex, int pageNumber, int pageSize) {
        if (statIndex < 0 || statIndex >= 7) return;

        if(!silentMode) System.out.println("\n=== PC BOX (" + statNames[statIndex] + " Sorted) | Page " + pageNumber + " ===");

        int startRank = (pageNumber - 1) * pageSize;
        int endRank = startRank + pageSize;

        indices[statIndex].printRange(masterRegistry, startRank, endRank, silentMode);
    }

    public int getTotalCount() { return totalCaptured; }
}