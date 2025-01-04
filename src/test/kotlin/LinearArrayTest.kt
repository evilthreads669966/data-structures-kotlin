import com.evilthreads.arrays.LinearArray
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class LinearArrayTest {
    private val array = LinearArray<Int>(6)

    @BeforeEach
    fun setup() {
        array.add(1)
        array.add(2)
        array.add(3)
        array.add(4)
        array.add(5)
    }

    @AfterEach
    fun tearDown() {
        array.clear()
    }

    @Test
    fun testIsEmpty(){
        assertFalse(array.isEmpty())
        array.clear()
        assertTrue(array.isEmpty())
    }

    @Test
    fun testClear(){
        assertFalse(array.isEmpty())
        array.clear()
        assertTrue(array.isEmpty())
    }

    @Test
    fun testAdd(){
        array.add(6)
        assertTrue(array.contains(6))
        assertEquals(6, array.get(5))
    }

    @Test
    fun testAddAll(){
        val values = listOf(6,7,8)
        array.addAll(values)
        assertTrue(array.containsAll(values))
    }

    @Test
    fun testRemove(){
        array.remove(3)
        assertFalse(array.contains(3))
    }

    @Test
    fun testRemoveAll(){
        val values = listOf(2,4)
        array.removeAll(values)
        assertFalse(array.containsAll(values))
    }

    @Test
    fun testGet(){
        assertEquals(3, array[2])
    }

    @Test
    fun testSet(){
        array.set(2, 10)
        assertEquals(10, array[2])
    }

    @Test
    fun testContains(){
        assertTrue(array.contains(3))
        assertFalse(array.contains(6))
    }

    @Test
    fun testContainsAll(){
        val values = mutableListOf(1,3,5)
        assertTrue(array.containsAll(values))
        values.add(6)
        assertFalse(array.containsAll(values))
    }

    @Test
    fun testSize(){
        assertEquals(5, array.size)
        array.add(6)
        assertEquals(6, array.size)
        array.remove(3)
        assertEquals(5, array.size)
        array.clear()
        assertEquals(0, array.size)
    }

    @Test
    fun testRetainAll(){
        val values = listOf(1,3,5)
        array.retainAll(values)
        assertEquals(3, array.size)
        assertFalse(array.contains(4))
        assertFalse(array.contains(2))
    }

    @Test
    fun testResize(){
        array.addAll(listOf(6,7,8,9,10))
        assertEquals(10, array.size)
    }

    @Test
    fun testIterator(){
        val iterator = array.iterator()
        assertTrue(iterator.hasNext())
        iterator.next()
        iterator.next()
        iterator.remove()
        assertFalse(array.contains(2))
    }

    @Test
    fun testToString(){
        val string = "[1 2 3 4 5]"
        assertEquals(string, array.toString())
    }
}