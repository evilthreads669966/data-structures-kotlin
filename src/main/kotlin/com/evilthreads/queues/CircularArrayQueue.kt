package com.evilthreads.queues

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class CircularArrayQueue<T: Comparable<T>>(initialCapacity: Int) {
    private var array: Array<T?> = arrayOfNulls<Comparable<T>>(initialCapacity) as Array<T?>
    private var front = 0
    private var rear = 0
    private var _size = 0
    val size: Int
        get() = _size

    fun enqueue(@NotNull value: T){
        if(isFull())
            resize()

        array[rear] = value
        rear = (rear + 1) % array.size
        _size++
    }

    @Nullable
    fun dequeue(): T?{
        val value = array[front]
        front = (front + 1) % array.size
        _size--

        return value
    }

    @Nullable
    fun peek(): T?{
        return array[front]
    }

    @Nullable
    fun last(): T?{
        if(isEmpty())
            return null
        println(rear)
        if(rear == 0)
            return array[rear]

        return array[rear - 1]
    }

    fun clear(){
        front = 0
        rear = 0
        array.fill(null)
        _size = 0
    }

    fun isEmpty(): Boolean = _size == 0

    fun contains(@NotNull value: T): Boolean{
        if(isEmpty())
            return false

        for(i in 0..rear + 1){
            if(array[i] == value)
                return true
        }

        return false
    }

    fun containsAll(@NotNull values: Collection<T>): Boolean{
        if(isEmpty())
            return false

        values.forEach { value ->
            if(!contains(value))
                return false
        }

        return true
    }

    private fun isFull(): Boolean = _size == array.size - 1

    private fun resize(){
        val arr: Array<T?> = arrayOfNulls<Comparable<T>>(array.size * 2) as Array<T?>

        for(i in front  until rear){
            arr[i] = array[i]
        }

        array = arr
    }
}