
class PCBox {
    private Pokeball root;
    private String statName;
    private int traversalCounter = 0;

    public PCBox(String statName) { this.statName = statName; }

    private int height(Pokeball n){ return (n==null)?0:n.height; }
    private int getBal(Pokeball n){ return (n==null)?0:height(n.left)-height(n.right); }

    private Pokeball rightRotate(Pokeball y) {
        Pokeball x = y.left;
        Pokeball T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }
    private Pokeball leftRotate(Pokeball x) {
        Pokeball y = x.right;
        Pokeball T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void insert(int val, int uid){ root = insertRec(root, val, uid); }

    private Pokeball insertRec(Pokeball node, int val, int uid) {
        if(node == null) return new Pokeball(val, uid);
        if(val < node.statValue) node.left = insertRec(node.left, val, uid);
        else if(val > node.statValue) node.right = insertRec(node.right, val, uid);
        else {
            if(uid < node.pokemonUniqueID) node.left = insertRec(node.left, val, uid);
            else node.right = insertRec(node.right, val, uid);
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBal(node);
        if(balance > 1 && (val < node.left.statValue || (val == node.left.statValue && uid < node.left.pokemonUniqueID))) return rightRotate(node);
        if(balance < -1 && (val > node.right.statValue || (val == node.right.statValue && uid > node.right.pokemonUniqueID))) return leftRotate(node);
        if(balance > 1 && (val > node.left.statValue || (val == node.left.statValue && uid > node.left.pokemonUniqueID))) { node.left = leftRotate(node.left); return rightRotate(node); }
        if(balance < -1 && (val < node.right.statValue || (val == node.right.statValue && uid < node.right.pokemonUniqueID))) { node.right = rightRotate(node.right); return leftRotate(node); }
        return node;
    }

    public void delete(int val, int uid) { root = deleteRec(root, val, uid); }

    private Pokeball deleteRec(Pokeball node, int val, int uid) {
        if(node == null) return node;
        if(val < node.statValue) node.left = deleteRec(node.left, val, uid);
        else if(val > node.statValue) node.right = deleteRec(node.right, val, uid);
        else {
            if(uid < node.pokemonUniqueID) node.left = deleteRec(node.left, val, uid);
            else if(uid > node.pokemonUniqueID) node.right = deleteRec(node.right, val, uid);
            else {
                if((node.left == null) || (node.right == null)) {
                    Pokeball temp = (node.left != null) ? node.left : node.right;
                    if(temp == null) { temp = node; node = null; }
                    else node = temp;
                } else {
                    Pokeball temp = minValueNode(node.right);
                    node.statValue = temp.statValue;
                    node.pokemonUniqueID = temp.pokemonUniqueID;
                    node.right = deleteRec(node.right, temp.statValue, temp.pokemonUniqueID);
                }
            }
        }
        if(node == null) return node;
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBal(node);
        if(balance > 1 && getBal(node.left) >= 0) return rightRotate(node);
        if(balance > 1 && getBal(node.left) < 0) { node.left = leftRotate(node.left); return rightRotate(node); }
        if(balance < -1 && getBal(node.right) <= 0) return leftRotate(node);
        if(balance < -1 && getBal(node.right) > 0) { node.right = rightRotate(node.right); return leftRotate(node); }
        return node;
    }

    private Pokeball minValueNode(Pokeball node) {
        Pokeball current = node;
        while(current.left != null) current = current.left;
        return current;
    }

    public void printRange(BillsPC registry, int startRank, int endRank, boolean silent) {
        if(root == null && !silent) { System.out.println("   (Box Empty)"); return; }
        traversalCounter = 0;
        printRangeRec(root, registry, startRank, endRank, silent);
    }

    private void printRangeRec(Pokeball node, BillsPC registry, int start, int end, boolean silent) {
        if(node == null) return;
        printRangeRec(node.right, registry, start, end, silent);
        if(traversalCounter >= start && traversalCounter < end) {
            Pokemon p = registry.get(node.pokemonUniqueID);
            if(p != null && !silent) {
                System.out.printf("#%-3d %s | %s: %d%n",
                        (traversalCounter+1), p.toString(), statName, node.statValue);
            }
        }
        traversalCounter++;
        printRangeRec(node.left, registry, start, end, silent);
    }


    public void printRange(BillsPC registry, int start, int end) {
        printRange(registry, start, end, false);
    }
}