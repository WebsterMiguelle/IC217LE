class Pokeball {
    int statValue;
    int pokemonUniqueID;
    Pokeball left, right;
    int height;

    Pokeball(int statValue, int uid){
        this.statValue = statValue;
        this.pokemonUniqueID = uid;
        this.height = 1;
    }
}
