package com.evilthreads.iterators

class ArrayIterator<T>(private val array: Array<T?>): Iterator<T> {
    private var index = -1

    override fun hasNext(): Boolean = array[index + 1] != null

    override fun next(): T = array[++index]!!
}