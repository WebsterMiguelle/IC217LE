import java.util.Random;

class Handler {
private BillsPC masterRegistry;
private PCBox[] indices;
private final String[] statNames = {"HP", "Atk","Def","SpA","SpD","Spe","Lvl"};
private int totalCaptured = 0;

// BENCHMARK HELPER
private boolean silentMode = false;
public void setSilent(boolean s) { this.silentMode = s; }

public Handler() {
    masterRegistry = new BillsPC();
    indices = new PCBox[statNames.length];
    for (int i = 0; i < statNames.length; i++) {
        indices[i] = new PCBox(statNames[i]);
    }
}

// Benchmark helper for Search
public boolean searchPokemon(int uniqueID) {
    return masterRegistry.get(uniqueID) != null;
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

    // Only print if not in benchmark mode
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

    // Suppress printing in silent mode to test raw traversal speed if needed
    if(!silentMode) System.out.println("\n=== PC BOX (" + statNames[statIndex] + " Sorted) | Page " + pageNumber + " ===");

    int startRank = (pageNumber - 1) * pageSize;
    int endRank = startRank + pageSize;

    indices[statIndex].printRange(masterRegistry, startRank, endRank, silentMode);
}

public int getTotalCount() { return totalCaptured; }
}