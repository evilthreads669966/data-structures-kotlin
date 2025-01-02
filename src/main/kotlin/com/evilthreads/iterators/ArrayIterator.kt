package com.evilthreads.iterators

import org.jetbrains.annotations.NotNull

class ArrayIterator<T>(private val array: Array<T?>, fromIndex: Int = 0, private val toIndex: Int = array.size - 1): Iterator<T> {
    private var index = fromIndex

    override fun hasNext(): Boolean = index <= toIndex

    @NotNull
    @Throws(IndexOutOfBoundsException::class)
    override fun next(): T = array[index++] ?: throw IndexOutOfBoundsException()
}