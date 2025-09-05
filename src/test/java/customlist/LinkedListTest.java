package customlist;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

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

    @Test
    void testIsEmpty() {
        LinkedList<Integer> list = new LinkedList<>();
        assertTrue(list.isEmpty());
        list.addFirst(1);
        assertFalse(list.isEmpty());
    }

    @Test
    void testClear() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void testIterator() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        int sum = 0;
        for (Integer val : list) {
            sum += val;
        }
        assertEquals(6, sum);

        // Explicit iterator test
        Iterator<Integer> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertTrue(it.hasNext());
        assertEquals(2, it.next());
        assertTrue(it.hasNext());
        assertEquals(3, it.next());
        assertFalse(it.hasNext());
    }


    @Test
    void testToString() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        assertEquals("[1, 2, 3]", list.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        list1.addLast(1);
        list1.addLast(2);
        list2.addLast(1);
        list2.addLast(2);

        assertEquals(list1, list2);
        assertEquals(list1.hashCode(), list2.hashCode());

        list2.addLast(3);
        assertNotEquals(list1, list2);
        assertNotEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    void testEqualsWithDifferentTypesAndNull() {
        LinkedList<Integer> list = new LinkedList<>();
        assertNotEquals(null, list);
        assertNotEquals(new Object(), list);
    }
}

