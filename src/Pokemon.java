import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Pokemon {
    private static String[] pokedexNames = new String[2000];
    private static boolean isLoaded = false;

    // Static CSV Loader
    public static void loadCSV(String filepath) {
        if(isLoaded) return;
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if(parts.length >= 2) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        if(id < pokedexNames.length) pokedexNames[id] = name;
                    } catch (NumberFormatException ignored) {}
                }
            }
            scanner.close();
            isLoaded = true;
        } catch (FileNotFoundException e) {
            System.out.println("Error: pokedex.csv not found.");
        }
    }

    // Instance Data
    int speciesID;
    int uniqueID; // New: Allows multiple Pikachus
    String name;
    int[] stats; // 0:HP, 1:Atk, 2:Def, 3:SpA, 4:SpD, 5:Spe, 6:Lvl

    Pokemon(int speciesID, int HP, int Atk, int Def, int SpA, int SpD, int Spe, int Lvl) {
        this.speciesID = speciesID;
        this.name = (pokedexNames[speciesID] != null) ? pokedexNames[speciesID] : "GlitchNo";
        this.stats = new int[]{HP,Atk,Def,SpA,SpD,Spe,Lvl};
    }

    @Override
    public String toString() {
        // Returns: Name | Lvl 5 | H:20 A:20 D:20 SA:20 SD:20 S:20
        return String.format("%-12s | Lvl %-3d | H:%-3d A:%-3d D:%-3d SA:%-3d SD:%-3d S:%-3d",
                name, stats[6],
                stats[0], // HP
                stats[1], // Atk
                stats[2], // Def
                stats[3], // SpA
                stats[4], // SpD
                stats[5]  // Spe
        );
    }
}