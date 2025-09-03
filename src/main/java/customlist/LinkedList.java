package customlist;

public class LinkedList<T> {

    private class Node {
        T data;
        Node next;
        Node(T data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;

    public int size() {
        return size;
    }

    public void addFirst(T el) {
        Node node = new Node(el);
        node.next = head;
        head = node;
        if (tail == null) tail = head;
        size++;
    }

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

    public T getFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        return head.data;
    }

    public T getLast() {
        if (tail == null) throw new IllegalStateException("List is empty");
        return tail.data;
    }

    public T get(int index) {
        return getNode(index).data;
    }

    public T removeFirst() {
        if (head == null) throw new IllegalStateException("List is empty");
        T data = head.data;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return data;
    }

    public T removeLast() {
        if (tail == null) throw new IllegalStateException("List is empty");
        if (size == 1) return removeFirst();

        Node prev = getNode(size - 2);
        T data = tail.data;
        tail = prev;
        tail.next = null;
        size--;
        return data;
    }

    public T remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node prev = getNode(index - 1);
        T data = prev.next.data;
        prev.next = prev.next.next;
        size--;
        return data;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
}

