import com.evilthreads.queues.CircularArrayQueue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CirclularArrayQueueTest {
    private var queue = CircularArrayQueue<Int>(5)

    @BeforeEach
    fun setUp() {
        queue.enqueue(1)
        queue.enqueue(2)
        queue.enqueue(3)
        queue.enqueue(4)
        queue.enqueue(5)
    }

    @AfterEach
    fun tearDown() {
        queue.clear()
    }

    @Test
    fun testEnqueue(){
        queue.enqueue(6)
        assertEquals(6, queue.size)
        assertEquals(6, queue.last())
    }

    @Test
    fun testDequeue(){
        assertEquals(1, queue.dequeue())
        assertEquals(2, queue.dequeue())

        queue.clear()
        assertNull(queue.dequeue())
    }

    @Test
    fun testPeek(){
        assertEquals(1, queue.peek())

        queue.clear()
        assertNull(queue.peek())
    }

    @Test
    fun testLast(){
        assertEquals(5, queue.last())

        queue.clear()
        assertNull(queue.last())

        queue.enqueue(1)
        assertEquals(1, queue.last())
    }

    @Test
    fun testSize(){
        assertEquals(5, queue.size)
        queue.enqueue(6)
        assertEquals(6, queue.size)
        queue.dequeue()
        assertEquals(5, queue.size)
        queue.clear()
        assertEquals(0, queue.size)
    }

    @Test
    fun testContains(){
        assertTrue(queue.contains(3))
        assertFalse(queue.contains(6))
        queue.dequeue()
        assertTrue(queue.contains(3))
    }

    @Test
    fun testContainsAll(){
        val values = mutableListOf(1,3,5)
        assertTrue(values.containsAll(values))
        values.add(6)
        assertFalse(queue.containsAll(values))
    }

    @Test
    fun testResize(){
        queue.enqueue(6)
        queue.enqueue(7)
        assertEquals(7, queue.size)
    }

    @Test
    fun testToString(){
        val string = "[1 2 3 4 5]"
        assertEquals(string, queue.toString())
    }

    @Test
    fun testIterator(){
        val iterator = queue.iterator()
        assertTrue(iterator.hasNext())
        assertNotNull(iterator.next())

        repeat(4){
            iterator.next()
        }

        assertFalse(iterator.hasNext())

        assertThrows(IndexOutOfBoundsException::class.java){ iterator.next() }
    }
}