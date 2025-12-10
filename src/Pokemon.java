class Pokemon {
    int id;
    String name;
    int[] stats;

    Pokemon(int id, String name, int HP, int Atk, int Def, int SpA, int SpD, int Spe, int Lvl) {
        this.id = id;
        this.name = name;
        this.stats = new int[]{HP,Atk,Def,SpA,SpD,Spe,Lvl};
    }

    @Override
    public String toString() {
        return String.format("[#%d] %-10s | Level: %d | Hp: %d | Atk: %d | Def: %d | SpAtk: %d | SpDef %d | Spd %d |",
                id, name, stats[6], stats[0], stats[1], stats[2], stats[3], stats[4], stats[5]);
    }
}
