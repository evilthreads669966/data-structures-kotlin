import com.evilthreads.queues.LinkedQueue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class LinkedQueueTest {
    private val queue = LinkedQueue<Int>()

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
    fun testClear(){
        assertFalse(queue.isEmpty())
        queue.clear()
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testIsEmpty(){
        assertFalse(queue.isEmpty())
        queue.clear()
        assertTrue(queue.isEmpty())
    }

    @Test
    fun testEnqueue(){
        assertEquals(5, queue.size)
        queue.enqueue(1)
        assertEquals(6, queue.size)
    }

    @Test
    fun testDequeue(){
        assertEquals(1, queue.dequeue())

        for(value in 2 until 6)
            assertEquals(value, queue.dequeue())

        assertNull(queue.dequeue())
    }

    @Test
    fun testPeek(){
        assertEquals(1, queue.peek())

        queue.clear()
        assertNull(queue.peek())
    }

    @Test
    fun testContains(){
        assertTrue(queue.contains(1))
        assertFalse(queue.contains(6))
    }

    @Test
    fun testContainsAll(){
        val values = mutableListOf(1,3,5)
        assertTrue(queue.containsAll(values))
        values.add(6)
        assertFalse(queue.containsAll(values))
        values.clear()
        assertFalse(queue.containsAll(values))
    }

    @Test
    fun testSize(){
        assertEquals(5, queue.size)
        queue.dequeue()
        assertEquals(4, queue.size)
        queue.enqueue(1)
        assertEquals(5, queue.size)
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
    }
}