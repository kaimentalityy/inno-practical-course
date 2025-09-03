package customlist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    @Test
    void testAddFirstAndGetFirst() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addFirst(10);
        list.addFirst(20);
        assertEquals(20, list.getFirst());
        assertEquals(10, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void testAddLastAndGetLast() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(10);
        list.addLast(20);
        assertEquals(10, list.getFirst());
        assertEquals(20, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void testAddByIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.addLast("A");
        list.addLast("C");
        list.add(1, "B");
        assertEquals("B", list.get(1));
        assertEquals(3, list.size());
    }

    @Test
    void testGetByIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        assertEquals("B", list.get(1));
    }

    @Test
    void testRemoveFirst() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        assertEquals(1, list.removeFirst());
        assertEquals(2, list.getFirst());
        assertEquals(1, list.size());
    }

    @Test
    void testRemoveLast() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        assertEquals(3, list.removeLast());
        assertEquals(2, list.getLast());
        assertEquals(2, list.size());
    }

    @Test
    void testRemoveByIndex() {
        LinkedList<String> list = new LinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        assertEquals("B", list.remove(1));
        assertEquals("C", list.get(1));
        assertEquals(2, list.size());
    }
}

