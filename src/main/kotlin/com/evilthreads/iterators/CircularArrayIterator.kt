package com.evilthreads.iterators

class CircularArrayIterator<T>(private val array: Array<T?>, private val front: Int, private val rear: Int): Iterator<T> {
    private var index = front

    override fun hasNext(): Boolean = index != rear

    @Throws(IndexOutOfBoundsException::class)
    override fun next(): T {
        val value = array[index++] ?: throw IndexOutOfBoundsException()
        if(front > rear && index == array.size)
            index = 0

        return value
    }
}