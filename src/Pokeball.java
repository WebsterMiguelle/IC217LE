class Pokeball {
    int statValue;
    int pokemonUniqueID; // Changed to UniqueID
    Pokeball left, right;
    int height;
    // Optimization: You could store 'size' of subtree here for O(log n) indexing,
    // but for this text game, simple traversal is fine.

    Pokeball(int statValue, int uid){
        this.statValue = statValue;
        this.pokemonUniqueID = uid;
        this.height = 1;
    }
}