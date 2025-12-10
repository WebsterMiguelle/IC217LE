class BillsPCNode {
    int key;
    Pokemon value;
    BillsPCNode next;
    BillsPCNode previous;

    public BillsPCNode(int key, Pokemon value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
