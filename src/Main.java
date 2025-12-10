//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Handler game = new Handler();

        game.addPokemon(new Pokemon(1,"Bulbasaur", 45,49,65,65,45,5,1));
        game.addPokemon(new Pokemon(4,"Charmander",39,52,43,60,50,65,5));
        game.addPokemon(new Pokemon(7, "Squirtle", 44, 48, 65, 50, 64, 43, 5));
        game.addPokemon(new Pokemon(25, "Pikachu", 35, 55, 40, 50, 50, 90, 5));

        System.out.println("\n--- Highest Lvl ---");
        game.printTopList(6);// Index 6 = Speed
        game.printTopList(0);// Index 0 = HP
        game.printTopList(1); // Index 1 = Attack
        game.printTopList(2); // Index 2 = Defense
        game.printTopList(3); // Index 3 = Special Attack
        game.printTopList(4); // Index 4 = Special Defense
        game.printTopList(5); // Index 5 = Speed
        game.printTopList(6);
    }
}