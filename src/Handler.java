class Handler {
    private BillsPC masterRegistry;
    private PCBox[] indices;
    private final String[] statNames = {"HP", "Atk","Def","SpA","SpD","Spe","Lvl"};

    public Handler() {
        masterRegistry = new BillsPC();
        indices = new PCBox[statNames.length];
        for (int i = 0; i < statNames.length; i++) {
            indices[i] = new PCBox(statNames[i]);
        }
    }

    public void addPokemon(Pokemon pokemon) {
        masterRegistry.put(pokemon.id, pokemon);

        for(int i = 0; i < indices.length; i++) {
            indices[i].insert(pokemon.stats[i],pokemon.id);
        }
        System.out.println("Welcome " + pokemon.name + " to your pc");
    }

    public void queryTopRank(int statIndex){
        System.out.println("List of top " + statNames[statIndex] + ": ");
        indices[statIndex].printMax(masterRegistry);
    }

    public void printTopList(int statIndex){
        if (statIndex < 0 || statIndex >= 7) {
            System.out.println("Invalid Stat Index.");
            return;
        }
        indices[statIndex].printOrdered(masterRegistry);
    }
    public void printTopPKMN(int statIndex){
        if (statIndex < 0 || statIndex >= 7) {
            System.out.println("Invalid Stat Index.");
            return;
        }
        indices[statIndex].printOrdered(masterRegistry);
    }

}
