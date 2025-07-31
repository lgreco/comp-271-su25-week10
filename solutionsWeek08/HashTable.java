/**
 * A simple hasht table is an array of linked lists. In its simplest form, a
 * linked list is represented by its first node. Typically we label this node as
 * "head". Here, however, we'll know it's the first node of the list because it
 * will be placed in an array element. For example, if we have 4 linked lists,
 * we know that the "head" of the third one can be found in position [2] of the
 * underlying array.
 */
public class HashTable<E extends Comparable<E>> {

    /**
     * Underlying array of nodes. Each non empty element of this array is the first
     * node of a linked list.
     */
    private Node<E>[] underlying;

    /** Counts how many places in the underlying array are occupied */
    private int usage;

    /** Counts how many nodes are stored in this hashtable */
    private int totalNodes;

    /** Tracks underlying array's load factor */
    private double loadFactor;

    /** Default size for the underlying array. */
    private static final int DEFAULT_SIZE = 4;

    /** Default load factor threshold for resizing */
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    /** Resize factor */
    private static final int RESIZE_BY = 2;

    /**
     * Basic constructor with user-specified size. If size is absurd, the
     * constructor will revert to the default size.
     */
    @SuppressWarnings("unchecked")
    public HashTable(int size) {
        if (size <= 0)
            size = DEFAULT_SIZE;
        this.underlying = (Node<E>[]) new Node[size];
        this.usage = 0;
        this.totalNodes = 0;
        this.loadFactor = 0.0;
    } // basic constructor

    /** Default constructor, passes default size to basic constructor */
    public HashTable() {
        this(DEFAULT_SIZE);
    } // default constructor

    /** Update the load factor */
    private void updateLoadFactor() {
        this.loadFactor = (double) this.usage / (double) this.underlying.length;
    } // method updateLoadFactor

    /**
     * Computes the destination position of content into the underlying array. The
     * content, once encapsulated in a node, will be added to the linked list at the
     * destination computed here.
     * 
     * @param content E value to add to a linked list in the underlying array
     * @return int position in the underlying array. This value is guaranteed to be
     *         in the integer interval [0, this.underlying.length) because of the %
     *         function used here.
     */
    private int hashPosition(E content) {
        // .hashCode may be negative due to int overflow and abs will correct it.
        return Math.abs(content.hashCode()) % this.underlying.length;
    } // method hashPosition

    /**
     * Determines if a target value is present in one of the hash table's
     * linked lists.
     * 
     * @param target E value to search for
     * @return true if (E) target is present in a linked list stored in the
     *         underlying array and false otherwise.
     */
    public boolean contains(E target) {
        // Assume target is not present
        boolean found = false;
        // Protect from null argument
        if (target != null) {
            // If target is present, the location of its linked list can be 
            // established by the hash function used.
            int expectedAt = this.hashPosition(target);
            // Traverse the linked list at the expected position. The position may be
            // vacant, ie there may be no linked list at all there. In this case a null
            // pointer at the expected position will short circuit the loop.
            Node<E> cursor = this.underlying[expectedAt];
            while (cursor != null && !found) {
                found = target.equals(cursor.getContent());
                cursor = cursor.getNext();
            }
        }
        return found;
    } // method contains

    /**
     * Rehashes the underlying array by sizing it up and redistributing its nodes.
     */
    private void rehash() {
        // Create a temporary hashtable that will eventuall replace this object
        HashTable<E> temporary = new HashTable<>(RESIZE_BY * this.underlying.length);
        // Iterate over every element of the underlying array. Underlying array elements
        // are linked lists that have to be traversed themselves.
        for (Node<E> head : this.underlying) {
            // One linked list at a time
            Node<E> current = head;
            // One node at a time in this linked list
            while (current != null) {
                // If the node is not empty, obtain its content and add it to the temporary hash
                // table. Remember that temporary is itself a hashtable and can use .add() to
                // determine a location for the content we are copying from this hashtable.
                temporary.add(current.getContent());
                // Move to the next node in the list
                current = current.getNext();
            } // done traversing the linked list at this location
        } // done traversing the underlying array of this object
          // Copy the fields of the temporary hash table to this object.
          // Basically we say: this <-- temporary
        this.underlying = temporary.underlying;
        this.loadFactor = temporary.loadFactor;
        this.usage = temporary.usage;
        this.totalNodes = temporary.totalNodes;
    } // method rehash

    /**
     * Adds a node, with the specified content, to a linked list in the underlying
     * array.
     * 
     * @param content E The content of a new node, to be placed in the array.
     */
    public void add(E content) {
        // Resize and rehash if needed first
        if (this.loadFactor >= LOAD_FACTOR_THRESHOLD) {
            this.rehash();
        }
        // Create the new node to add to the hashtable
        Node<E> newNode = new Node<E>(content);
        // Use the hashcode for the new node's contents to find where to place it in the
        // underlying array. Use abs in case hashcode < 0.
        int position = this.hashPosition(content);
        // Check if selected position is already in use
        if (this.underlying[position] == null) {
            // Selected position not in use. Place the new node here and update the usage of
            // the underlying array.
            this.underlying[position] = newNode;
            this.usage += 1;
        } else {
            // Selected position in use. We will append its contents to the new node first,
            // then place the new node in the selected position. Effectively the new node
            // becomes the first node of the existing linked list in this position.
            newNode.setNext(this.underlying[position]);
            this.underlying[position] = newNode;
        }
        // Update the number of nodes
        this.totalNodes += 1;
        this.updateLoadFactor();
    } // method add

    /** Constants for toString */
    private static final String LINKED_LIST_HEADER = "\n[ %2d ]: ";
    private static final String EMPTY_LIST_MESSAGE = "null";
    private static final String ARRAY_INFORMATION = "Underlying array usage / length: %d/%d";
    private static final String NODES_INFORMATION = "\nTotal number of nodes: %d";
    private static final String NODE_CONTENT = "%s --> ";

    /** String representationf for the object */
    public String toString() {
        // Initialize the StringBuilder object with basic info
        StringBuilder sb = new StringBuilder(
                String.format(ARRAY_INFORMATION,
                        this.usage, this.underlying.length));
        sb.append(String.format(NODES_INFORMATION, this.totalNodes));
        // Iterate the array
        for (int i = 0; i < underlying.length; i++) {
            sb.append(String.format(LINKED_LIST_HEADER, i));
            Node<E> head = this.underlying[i];
            if (head == null) {
                // message that this position is empty
                sb.append(EMPTY_LIST_MESSAGE);
            } else {
                // traverse the linked list, displaying its elements
                Node<E> cursor = head;
                while (cursor != null) {
                    // update sb
                    sb.append(String.format(NODE_CONTENT, cursor));
                    // move to the next node of the ll
                    cursor = cursor.getNext();
                } // done traversing the linked list
            } // done checking the current position of the underlying array
        } // done iterating the underlying array
        return sb.toString();
    } // method toString

} // class HashTable