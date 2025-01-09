import com.evilthreads.trees.BinarySearchTree
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class BinarySearchTreeTest {
    private val tree = BinarySearchTree<Int>()

    @BeforeEach
    fun setUp() {
        tree.insert(3)
        tree.insert(1)
        tree.insert(2)
        tree.insert(5)
        tree.insert(4)
        tree.insert(6)
    }

    @Test
    fun testInsert(){
        assertFalse(tree.contains(7))
        tree.insert(7)
        assertTrue(tree.contains(7))
    }

    @Test
    fun testContains(){
        assertTrue(tree.contains(4))
        assertTrue(tree.contains(3))
        assertTrue(tree.contains(2))
        assertTrue(tree.contains(5))
        assertTrue(tree.contains(6))
        assertFalse(tree.contains(7))
    }

    @Test
    fun testContainsAll(){
        val values = mutableListOf(2,4,6)
        assertTrue(tree.containsAll(values))
        values.add(10)
        assertFalse(tree.containsAll(values))
    }

    @Test
    fun testMax(){
        assertEquals(6, tree.max())
        tree.insert(10)
        assertEquals(10, tree.max())
        tree.clear()
        assertThrows(NoSuchElementException::class.java) { tree.max() }
    }

    @Test
    fun testMin(){
        assertEquals(1, tree.min())
        tree.insert(0)
        assertEquals(0, tree.min())
        tree.clear()
        assertThrows(NoSuchElementException::class.java) { tree.min() }
    }

    @Test
    fun testRemove(){
        tree.remove(5)
        assertFalse(tree.contains(5))
        assertEquals(5, tree.size)
    }

    @Test
    fun testIndexOf(){
        assertEquals(0, tree.indexOf(3))
        assertEquals(1, tree.indexOf(1))
        assertEquals(2, tree.indexOf(2))
        assertEquals(3, tree.indexOf(5))
        assertEquals(4, tree.indexOf(4))
        assertEquals(5, tree.indexOf(6))
        assertEquals(-1, tree.indexOf(9))
    }

    @Test
    fun testRemoveAt(){
        var index = tree.indexOf(4)
        tree.removeAt(index)
        index = tree.indexOf(6)
        tree.removeAt(index)
        assertFalse(tree.contains(6))
        index = tree.indexOf(5)
        tree.removeAt(index)
        assertFalse(tree.contains(5))

        assertThrows(IndexOutOfBoundsException::class.java) { tree.removeAt(tree.size) }
    }

    @Test
    fun testRetainAll(){
        val values = listOf(2,5,6)
        tree.retainAll(values)
        assertEquals(values, tree.inOrderTraversal())
        assertEquals(values.size, tree.size)
    }

    @Test
    fun testGet(){
        val index = tree.indexOf(4)
        assertEquals(4, tree.get(index))
    }

    @Test
    fun testSet(){
        val index = tree.indexOf(4)
        tree.set(index, 10)
        assertTrue(tree.contains(10))
        assertFalse(tree.contains(4))
    }

    @Test
    fun testSize(){
        assertEquals(6, tree.size)
        tree.remove(3)
        assertEquals(5, tree.size)
        tree.insert(7)
        assertEquals(6, tree.size)
        tree.removeAt(2)
        assertEquals(5, tree.size)
        tree.removeAll(listOf(4,7))
        assertEquals(3, tree.size)
        tree.clear()
        assertEquals(0, tree.size)
    }

    @Test
    fun testRemoveAll(){
        val values = listOf(2,5,6,3)
        tree.removeAll(values)
        values.forEach { assertFalse(tree.contains(it)) }
    }
}