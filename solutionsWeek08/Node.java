/**
 * An improved Node object that can store any object -- previously we looked at
 * Nodes that could store only Strings (the object TrainStation for example).
 * Here, we define a parameter <E> that can any class -- so Node<E> is a node
 * that can any object: <E> can be substituted, in implementation is <String>,
 * <Person>, <Book> etc. This parameterized class Node is known as a generic.
 * One additional requirement specified below is that whatever object we use for
 * these nodes, it must implement the Comparable interface.
 */
public class Node<E extends Comparable<E>> {

    /** Contents of the node */
    private E content;
    /** Pointer to next node */
    private Node<E> next;

    /** Basic constructor */
    public Node(E e) {
        this.content = e;
        this.next = null;
    } // basic constructor

    /** Getter method for content */
    public E getContent() {
        return content;
    }

    /** Predicate getter for next */
    public boolean hasNext() {
        return this.next != null;
    }

    /** Getter for next */
    public Node<E> getNext() {
        return this.next;
    }

    /** Setter for next */
    public void setNext(Node<E> next) {
        this.next = next;
    }

    /**
     * String representation is delegated to the class E's toString method. If class
     * E is implemented without a dedicated toString, it still inherits
     * Object.toString()
     */
    public String toString() {
        return content.toString();
    }

} // Generic class Node
