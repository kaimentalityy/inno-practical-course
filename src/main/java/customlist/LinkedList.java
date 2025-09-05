package customlist;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A simple generic singly linked list implementation supporting basic operations
 * such as adding, retrieving, and removing elements.
 *
 * @param <T> the type of elements held in this list
 */
public class LinkedList<T> {

    /**
     * Inner class representing a single node in the linked list.
     */
    private class Node {
        T data;
        Node next;
        /**
         * Constructs a new node with the given data.
         *
         * @param data the data to store in this node
         */
        Node(T data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Adds an element at the beginning of the list.
     *
     * @param el the element to be added
     */
    public void addFirst(T el) {
        Node node = new Node(el);
        node.next = head;
        head = node;
        if (tail == null){
            tail = head;
        }
        size++;
    }

    /**
     * Adds an element at the end of the list.
     *
     * @param el the element to be added
     */
    public void addLast(T el) {
        Node node = new Node(el);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /**
     * Adds an element at the specified index in the list.
     *
     * @param index the position at which to insert the element
     * @param el the element to be inserted
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void add(int index, T el) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        if (index == 0) {
            addFirst(el);
            return;
        }
        if (index == size) {
            addLast(el);
            return;
        }

        Node prev = getNode(index - 1);
        Node node = new Node(el);
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    /**
     * Returns the first element of the list.
     *
     * @return the first element
     * @throws IllegalStateException if the list is empty
     */
    public T getFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        return head.data;
    }

    /**
     * Returns the last element of the list.
     *
     * @return the last element
     * @throws IllegalStateException if the list is empty
     */
    public T getLast() {
        if (tail == null) throw new IllegalStateException("List is empty");
        return tail.data;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index the position of the element
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T get(int index) {
        return getNode(index).data;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * @return the first element
     * @throws IllegalStateException if the list is empty
     */
    public T removeFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        T data = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return data;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * @return the last element
     * @throws IllegalStateException if the list is empty
     */
    public T removeLast() {
        if (tail == null){
            throw new IllegalStateException("List is empty");
        }
        if (size == 1){
            return removeFirst();
        }

        Node prev = getNode(size - 2);
        T data = tail.data;
        tail = prev;
        tail.next = null;
        size--;
        return data;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index the position of the element
     * @return the removed element
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public T remove(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if (index == 0){
            return removeFirst();
        }
        if (index == size - 1){
            return removeLast();
        }

        Node prev = getNode(index - 1);
        T data = prev.next.data;
        prev.next = prev.next.next;
        size--;
        return data;
    }

    /**
     * Helper method to retrieve a node by its index.
     *
     * @param index the position of the node
     * @return the node at the given index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    private Node getNode(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the list, resetting it to empty.
     */
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an Iterator over the elements in this list
     */
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    /**
     * Returns a string representation of the list in the format [elem1, elem2, ...].
     *
     * @return a string representing the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            current = current.next;
            if (current != null){
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    /**
     * Compares this list to another object for equality.
     * <p>
     * Two LinkedList instances are considered equal if:
     * <ul>
     *   <li>The other object is not null</li>
     *   <li>It is the same class as this list</li>
     *   <li>The sizes of the lists are equal</li>
     *   <li>The head and tail nodes are equal (recursively compares the linked nodes)</li>
     * </ul>
     *
     * @param o the object to compare with this list
     * @return true if the specified object is equal to this list, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        LinkedList<?> that = (LinkedList<?>) o;
        return size == that.size && Objects.equals(head, that.head) && Objects.equals(tail, that.tail);
    }

    /**
     * Returns a hash code value for the list.
     * <p>
     * The hash code is computed based on the head node, tail node, and the size of the list.
     * This ensures that two lists that are equal according to {@link #equals(Object)}
     * will return the same hash code.
     *
     * @return the hash code value for this list
     */
    @Override
    public int hashCode() {
        return Objects.hash(head, tail, size);
    }
}

