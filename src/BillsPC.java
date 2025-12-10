class BillsPC {
    private BillsPCNode[] buckets;
    private int capacity;
    private int size;

    public BillsPC() {
        this.capacity = 10;
        buckets = new BillsPCNode[capacity];
        size = 0;
    }
    private int getBucketIndex(int key){
        return Math.abs(key) % capacity;
    }

    public void put(int key, Pokemon value){
        int index = getBucketIndex(key);
        BillsPCNode head = buckets[index];

        while(head != null){
            if(head.key == key){
                head.value = value;
                return;
            }
            head = head.next;
        }

        size++;
        head = buckets[index];
        BillsPCNode newNode = new BillsPCNode(key, value);
        newNode.next = head;
        buckets[index] = newNode;
    }

    public Pokemon get(int key){
        int index = getBucketIndex(key);
        BillsPCNode head = buckets[index];

        while(head != null){
            if(head.key == key){
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

}
