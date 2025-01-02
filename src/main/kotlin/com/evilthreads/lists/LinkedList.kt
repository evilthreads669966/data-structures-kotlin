package com.evilthreads.lists

import com.evilthreads.Node
import com.evilthreads.SortingType
import com.evilthreads.iterators.MutableLinkedIterator
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*
import kotlin.NoSuchElementException

class LinkedList<T : Comparable<T>>(vararg values: T) : MutableList<T>, Comparable<LinkedList<T>> {
    companion object {
        @NotNull
        @JvmStatic
        fun <T : Comparable<T>> fromIterable(@NotNull values: Iterable<T>): LinkedList<T> =
            LinkedList<T>().apply { addAll(values) }
    }

    override val size: Int
        get() = _size

    @Nullable
    private var head: Node<T>? = null

    @Nullable
    private var tail: Node<T>? = null
    private var _size = 0

    init {
        values.forEach { value ->
            add(value)
        }
    }

    @NotNull
    fun copy(): LinkedList<T> {
        val list = LinkedList<T>()
        list.addAll(this)

        return list
    }

    fun push(@NotNull value: T) {
        _size++
        val node = Node(value)

        if (isEmpty()) {
            head = node
            tail = head
        } else {
            node.next = head
            head = node
        }
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun pop(): T {
        val value = head?.value ?: throw NoSuchElementException()
        head = head!!.next

        _size--

        return value
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun peek(): T = head?.value ?: throw NoSuchElementException()

    override fun isEmpty(): Boolean = head == null

    override fun clear() {
        head = null
        tail = null
        _size = 0;
    }

    @Throws(IndexOutOfBoundsException::class)
    override fun set(index: Int, @NotNull value: T): T = value.apply { getNode(index).value = this }

    @NotNull
    @Throws(IndexOutOfBoundsException::class)
    override operator fun get(index: Int): T = getNode(index).value

    @Nullable
    fun getOrNull(index: Int): T? {
        try {
            return get(index)
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }

    @NotNull
    fun getOrElse(@NotNull defaultValue: T, index: Int): T = getOrNull(index) ?: defaultValue

    override fun remove(@NotNull value: T): Boolean {
        if (isEmpty())
            return false
        if (head!!.value == value) {
            pop()
            return true
        }
        var curr = head!!.next
        var prev = head

        while (curr != null) {
            if (curr.value == value) {
                curr = curr.next
                prev!!.next = curr?.next

                if (curr == null)
                    tail = prev

                _size--

                return true
            }

            prev = curr
            curr = curr.next
        }

        return false
    }

    override fun removeAll(values: Collection<T>): Boolean {
        var removed = false

        if (isEmpty())
            return removed

        var curr = head
        var prev: Node<T>? = null

        while (curr != null) {
            if (values.contains(curr.value)) {
                curr = curr.next
                if (prev == null)
                    head = curr
                else
                    prev.next = curr

                removed = true
                _size--

                if (curr == null)
                    tail = prev

                continue
            }

            prev = curr
            curr = curr.next
        }

        return removed
    }

    fun removeIf(@NotNull predicate: (T) -> Boolean): Boolean {
        if (isEmpty())
            return false

        var removed = false
        var curr = head
        var prev: Node<T>? = null

        while (curr != null) {
            if (predicate(curr.value)) {
                curr = curr.next
                if (prev == null)
                    head = curr
                else
                    prev.next = curr

                _size--
                removed = true

                if (curr == null)
                    tail = prev

                continue
            }

            prev = curr
            curr = curr.next
        }

        return removed
    }

    @NotNull
    @Throws(IndexOutOfBoundsException::class)
    override fun removeAt(index: Int): T {
        if (isEmpty() || index < 0 || index >= _size)
            throw IndexOutOfBoundsException()

        if (index == 0)
            return pop()

        val curr = getNode(index - 1)
        val value = curr.next!!.value
        curr.next = curr.next?.next
        _size--

        if (curr.next == tail)
            tail = curr

        return value
    }

    @Throws(IndexOutOfBoundsException::class)
    override fun add(index: Int, value: T) {
        if (isEmpty() || index < 0 || index >= _size)
            throw IndexOutOfBoundsException()

        if (index == 0) {
            push(value)
            return
        }

        val curr = getNode(index - 1)
        val node = Node(value)
        node.next = curr.next
        curr.next = node
        _size++
    }

    override fun add(@NotNull value: T): Boolean {
        _size++
        val node = Node(value)

        if (isEmpty()) {
            head = node
            tail = head

            return true
        }

        tail!!.next = node
        tail = tail!!.next

        return true
    }

    override fun addAll(@NotNull values: Collection<T>): Boolean {
        values.forEach { value ->
            add(value)
        }

        return true
    }

    @Throws(IndexOutOfBoundsException::class)
    override fun addAll(index: Int, values: Collection<T>): Boolean {
        if (index >= _size)
            throw IndexOutOfBoundsException()

        var curr: Node<T>?
        val values = values.toMutableList()

        if (index == 0) {
            push(values.removeFirst())
            curr = getNode(0)
        } else {
            curr = getNode(index - 1)
        }

        values.forEach { value ->
            val node = Node(value)
            node.next = curr!!.next
            curr!!.next = node

            _size++
            curr = curr!!.next
        }

        return true
    }

    operator fun plus(values: Collection<T>) = addAll(values)

    operator fun minus(values: Collection<T>) = removeAll(values)

    override fun indexOf(@NotNull value: T): Int {
        if (isEmpty())
            return -1

        var curr = head
        var idx = 0

        while (curr != null) {
            if (curr.value == value)
                return idx

            idx++
            curr = curr.next
        }

        return -1
    }

    override fun lastIndexOf(@NotNull value: T): Int {
        var idx = -1

        if (isEmpty())
            return idx

        var curr = head
        var index = 0

        while (curr != null) {
            if (curr.value == value)
                idx = index

            index++
            curr = curr.next
        }

        return idx
    }

    override fun containsAll(@NotNull c: Collection<T>): Boolean {
        if (isEmpty() || c.isEmpty())
            return false

        return c.all{ value -> contains(value) }
    }

    override fun contains(@NotNull value: T): Boolean {
        var curr = head

        while (curr != null) {
            if (curr.value == value)
                return true

            curr = curr.next
        }

        return false
    }

    @Nullable
    fun firstOrNull(): T? {
        try {
            return first()
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun first(): T = peek()

    @Nullable
    fun lastOrNull(): T? = tail?.value

    @NotNull
    @Throws(NoSuchElementException::class)
    fun last(): T = tail?.value ?: throw NoSuchElementException()

    @NotNull
    @Throws(NoSuchElementException::class)
    fun last(predicate: (T) -> Boolean): T {
        var curr = head
        var value: T? = null

        while (curr != null) {
            if (predicate(curr.value))
                value = curr.value

            curr = curr.next
        }

        return value ?: throw NoSuchElementException()
    }


    override fun retainAll(values: Collection<T>): Boolean {
        var removed = false

        if (isEmpty())
            return removed

        var curr = head
        var prev: Node<T>? = null

        while (curr != null) {
            if (!values.contains(curr.value)) {
                curr = curr.next
                if (prev == null)
                    head = curr
                else
                    prev.next = curr

                removed = true
                _size--

                continue
            }

            prev = curr
            curr = curr.next
        }

        return removed
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun max(): T {
        if (isEmpty())
            throw NoSuchElementException()

        var max = head!!.value

        if (head!!.next == null)
            return max

        var curr = head!!.next

        while (curr != null) {
            if (max.compareTo(curr.value) == -1) {
                max = curr.value
            }

            curr = curr.next
        }

        return max
    }

    @Nullable
    fun maxOrNull(): T? {
        try {
            return max()
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun min(): T {
        if (isEmpty())
            throw NoSuchElementException()

        if (head!!.next == null)
            return head!!.value

        var min = head!!.value
        var curr = head!!.next

        while (curr != null) {
            if (min.compareTo(curr.value) == 1) {
                min = curr.value
            }

            curr = curr.next
        }

        return min
    }

    @Nullable
    fun minOrNull(): T? {
        try {
            return min()
        } catch (e: NoSuchElementException) {
            return null
        }
    }

    @NotNull
    @Throws(IndexOutOfBoundsException::class)
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = slice(IntRange(fromIndex, toIndex))

    @NotNull
    @Throws(IndexOutOfBoundsException::class)
    fun slice(@NotNull range: IntRange): MutableList<T> {
        if (range.first > range.last)
            throw IndexOutOfBoundsException()

        var curr: Node<T>? = getNode(range.first)
        var idx = range.first
        val list = ArrayList<T>()

        while (curr != null && idx <= range.last) {
            list.add(curr.value)

            idx++
            curr = curr.next
        }

        return list
    }

    @NotNull
    fun <S> fold(@NotNull initial: S, @NotNull operation: (S, T) -> S): S {
        if (isEmpty())
            return initial

        var accumulator = initial
        var curr = head

        while (curr != null) {
            accumulator = operation(accumulator, curr.value)

            curr = curr.next
        }

        return accumulator
    }

    @NotNull
    fun mapIndexed(index: Int, transform: (T) -> T): List<T> {
        if (isEmpty() || index < 0 || index >= _size)
            throw IndexOutOfBoundsException()

        val list = LinkedList<T>()
        var curr: Node<T>? = getNode(index)

        while (curr != null) {
            curr.value = transform(curr.value)
            list.add(curr.value)

            curr = curr.next
        }

        return list
    }

    @NotNull
    fun chunked(n: Int): List<List<T>> {
        val chunks = ArrayList<List<T>>()

        if (isEmpty() || n <= 0)
            return chunks

        val quotient = _size / n
        val remainder = _size % n

        if (quotient == 0 || (quotient == 1 && remainder == 0))
            return chunks.apply { add(this@LinkedList) }

        val groups: Int = if (remainder > 0) quotient + 1 else quotient
        var curr = head

        repeat(groups) {
            val chunk = ArrayList<T>(n)
            var count = 0

            while (curr != null && count < n) {
                chunk.add(curr!!.value)

                curr = curr!!.next
                count++
            }

            chunks.add(chunk)
        }

        return chunks
    }

    @NotNull
    fun <S> chunked(n: Int, transform: (T) -> S): List<List<S>> {
        val chunks = LinkedList<List<S>>()

        if (isEmpty() || n <= 0)
            return chunks

        val quotient = _size / n
        val remainder = _size % n

        if (quotient == 0 || (quotient == 1 && remainder == 0)) {
            val list = LinkedList<S>()
            this.forEach { value ->
                list.add(transform(value))
            }
            return chunks.apply { add(this@LinkedList.map { transform(it) }) }
        }

        val groups: Int = if (remainder > 0) quotient + 1 else quotient
        var curr = head

        repeat(groups) {
            val chunk = LinkedList<S>()
            var count = 0

            while (curr != null && count < n) {
                chunk.add(transform(curr!!.value))

                curr = curr!!.next
                count++
            }

            chunks.add(chunk)
        }

        return chunks
    }

    fun any(@NotNull predicate: (T) -> Boolean): Boolean {
        if(isEmpty())
            return false

        var curr = head

        while (curr != null) {
            if (!predicate(curr.value))
                return false

            curr = curr.next
        }

        return true
    }

    fun all(predicate: (T) -> Boolean): Boolean {
        var curr = head

        while (curr != null) {
            if(!predicate(curr.value))
                return false

            curr = curr.next
        }

        return true
    }

    @NotNull
    fun <S> zip(@NotNull values: Collection<S>): List<Pair<T, S>> {
        val pairs = ArrayList<Pair<T, S>>()

        if (isEmpty() || values.isEmpty())
            return pairs

        values.forEachIndexed { index, v ->
            val value = getOrNull(index) ?: return@forEachIndexed
            pairs.add(Pair(value, v))
        }

        return pairs
    }

    @NotNull
    fun <S> flatMap(transform: (T) -> Iterable<S>): List<S> {
        val list = ArrayList<S>()

        var curr = head

        while (curr != null) {
            val values = transform(curr.value)
            list.addAll(values)

            curr = curr.next
        }

        return list
    }

    @NotNull
    fun <S> map(transform: (T) -> S): List<S> {
        var curr = head
        val list = LinkedList<S>()

        while (curr != null) {
            list.add(transform(curr.value))

            curr = curr.next
        }

        return list
    }

    @NotNull
    fun filter(@NotNull predicate: (T) -> Boolean): List<T> {
        if (isEmpty())
            return this

        val list = ArrayList<T>()
        var curr = head

        while (curr != null) {
            if (!predicate(curr.value))
                list.add(curr.value)

            curr = curr.next
        }

        return list
    }

    @NotNull
    @Throws(IllegalArgumentException::class)
    fun drop(n: Int): List<T> {
        if (n < 0)
            throw IllegalArgumentException()

        if (n == 0)
            return this

        val list = ArrayList<T>()

        if (isEmpty() || n >= _size)
            return list

        var curr = head
        var idx = 0

        while (curr != null && idx < _size - n) {
            list.add(curr.value)

            idx++
            curr = curr.next
        }

        return list
    }

    @NotNull
    fun dropWhile(@NotNull predicate: (T) -> Boolean): List<T> {
        val list = LinkedList<T>()

        if (isEmpty())
            return list

        var curr = head

        while (curr != null && predicate(curr.value)) {
            curr = curr.next
        }

        while (curr != null) {
            list.add(curr.value)

            curr = curr.next
        }

        return list
    }

    @NotNull
    @Throws(IllegalArgumentException::class)
    fun take(n: Int): List<T> {
        if (n < 0)
            throw IllegalArgumentException()

        if (isEmpty() || n >= _size - 1)
            return this

        val list = ArrayList<T>()

        if (n == 0)
            return list

        var curr = head
        var idx = 0

        while (curr != null && idx < n) {
            list.add(curr.value)

            curr = curr.next
            idx++
        }

        return list
    }

    @NotNull
    fun takeWhile(predicate: (T) -> Boolean): List<T> {
        var curr = head
        val list = LinkedList<T>()

        while (curr != null) {
            if (predicate(curr.value))
                list.add(curr.value)
            else
                return list

            curr = curr.next
        }

        return list
    }

    @NotNull
    fun reversed(): List<T> {
        val list = LinkedList<T>()
        var curr = head
        while (curr != null) {
            list.push(curr.value)

            curr = curr.next
        }

        return list
    }

    fun countOccurrences(@NotNull value: T): Int {
        if (isEmpty())
            return 0

        var curr = head
        var count = 0

        while (curr != null) {
            if (curr.value.equals(value))
                count++

            curr = curr.next
        }

        return count
    }

    fun countOccurrences(predicate: (T) -> Boolean): Int {
        if (isEmpty())
            return 0

        var curr = head
        var count = 0

        while (curr != null) {
            if (predicate(curr.value))
                count++

            curr = curr.next
        }

        return count
    }

    fun sort(sortingType: SortingType = SortingType.ASCENDING) = selectionSort(sortingType)

    fun selectionSort(sortingType: SortingType = SortingType.ASCENDING) {
        if (isEmpty())
            return

        val greaterOrSmaller: Int

        if (sortingType == SortingType.ASCENDING)
            greaterOrSmaller = 1
        else
            greaterOrSmaller = -1

        var startingNode = head!!
        var curr = head!!.next

        while (curr != null) {
            while (curr != null) {
                if (startingNode.value.compareTo(curr.value) == greaterOrSmaller)
                    swapValues(startingNode, curr)

                curr = curr.next
            }

            startingNode = startingNode.next!!
            curr = startingNode.next
        }
    }

    fun bubbleSort(sortingType: SortingType = SortingType.ASCENDING) {
        if (isEmpty() || _size == 1)
            return

        val greaterOrLess: Int

        if (sortingType == SortingType.ASCENDING)
            greaterOrLess = 1
        else
            greaterOrLess = -1

        repeat(_size) { i ->
            var curr = head
            var idx = 0

            while (idx < _size - 1 - i) {
                if (curr!!.value.compareTo(curr.next!!.value) == greaterOrLess)
                    swapValues(curr, curr.next!!)

                curr = curr.next
                idx++
            }
        }
    }

    private fun swapValues(@NotNull left: Node<T>, @NotNull right: Node<T>) {
        val temp = left.value
        left.value = right.value
        right.value = temp
    }

    @NotNull
    @Throws(IndexOutOfBoundsException::class)
    private fun getNode(index: Int): Node<T> {
        if (isEmpty() || index < 0 || index >= _size)
            throw IndexOutOfBoundsException()

        var curr = head
        var idx = 0

        while (curr != null) {
            if (idx == index)
                return curr

            curr = curr.next
            idx++
        }

        throw IndexOutOfBoundsException()
    }

    override fun compareTo(other: LinkedList<T>): Int {
        if (this._size > other._size)
            return 1
        if (this._size < other._size)
            return -1
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (other !is LinkedList<*>) return false
        if (_size != other._size) return false

        var curr = head
        var otherCurr = other.head

        while (curr != null) {
            if (curr.value != otherCurr!!.value)
                return false

            curr = curr.next
            otherCurr = otherCurr.next
        }

        return true
    }

    override fun toString(): String = joinToString(separator = ",") { it.toString() }

    @NotNull
    fun joinToString(
        separator: String = " ",
        prefix: String = "[",
        postfix: String = "]",
        transform: (T) -> String
    ): String {
        if (isEmpty())
            return ""

        var curr = head
        val sb = StringBuilder(prefix)

        while (curr != null) {
            val string = transform(curr.value)

            if (curr.next == null) {
                sb.append(string)
                break
            } else {
                sb.append("$string$separator")
            }

            curr = curr.next
        }
        sb.append(postfix)

        return sb.toString()
    }

    @NotNull
    override fun iterator(): MutableIterator<T> {
        return MutableLinkedIterator(head)
    }

    @Throws(NotImplementedError::class)
    override fun listIterator(): MutableListIterator<T> {
        throw NotImplementedError()
    }

    @Throws(NotImplementedError::class)
    override fun listIterator(index: Int): MutableListIterator<T> {
        throw NotImplementedError()
    }
}
@NotNull
fun <T: Comparable<T>> Iterable<T>.toLinkedList(): LinkedList<T> = LinkedList.fromIterable(this)