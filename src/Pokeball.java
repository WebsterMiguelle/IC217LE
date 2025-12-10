public class Pokeball {
    //Treat this class as if it is the AVL Node
    int statValue;
    int pokemonID;
    Pokeball left;
    Pokeball right;
    int height;

    Pokeball(int statValue, int pokemonID){
        this.statValue = statValue;
        this.pokemonID = pokemonID;
        this.height = 1;
    }
}
