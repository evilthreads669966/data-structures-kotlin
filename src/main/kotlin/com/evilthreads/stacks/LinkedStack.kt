package com.evilthreads.stacks

import com.evilthreads.Node
import org.jetbrains.annotations.NotNull

class LinkedStack<T> {
    private var head: Node<T>? = null
    private var _size = 0
    val size: Int
        get() = _size

    fun push(@NotNull value: T){
        _size++

        val node = Node(value)
        node.next = head
        head = node
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun pop(): T{
        if(isEmpty())
            throw NoSuchElementException()

        _size--

        val value = head!!.value
        head = head!!.next

        return value
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun peek(): T = head?.value ?: throw NoSuchElementException()

    fun isEmpty(): Boolean = head == null

    fun clear(){
        head = null
        _size = 0
    }
}