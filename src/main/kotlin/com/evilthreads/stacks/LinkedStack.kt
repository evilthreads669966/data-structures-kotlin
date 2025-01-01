package com.evilthreads.stacks

import com.evilthreads.Node
import com.evilthreads.SortingType
import org.jetbrains.annotations.NotNull

class LinkedStack<T: Comparable<T>> {
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

    fun selectionSort(type: SortingType = SortingType.ASCENDING){
        if(isEmpty())
            return

        val lessOrGreater: Int

        if(type == SortingType.ASCENDING)
            lessOrGreater = -1
        else
            lessOrGreater = 1

        var startingNode = head
        var curr = startingNode!!.next

        while(curr != null){
            while(curr != null){
                if(startingNode!!.value.compareTo(curr.value) == lessOrGreater)
                    swapValues(startingNode, curr)

                curr = curr.next
            }

            startingNode = startingNode!!.next
            curr = startingNode!!.next
        }
    }

    private fun swapValues(@NotNull left: Node<T>, @NotNull right: Node<T>){
        val temp = left.value
        left.value = right.value
        right.value = temp
    }

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