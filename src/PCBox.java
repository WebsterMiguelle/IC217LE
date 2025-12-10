class PCBox {
    private Pokeball root;
    private String statName;

    public PCBox(String statName) { this.statName = statName; }

    private int height(Pokeball node){
        if(node == null) return 0;
        return node.height;
    }

    private int getBalance(Pokeball node){
        if(node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private Pokeball rightRotate(Pokeball y){
        Pokeball x = y.left;
        Pokeball T2 = x.right;
        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Pokeball leftRotate(Pokeball x){
        Pokeball y = x.right;
        Pokeball T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(x.left), height(x.right)) + 1;
        return y;
    }

    public void insert(int statValue, int pokemonID){
        root = insertRecurse(root, statValue, pokemonID);
    }

    private Pokeball insertRecurse(Pokeball node, int statValue, int pokemonID) {
        if(node == null) return new Pokeball(statValue, pokemonID);

        //1. Primary Sorting : StatValue
        if(statValue < node.statValue){
            node.left = insertRecurse(node.left, statValue, pokemonID);
        } else if(statValue > node.statValue){
            node.right = insertRecurse(node.right, statValue, pokemonID);
        } else {
            //2. Secondary Sort: By ID
            if(pokemonID < node.pokemonID){
                node.left = insertRecurse(node.left, statValue, pokemonID);
            } else{
                node.right = insertRecurse(node.right, statValue, pokemonID);
            }
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        //LL
        if(balance > 1 && (statValue < node.left.statValue ||
                (statValue == node.left.statValue && pokemonID < node.left.pokemonID))) {
            return rightRotate(node);
        }

        //RR
        // FIX #1: changed node.left.pokemonID to node.right.pokemonID
        if(balance < -1 && (statValue > node.right.statValue ||
                (statValue == node.right.statValue && pokemonID > node.right.pokemonID))) {
            return leftRotate(node);
        }

        //LR
        if(balance > 1 && (statValue > node.left.statValue ||
                (statValue == node.left.statValue && pokemonID > node.left.pokemonID))) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL
        if(balance < -1 && (statValue < node.right.statValue ||
                (statValue == node.right.statValue && pokemonID < node.right.pokemonID))) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public void delete(int statValue, int pokemonID){
        root = deleteRecurse(root, statValue, pokemonID);
    }

    private Pokeball deleteRecurse(Pokeball node, int statValue, int pokemonID) {
        if(node == null) return node;

        if(statValue < node.statValue){
            node.left = deleteRecurse(node.left, statValue, pokemonID);
        } else if(statValue > node.statValue){
            node.right = deleteRecurse(node.right, statValue, pokemonID);
        } else {
            if(pokemonID < node.pokemonID){
                node.left = deleteRecurse(node.left, statValue, pokemonID);
            } else if(pokemonID > node.pokemonID){
                node.right = deleteRecurse(node.right, statValue, pokemonID);
            } else {
                // Node found
                if((node.left == null) || (node.right == null)) {
                    Pokeball temp = (node.left != null) ? node.left : node.right;
                    if(temp == null){
                        temp = node;
                        node = null;
                    } else {
                        node = temp;
                    }
                } else {
                    Pokeball temp = minValueNode(node.right);
                    node.statValue = temp.statValue;
                    node.pokemonID = temp.pokemonID;

                    // FIX #2: Must delete the TEMP values (successor), not the original values
                    node.right = deleteRecurse(node.right, temp.statValue, temp.pokemonID);
                }
            }
        }

        if(node == null) return node;

        node.height = Math.max(height(node.left), height(node.right)) + 1;

        int balance = getBalance(node);

        // LL Case
        if(balance > 1 && getBalance(node.left) >= 0){
            return rightRotate(node);
        }

        // LR Case
        if(balance > 1 && getBalance(node.left) < 0){
            // FIX #3: Added the missing left rotation
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RR Case
        if(balance < -1 && getBalance(node.right) <= 0){
            return leftRotate(node);
        }

        // RL Case
        if(balance < -1 && getBalance(node.right) > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    private Pokeball minValueNode(Pokeball node){
        Pokeball current = node;
        while(current.left != null){
            current = current.left;
        }
        return current;
    }

    public void printMax(BillsPC registry){
        if(root == null){
            System.out.println("The Box is Empty");
            return;
        }
        Pokeball current = root;
        while (current.right != null){
            current = current.right;
        }

        System.out.println("Highest (" + statName + "): " + registry.get(current.pokemonID));
    }

    public void printOrdered(BillsPC registry){
        System.out.println("--- Ranking by " + statName + " (High to Low) ---");
        if(root == null){
            System.out.println("The Box is Empty");
            return;
        }
        printOrderedRecurse(root, registry);
        System.out.println("-------------------------------------");
    }

    private void printOrderedRecurse(Pokeball node, BillsPC registry){
        if(node == null) return;

        // Right first (High values)
        printOrderedRecurse(node.right, registry);

        Pokemon p = registry.get(node.pokemonID);
        System.out.printf("#%d %-12s | %s: %d%n",
                p.id, p.name, statName, node.statValue);

        // Left last (Low values)
        printOrderedRecurse(node.left, registry);
    }
}