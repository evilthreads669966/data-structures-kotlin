package com.evilthreads.iterators

class ArrayStackIterator<T>(private val array: Array<T?>, private var top: Int): Iterator<T> {

    override fun hasNext(): Boolean = top != -1

    override fun next(): T = array[top--]!!
}