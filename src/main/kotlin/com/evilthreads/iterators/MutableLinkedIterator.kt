package com.evilthreads.iterators

import com.evilthreads.Node
import org.jetbrains.annotations.Nullable

class MutableLinkedIterator<T: Comparable<T>>(@Nullable private var curr: Node<T>?): MutableIterator<T> {
    private var prev: Node<T>? = null

    override fun hasNext(): Boolean  = curr != null

    override fun next(): T {
        val value = curr!!.value
        prev = curr
        curr = curr?.next

        return value
    }

    @Throws(NotImplementedError::class)
    override fun remove() = throw NotImplementedError()
}