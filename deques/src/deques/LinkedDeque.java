package deques;

/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    private Node<T> frontSentinel;
    private Node<T> backSentinel;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() { // make sent nodes
        // frontSentinel = new Node<>(null, null, front);
        // backSentinel = new Node<>(null, back, null);
        frontSentinel = new Node<>(null, null, null);
        backSentinel = new Node<>(null, null, null);
        frontSentinel.next = backSentinel;
        backSentinel.prev = frontSentinel;
        front = frontSentinel;
        back = backSentinel;
        size = 0;
    }

    public void addFirst(T item) { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        Node<T> addNode = new Node<>(item, frontSentinel, frontSentinel.next);
        frontSentinel.next.prev = addNode;
        frontSentinel.next = addNode;
        size += 1;
    }

    public void addLast(T item) { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        Node<T> addNode = new Node<>(item, backSentinel.prev, backSentinel);
        backSentinel.prev.next = addNode;
        backSentinel.prev = addNode;
        size += 1;
    }

    public T removeFirst() { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        if (size == 0) {
            return null;
        }
        Node<T> removed = frontSentinel.next;
        frontSentinel.next = removed.next;
        removed.next.prev = frontSentinel;
        size -= 1;
        return removed.value;
    }

    public T removeLast() { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        if (size == 0) {
            return null;
        }
        Node<T> removed = backSentinel.prev;
        backSentinel.prev = removed.next;
        removed.prev.next = backSentinel;
        size -= 1;
        // front = frontSentinel;
        // back = backSentinel;
        return removed.value;
    }

    public T get(int index) { // must use iteration, not recursion
        if ((index >= size) || (index < 0)) {
            return null;
        }
        //int num;
        Node<T> select;
        if (Math.abs(index - size) <= index) {
            select = backSentinel.prev;
            for (int i = size - 1; i > index; i--) {
                select = select.prev;
            }
        } else {
            select = frontSentinel.next;
            for (int i = 0; i < index; i++) {
                select = select.next;
            }
        }
        return select.value;
    }

    public int size() {
        return size;
    } // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
}
