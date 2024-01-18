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
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        this.front = new Node<>(null);
        this.back = new Node<>(null);
        front.next = back;
        back.prev = front;
        size = 0;
    }

    public void addFirst(T item) { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        Node<T> hold = front.next;
        Node<T> addNode = new Node<>(item, front, front.next);
        front.next = addNode;
        hold.prev = addNode;
        size += 1;
    }

    public void addLast(T item) { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        Node<T> hold = back.prev;
        Node<T> addNode = new Node<>(item, back.prev, back);
        back.prev = addNode;
        hold.next = addNode;
        size += 1;
    }

    public T removeFirst() { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        if (size == 0) {
            return null;
        }
        Node<T> removed = front.next;
        Node<T> hold = front.next.next;
        front.next = hold;
        hold.prev = front;
        size -= 1;
        return removed.value;
    }

    public T removeLast() { // must run in constant time (operations must not involve any looping or recursion). Note: size is provided for you
        if (size == 0) {
            return null;
        }
        Node<T> removed = back.prev;
        Node<T> hold = back.prev.prev;
        back.prev = hold;
        hold.next = back;
        size -= 1;
        return removed.value;
    }

    public T get(int index) { // must use iteration, not recursion
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> select;
        if (Math.abs(index - size) <= index) {
            select = back.prev;
            for (int i = size - 1; i > index; i--) {
                select = select.prev;
            }
        } else {
            select = front.next;
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
