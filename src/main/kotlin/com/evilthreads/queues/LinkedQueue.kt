package com.evilthreads.queues

import com.evilthreads.Node
import com.evilthreads.iterators.LinkedIterator
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class LinkedQueue<T: Comparable<T>>: Iterable<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var _size  = 0
    val size: Int
        get() = _size

    fun enqueue(@NotNull value: T){
        val node = Node(value)
        if(isEmpty()){
            node.next = head
            head = node
            tail = head
        }else{
            tail!!.next = node
            tail = node
        }

        _size++
    }

    @Nullable
    fun dequeue(): T?{
        val value = head?.value ?: return null
        head = head!!.next

        _size--

        return value
    }

    @Nullable
    fun peek(): T? = head?.value


    fun contains(@NotNull value: T): Boolean{
        if(isEmpty())
            return false

        var curr = head

        while(curr != null){
            if(curr.value == value)
                return true

            curr = curr.next
        }

        return false
    }

    fun containsAll(@NotNull values: Collection<T>): Boolean{
        if(isEmpty() || values.isEmpty())
            return false

        values.forEach { value ->
            if(!contains(value))
                return false
        }

        return true
    }

    fun clear(){
        head = null
        tail = null
        _size = 0
    }

    fun isEmpty() : Boolean = head == null

    override fun iterator(): Iterator<T> = LinkedIterator<T>(head)

    override fun toString(): String {
        val sb = StringBuilder("")

        if(isEmpty())
            return sb.toString()

        sb.append("[")

        var curr = head

        while(curr != null){
            if(curr.next == null)
                sb.append(curr.value)
            else
                sb.append("${curr.value} ")

            curr = curr.next
        }

        sb.append("]")

        return sb.toString()
    }
}