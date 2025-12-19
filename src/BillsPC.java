class BillsPC {
    private BillsPCNode[] buckets = new BillsPCNode[100];

    public void put(int key, Pokemon val){
        int idx = Math.abs(key) % buckets.length;
        BillsPCNode n = new BillsPCNode(key, val);
        n.next = buckets[idx];
        buckets[idx] = n;
    }
    public Pokemon get(int key){
        int idx = Math.abs(key) % buckets.length;
        BillsPCNode h = buckets[idx];
        while(h!=null){ if(h.key==key) return h.val; h=h.next; }
        return null;
    }
    public void remove(int key) {
        int idx = Math.abs(key) % buckets.length;
        BillsPCNode head = buckets[idx];
        BillsPCNode prev = null;
        while(head != null) {
            if(head.key == key) {
                if(prev == null) buckets[idx] = head.next;
                else prev.next = head.next;
                return;
            }
            prev = head;
            head = head.next;
        }
    }
}