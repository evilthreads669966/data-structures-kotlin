import com.evilthreads.lists.LinkedList
import com.evilthreads.SortingType
import com.evilthreads.lists.toLinkedList
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class LinkedListTest {
    private val list = LinkedList<Int>()

    @BeforeEach
    fun setUp() {
        list.push(1)
        list.push(2)
        list.push(3)
        list.push(4)
        list.push(5)
    }

    @AfterEach
    fun tearDown() {
        list.clear()
        listOf(1,2,3)
    }

    @Test
    fun testClearAndIsEmpty(){
        assertFalse(list.isEmpty())

        list.clear()
        assertTrue(list.isEmpty())
    }

    @Test
    fun testPushAndPop(){
        list.push(6)
        assertEquals(6, list.size)
        assertEquals(6, list.pop())

        assertThrows(NoSuchElementException::class.java){
            list.clear()
            list.pop()
        }
    }

    @Test
    fun testPeek(){
        val value = list.peek()
        assertEquals(5, value)

        assertThrows(NoSuchElementException::class.java){
            list.clear()
            list.peek()
        }
    }

    @Test
    fun testSize(){
        val size = list.size
        assertEquals(5, size)
    }

    @Test
    fun testAdd(){
        list.add(6)
        assertEquals(6, list.last())
        assertEquals(6, list.size)
    }

    @Test
    fun testAddWithIndex(){
        list.add(2, 10)
        assertEquals(10, list[2])
        assertEquals(6, list.size)
        assertThrows(IndexOutOfBoundsException::class.java){ list.add(list.size, 100) }
        assertThrows(IndexOutOfBoundsException::class.java){ list.add(-1, 100) }
        assertThrows(IndexOutOfBoundsException::class.java){
            list.clear()
            list.add(2, 100)
        }
    }

    @Test
    fun testAddAll(){
        val values = listOf(6,7,8)
        list.addAll(values)
        assertEquals(8, list.size)
        assertIterableEquals(values, list.slice(IntRange(5,7)))
    }

    @Test
    fun testAddAllWithIndex(){
        var values = listOf(10,20)
        list.addAll(1, values)
        assertEquals(10, list[1])

        values = listOf(30,40)
        list.addAll(0, values)
        assertEquals(30, list[0])
        assertEquals(9, list.size)
        assertThrows(IndexOutOfBoundsException::class.java){ list.addAll(-1, values) }
        assertThrows(IndexOutOfBoundsException::class.java){ list.addAll(list.size, values) }
        assertThrows(IndexOutOfBoundsException::class.java){
            list.clear()
            list.addAll(0, values)
        }
    }

    @Test
    fun testRemove(){
        assertTrue(list.remove(2))
        assertFalse(list.remove(6))
        assertTrue(list.remove(5))
        assertEquals(3, list.size)
        assertEquals(4, list.first())
    }

    @Test
    fun testRemoveAll(){
        val values = listOf(2,4)
        assertTrue(list.removeAll(values))
        assertEquals(3, list.size)
        assertFalse(list.contains(2) || list.contains(4))
    }

    @Test
    fun testRemoveAt(){
        assertEquals(3, list.removeAt(2))
        assertEquals(4, list.size)
        list.removeAt(0)
        assertEquals(4, list.first())
        assertEquals(1, list.removeAt(list.size - 1))
        assertThrows(IndexOutOfBoundsException::class.java){ list.removeAt(-1) }
        assertThrows(IndexOutOfBoundsException::class.java){ list.removeAt(list.size) }


        assertThrows(IndexOutOfBoundsException::class.java){
            list.clear()
            list.removeAt(0)
        }
    }

    @Test
    fun testRemoveIfFunctional(){
        list.removeIf{ value -> value % 2 > 0 }
        list.forEach { value -> assertFalse(value % 2 > 0) }
        assertEquals(2, list.size)
    }

    @Test
    fun testContains(){
        assertTrue(list.contains(2))
        assertFalse(list.contains(6))
    }

    @Test
    fun containsAll(){
        val values = listOf(1,2,3)
        assertTrue(list.containsAll(values))
    }

    @Test
    fun testSet(){
        val value = list.set(2, 13)
        assertEquals(2, 2)
        assertEquals(13, list[2])
        assertThrows(IndexOutOfBoundsException::class.java){ list.set(10, list.size) }
        assertThrows(IndexOutOfBoundsException::class.java){ list.set(10, -1) }


        assertThrows(IndexOutOfBoundsException::class.java){
            list.clear()
            list.set(10, 0)
        }
    }

    @Test
    fun testGet(){
        assertEquals(3, list.get(2))
        assertThrows(IndexOutOfBoundsException::class.java){ list.get(-1) }
        assertThrows(IndexOutOfBoundsException::class.java){ list.get(list.size) }

        assertThrows(IndexOutOfBoundsException::class.java){
            list.clear()
            list.get(0)
        }
    }

    @Test
    fun testGetOrNull(){
        assertEquals(3, list.get(2))
        assertNull(list.getOrNull(list.size))
    }

    @Test
    fun testGetOrElse(){
        var value = list.getOrElse(10, 6)
        assertEquals(10, value)

        value = list.getOrElse(10, 2)
        assertEquals(3, value)
    }

    @Test
    fun testIndexOf(){
        assertEquals(3, list.indexOf(2))
        assertEquals(-1, list.indexOf(6))

        list.clear()
        assertEquals(-1, list.indexOf(1))
    }

    @Test
    fun testLastIndexOf(){
        val value = 2
        val index = list.indexOf(value)
        list.push(value)
        assertEquals(index + 1, list.lastIndexOf(value))
        assertEquals(-1, list.lastIndexOf(6))

        list.clear()
        assertEquals(-1, list.lastIndexOf(1))
    }

    @Test
    fun testMap(){
        val values = list.map { it + 1 }
        var value = 6

        values.forEach {
            assertEquals(value, it)
            value--
        }
    }

    @Test
    fun mapIndexed(){
        val values = list.mapIndexed(2){ value -> value + 1 }
        var value = 4
        values.forEach{ v ->
            assertEquals(value, v)
            value--
        }

        assertThrows(IndexOutOfBoundsException::class.java){ list.mapIndexed(-1){ 1 } }
        assertThrows(IndexOutOfBoundsException::class.java){ list.mapIndexed(list.size){ 1 } }
        list.clear()
        assertThrows(IndexOutOfBoundsException::class.java){ list.mapIndexed(0){ 1 } }
    }

    @Test
    fun testFilter(){
        val values = list.filter { it % 2 == 0 }
        values.forEach { assertFalse(it % 2 == 0) }
        assertEquals(3, values.size)
    }

    @Test
    fun testFold(){
        val total = list.fold(0){ acc, value -> acc + value}
        assertEquals(15, total)
    }

    @Test
    fun testDrop(){
        val values = list.drop(2)
        assertEquals(3, values.size)
        assertEquals(5, values.first())
        assertThrows(IllegalArgumentException::class.java){ values.drop(-1) }

        list.clear()
        assertEquals(0, list.drop(2).size)
        setUp()
        assertEquals(0, list.drop(list.size).size)
    }

    @Test
    fun testDropWhile(){
        val values = list.dropWhile { it > 3 }
        assertEquals(3, values.size)
    }

    @Test
    fun testTake(){
        val values = list.take(2)
        assertEquals(2, values.size)
        assertEquals(5, values.first())
        assertIterableEquals(list, list.take(list.size))
        assertThrows(IllegalArgumentException::class.java){ list.take(-1) }

        list.clear()
        assertEquals(0, list.take(2).size)
    }

    @Test
    fun testTakeWhile(){
        val values = list.takeWhile { it > 2 }
        assertEquals(3, values.size)

        var value = 5
        values.forEach {
            assertEquals(value, it)
            value--
        }
    }

    @Test
    fun testSlice(){
        val values = list.slice(IntRange(2,4))
        assertEquals(3, values.size)
        assertEquals(list[2], values.first())

        assertThrows(IndexOutOfBoundsException::class.java){ list.slice(IntRange(-1,4)) }
        assertThrows(IndexOutOfBoundsException::class.java){ list.slice(IntRange(2,1)) }

        assertThrows(IndexOutOfBoundsException::class.java){
            list.clear()
            list.slice(IntRange(1,2))
        }
    }

    @Test
    fun testSubList(){
        val values = list.subList(2,4)
        assertEquals(3, values.size)
        assertEquals(3, values.first())
        assertEquals(1, values.last())
        assertThrows(IndexOutOfBoundsException::class.java){ list.subList(-1, 4) }
        assertThrows(IndexOutOfBoundsException::class.java){ list.subList(2, 1) }

        assertThrows(IndexOutOfBoundsException::class.java){
            list.clear()
            list.subList(1,2)
        }
    }

    @Test
    fun testMax(){
        list.add(10)
        list.add(10)
        assertEquals(10, list.max())

        assertThrows(NoSuchElementException::class.java){
            list.clear()
            list.max()
        }
    }

    @Test
    fun testMin(){
        list.add(0)
        assertEquals(0, list.min())

        assertThrows(NoSuchElementException::class.java){
            list.clear()
            list.min()
        }
    }

    @Test
    fun testMaxOrNull(){
        assertEquals(5, list.maxOrNull())

        list.clear()
        assertNull(list.maxOrNull())
    }

    @Test
    fun testMinOrNull(){
        assertEquals(1, list.minOrNull())

        list.clear()
        assertNull(list.minOrNull())
    }

    @Test
    fun testZip(){
        val values = listOf("a", "b", "c")
        var pairs = list.zip(values)
        assertEquals(3, pairs.size)
        assertEquals(Pair(5, "a"), pairs.first())

        list.clear()
        pairs = list.zip(values)
        assertEquals(0, pairs.size)
    }

    @Test
    fun testAny(){
        assertTrue(list.any { it % 2 == 0 })
        assertFalse(list.any{ it > 6 })
    }

    @Test
    fun testChunked(){
        val values = listOf(10,11)
        list.addAll(values)
        var chunks = list.chunked(2)
        assertEquals(4, chunks.size)
        assertEquals(2, chunks.first().size)
        assertEquals(5, chunks.first().first())

        list.clear()
        chunks = list.chunked(2)
        assertEquals(0, chunks.size)
    }

    @Test
    fun testHigherOrderChunked(){
        var chunks = list.chunked(5){ "$it" }
        assertEquals(1, chunks.size)
        chunks = list.chunked(2){ "$it" }
        assertEquals(3, chunks.size)
        assertEquals(2, chunks.first().size)
        assertEquals(1, chunks.last().size)
        assertEquals("5", chunks.first().first())
    }

    @Test
    fun flatMap(){
        val boxA = Box(listOf(1,2,3))
        val boxB = Box(listOf(4,5,6))
        val linkedList = LinkedList<Box<Int>>()
        linkedList.add(boxA)
        linkedList.add(boxB)
        val values = linkedList.flatMap { it.values }

        assertEquals(boxA.values.size + boxB.values.size, values.size)
        assertIterableEquals(listOf(1,2,3,4,5,6), values)
    }

    @Test
    fun fromCollection(){
        val values = listOf(1,2,3)
        val linkedList = LinkedList.fromIterable(values)
        assertIterableEquals(values, linkedList)
    }

    @Test
    fun testIterator(){
        val iterator = list.iterator()
        assertTrue(iterator.hasNext())
        assertEquals(5, iterator.next())

        repeat(list.size - 1){
            iterator.next()
        }

        assertFalse(iterator.hasNext())
    }

    @Test
    fun testCompareTo(){
        val other = LinkedList<Int>(1,2,3)
        val result = list.compareTo(other)
        assertEquals(1, result)
    }

    @Test
    fun testToLinkedList(){
        val values = listOf(1,2,3)
        assertTrue(values.toLinkedList() is LinkedList<Int>)
        assertIterableEquals(values, values.toLinkedList())
    }

    @Test
    fun testVarargValues(){
        val linkedList = LinkedList(1,2,3,4,5)
        assertEquals(5, linkedList.size)

        val values = listOf(1,2,3,4,5)
        assertIterableEquals(values, linkedList)
    }

    @Test
    fun testFirst(){
        assertEquals(5, list.first())

        assertThrows(NoSuchElementException::class.java){
            list.clear()
            list.first()
        }
    }

    @Test
    fun testFirstOrNull(){
        assertEquals(5, list.firstOrNull())

        list.clear()
        assertNull(list.firstOrNull())
    }

    @Test
    fun testLastOrNull() {
        assertEquals(1, list.lastOrNull())

        list.clear()
        assertNull(list.lastOrNull())
    }

    @Test
    fun testLast(){
        var last = list.last()
        assertEquals(1, last)

        assertThrows(NoSuchElementException::class.java){
            list.clear()
            list.last()
        }
    }

    @Test
    fun testHigherOrderLast(){
        val value = list.last { it % 2 == 0 }
        assertEquals(2, value)
        assertThrows(NoSuchElementException::class.java){ list.last { it > 6 } }
    }

    @Test
    fun countOccurrences(){
        list.add(5)
        assertEquals(2, list.countOccurrences(5))
        assertEquals(0, list.countOccurrences(6))
    }

    @Test
    fun testHigherOrderCountOccurrences(){
        val count = list.countOccurrences{ value -> value % 2 == 0}
        assertEquals(2, count)
    }

    @Test
    fun testReversed(){
        val values = list.reversed()
        var value = 1

        values.forEach { v ->
            assertEquals(value, v)
            value++
        }
    }

    @Test
    fun testRetainAll(){
        val values = listOf(1,3,5)
        list.retainAll(values)
        assertEquals(3, list.size)
        assertTrue(list.containsAll(listOf(1,3,5)))
    }

    @Test
    fun testCopy(){
        val values = list.copy()
        assertEquals(list, values)
    }

    @Test
    fun testSelectionSort(){
        var values = list.shuffled().toLinkedList().apply { this.selectionSort() }
        assertEquals(list.size, values.size)
        println(values)
        list.reversed().forEachIndexed { idx, value -> assertEquals(value, values[idx]) }
    }

    @Test
    fun bubbleSort(){
        var values = list.shuffled().toLinkedList().apply{ bubbleSort() }
        assertEquals(5, list.size)

        var value = 1
        values.forEach { v ->
            assertEquals(value, v)
            value++
        }

        values = list.shuffled().toLinkedList().apply{ ( bubbleSort(SortingType.DESCENDING)) }
        value = 5
        values.forEach { v ->
            assertEquals(value, v)
            value--
        }
    }

    @Test
    fun testLinkedListToString(){
        assertEquals("[5,4,3,2,1]", list.toString())

        list.clear()
        assertEquals("", list.toString())
    }

    @Test
    fun testJoinToString(){
        val string = list.joinToString(","){ it.toString() }
        assertEquals("[5,4,3,2,1]", string)
    }

    @Test
    fun testPlusOperator(){
        list + listOf(6,7,8,9,10)
        assertEquals(10, list.size)
        assertEquals(10, list.last())

        list += listOf(11,12,13,14,15)
        assertEquals(15, list.size)
        assertEquals(15, list.last())
    }

    @Test
    fun testMinusOperator(){
        list - listOf(2,4)
        assertEquals(3, list.size)
        assertFalse(list.contains(2) || list.contains(4))
    }
}

data class Box<T>(val values: List<T>): Comparable<Box<T>> {
    @Throws(NotImplementedError::class)
    override fun compareTo(other: Box<T>): Int = throw NotImplementedError()
}