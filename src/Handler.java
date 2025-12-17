import java.util.Random;

class Handler {
    private BillsPC masterRegistry;
    private PCBox[] indices;
    private final String[] statNames = {"HP", "Atk","Def","SpA","SpD","Spe","Lvl"};
    private int totalCaptured = 0;

    public Handler() {
        masterRegistry = new BillsPC();
        indices = new PCBox[statNames.length];
        for (int i = 0; i < statNames.length; i++) {
            indices[i] = new PCBox(statNames[i]);
        }
    }

    public void generateRandomEncounter(int id) {
        // Random Stats Generator
        Random r = new Random();
        int lvl = r.nextInt(50) + 5; // Level 5 to 55

        // Base stats + Random Variance based on level
        int hp  = (lvl * 2) + r.nextInt(20);
        int atk = (lvl * 2) + r.nextInt(20);
        int def = (lvl * 2) + r.nextInt(20);
        int spa = (lvl * 2) + r.nextInt(20);
        int spd = (lvl * 2) + r.nextInt(20);
        int spe = (lvl * 2) + r.nextInt(20);

        Pokemon wildMon = new Pokemon(id, hp, atk, def, spa, spd, spe, lvl);

        // Fake unique ID
        wildMon.uniqueID = (id * 10000) + r.nextInt(9999);

        addPokemon(wildMon);
    }

    public void addPokemon(Pokemon p) {
        masterRegistry.put(p.uniqueID, p);
        for(int i = 0; i < indices.length; i++) {
            indices[i].insert(p.stats[i], p.uniqueID);
        }
        totalCaptured++;

        // --- UPDATED PRINT LOGIC ---
        System.out.println("--> CAUGHT: " + p.name + " (Lvl " + p.stats[6] + ")");
        System.out.println("    [ HP:" + p.stats[0] + " Atk:" + p.stats[1] + " Def:" + p.stats[2] +
                " SpA:" + p.stats[3] + " SpD:" + p.stats[4] + " Spe:" + p.stats[5] + " ]");
        System.out.println("    [Sent to Bill's PC]");
    }

    public void printPage(int statIndex, int pageNumber, int pageSize) {
        if (statIndex < 0 || statIndex >= 7) return;

        System.out.println("\n=== PC BOX (" + statNames[statIndex] + " Sorted) | Page " + pageNumber + " ===");

        // Calculate the range of items to show
        // Page 1: Items 1 to 5. Page 2: Items 6 to 10.
        int startRank = (pageNumber - 1) * pageSize;
        int endRank = startRank + pageSize;

        // Pass this "Window" to the Tree
        indices[statIndex].printRange(masterRegistry, startRank, endRank);
    }

    public int getTotalCount() { return totalCaptured; }
}