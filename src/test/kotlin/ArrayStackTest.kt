import com.evilthreads.SortingType
import com.evilthreads.stacks.ArrayStack
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArrayStackTest {
    private val stack = ArrayStack<Int>(20)

    @BeforeEach
    fun setUp() {
        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        stack.push(5)
    }

    @AfterEach
    fun tearDown(){
        stack.clear()
    }

    @Test
    fun testIsEmpty(){
        assertFalse(stack.isEmpty())
        stack.clear()
        assertTrue(stack.isEmpty())
    }

    @Test
    fun testClear(){
        stack.clear()
        assertTrue(stack.isEmpty())
    }

    @Test
    fun testSize(){
        assertEquals(5, stack.size)
    }

    @Test
    fun testPush(){
        stack.push(6)
        assertEquals(6, stack.size)
        assertEquals(6, stack.pop())
    }

    @Test
    fun testPop(){
        assertEquals(5, stack.pop())
        assertTrue(stack.pop() is Int)
        stack.clear()
        assertThrows(NoSuchElementException::class.java) { stack.pop() }
    }

    @Test
    fun testPeek(){
        assertEquals(5, stack.peek())
        assertTrue(stack.peek() is Int)
        stack.clear()
        assertThrows(NoSuchElementException::class.java) { stack.peek() }
    }

    @Test
    fun testResize(){
        repeat(15){
            stack.push(it)
        }
        assertEquals(20, stack.size)
        stack.push(100)
        assertEquals(21, stack.size)
    }

    @Test
    fun selectionSort(){
        stack.clear()
        stack.push(2)
        stack.push(5)
        stack.push(1)
        stack.push(3)
        stack.push(4)

        stack.selectionSort(SortingType.ASCENDING)
        for(i in 5 downTo 1){
            assertEquals(i, stack.pop())
        }
    }

    @Test
    fun testIterator(){
        val iterator = stack.iterator()
        assertTrue(iterator.hasNext())
        assertNotNull(iterator.next())
        repeat(4){
            iterator.next()
        }

        assertFalse(iterator.hasNext())
    }

    @Test
    fun testContains(){
        assertTrue(stack.contains(3))
        assertFalse(stack.contains(6))
    }

    @Test
    fun testContainsAll(){
        val values = mutableListOf(1,3,5)
        assertTrue(stack.containsAll(values))
        values.add(6)
        assertFalse(stack.containsAll(values))
    }

}