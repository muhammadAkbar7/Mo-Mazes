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
        frontSentinel = new Node<>(null, null, front);
        backSentinel = new Node<>(null, back, null);
        frontSentinel.next = backSentinel;
        backSentinel.prev = frontSentinel;
        front = frontSentinel;
        back = backSentinel;
        size = 0;
        // TODO: replace this with your code
    }

    public void addFirst(T item) { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        size += 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void addLast(T item) { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        size += 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeFirst() { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        if (size == 0) {
            return null;
        }
        size -= 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeLast() { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        if (size == 0) {
            return null;
        }
        size -= 1;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T get(int index) { // must use iteration, not recursion
        if ((index >= size) || (index < 0)) {
            return null;
        }
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int size() {
        return size;
    } // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
}
