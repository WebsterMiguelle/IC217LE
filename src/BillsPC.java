class BillsPC {
    private BillsPCNode[] buckets = new BillsPCNode[50]; // Increased size

    public void put(int key, Pokemon val){
        int idx = Math.abs(key) % 50;
        BillsPCNode n = new BillsPCNode(key, val);
        n.next = buckets[idx];
        buckets[idx] = n;
    }
    public Pokemon get(int key){
        int idx = Math.abs(key) % 50;
        BillsPCNode h = buckets[idx];
        while(h!=null){ if(h.key==key) return h.val; h=h.next; }
        return null;
    }
}