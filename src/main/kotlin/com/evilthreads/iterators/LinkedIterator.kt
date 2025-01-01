package com.evilthreads.iterators

import com.evilthreads.Node
import org.jetbrains.annotations.Nullable

class LinkedIterator<T: Comparable<T>>(@Nullable private var curr: Node<T>?): Iterator<T> {
    private var prev: Node<T>? = null

    override fun hasNext(): Boolean = curr != null

    override fun next(): T {
        val value = curr!!.value
        prev = curr
        curr = curr?.next

        return value
    }
}