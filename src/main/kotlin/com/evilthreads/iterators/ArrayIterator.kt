package com.evilthreads.iterators

class ArrayIterator<T>(private val array: Array<T?>, fromIndex: Int = 0, private val toIndex: Int = array.size - 1): Iterator<T> {
    private var index = fromIndex

    override fun hasNext(): Boolean = index <= toIndex

    override fun next(): T = array[index++]!!
}