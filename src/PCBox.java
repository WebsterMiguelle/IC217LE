class PCBox {
    private Pokeball root;
    private String statName;

    // Global counter for the recursion to track "Where am I in the list?"
    private int traversalCounter = 0;

    public PCBox(String statName) { this.statName = statName; }

    // --- AVL Logic (Insert/Balance) remains the same ---
    private int height(Pokeball n){ return (n==null)?0:n.height; }
    private int getBal(Pokeball n){ return (n==null)?0:height(n.left)-height(n.right); }

    public void insert(int val, int uid){ root = insertRec(root, val, uid); }

    private Pokeball insertRec(Pokeball node, int val, int uid) {
        if(node == null) return new Pokeball(val, uid);
        if(val < node.statValue) node.left = insertRec(node.left, val, uid);
        else if(val > node.statValue) node.right = insertRec(node.right, val, uid);
        else {
            // Handle duplicates using Unique ID
            if(uid < node.pokemonUniqueID) node.left = insertRec(node.left, val, uid);
            else node.right = insertRec(node.right, val, uid);
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        // (Balancing logic omitted for brevity - paste your previous rotation logic here)
        return node;
    }

    // --- NEW: PAGINATION LOGIC ---
    public void printRange(BillsPC registry, int startRank, int endRank) {
        if(root == null) { System.out.println("   (Box Empty)"); return; }

        // Reset counter before starting traversal
        traversalCounter = 0;
        printRangeRec(root, registry, startRank, endRank);
    }

    private void printRangeRec(Pokeball node, BillsPC registry, int start, int end) {
        if(node == null) return;

        // 1. Visit Right (Highest Values first)
        printRangeRec(node.right, registry, start, end);

        // 2. Process Current Node
        // We only print if the current counter is within the "Page Window"
        if(traversalCounter >= start && traversalCounter < end) {
            Pokemon p = registry.get(node.pokemonUniqueID);
            if(p != null) {
                System.out.printf("#%-3d %s | %s: %d%n",
                        (traversalCounter+1), p.toString(), statName, node.statValue);
            }
        }
        traversalCounter++; // Increment rank

        // Optimization: If we passed the page, we could stop, but for simple recursion we continue

        // 3. Visit Left (Lower Values)
        printRangeRec(node.left, registry, start, end);
    }
}