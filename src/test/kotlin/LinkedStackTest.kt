import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import com.evilthreads.stacks.LinkedStack

class LinkedStackTest {
    private val stack = LinkedStack<Int>()

    @BeforeEach
    fun setUp() {
        stack.push(1)
        stack.push(2)
        stack.push(3)
        stack.push(4)
        stack.push(5)
    }

    @AfterEach
    fun tearDown() {
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
        assertFalse(stack.isEmpty())
        stack.clear()
        assertTrue(stack.isEmpty())
    }

    @Test
    fun testPush(){
        stack.push(6)
        assertEquals(6, stack.pop())
    }

    @Test
    fun testPop(){
        assertEquals(5, stack.pop())

        stack.clear()
        assertThrows(NoSuchElementException::class.java) { stack.pop() }
    }

    @Test
    fun testPeek(){
        assertEquals(5, stack.peek())

        stack.clear()
        assertThrows(NoSuchElementException::class.java) { stack.peek() }
    }

    @Test
    fun testSize(){
        assertEquals(5, stack.size)
        stack.push(6)
        assertEquals(6, stack.size)
        stack.pop()
        assertEquals(5, stack.size)
        stack.clear()
        assertEquals(0, stack.size)
    }

    @Test
    fun testSelectionSort(){
        stack.clear()
        stack.push(2)
        stack.push(5)
        stack.push(1)
        stack.push(3)
        stack.push(4)

        stack.selectionSort()

        for(value in 5 downTo  1){
            assertEquals(value, stack.pop())
        }
    }

    @Test
    fun testToString(){
        val string = "[5 4 3 2 1]"
        assertEquals(string, stack.toString())
    }
}