package com.evilthreads.queues

import com.evilthreads.SortingType
import com.evilthreads.iterators.CircularArrayIterator
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*
import kotlin.NoSuchElementException

class CircularArrayQueue<T: Comparable<T>>(initialCapacity: Int): Iterable<T>{
    private var array: Array<T?> = arrayOfNulls<Comparable<T>>(initialCapacity) as Array<T?>
    private var front = 0
    private var rear = 0
    private var _size = 0
    val size: Int
        get() = _size

    fun enqueue(@NotNull value: T){
        if(isFull())
            resize()

        array[rear++] = value
        rear = rear % array.size
        _size++
    }

    @Nullable
    fun dequeue(): T?{
        val value = array[front++]
        front = front % array.size
        _size--

        return value
    }

    @Nullable
    fun peek(): T? = array[front]

    @Nullable
    fun last(): T?{
        if(isEmpty())
            return null

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
        if(front < rear){
            for(i in front until rear){
                if(array[i] == value)
                    return true
            }
        }else{
            for(i in front  until array.size){
                if(array[i] == value)
                    return true
            }
            for(i in 0 until rear){
                if(array[i] == value)
                    return true
            }
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
        println("resizing")
        var idx = 0
        if(front < rear){
            for(i in front  until rear){
                arr[idx] = array[i]
                idx++
            }
        }else{
            for(i in front until array.size){
                arr[idx] = array[i]
                idx++
            }
            for(i in 0 until rear){
                arr[idx] = array[i]
                idx++
            }
        }
        front = 0
        rear = _size
        array = arr
    }

    override fun iterator(): MutableIterator<T> {
        return CircularArrayIterator(array, front, rear)
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(other == null) return false
        if(other !is CircularArrayQueue<*>) return false
        if(size != other.size) return false

        val iterator = iterator()
        val otherIterator = iterator()

        while(iterator.hasNext()){
            if(iterator.next() != otherIterator.next())
                return false
        }

        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()

        if(isEmpty())
            return sb.toString()

        sb.append("[")

        if(front < rear){
            for(index in front until rear){
                if(index == rear - 1)
                    sb.append(array[index])
                else
                    sb.append("${array[index]} ")
            }
        }else{
            for(index in front until array.size){
                sb.append("${array[index]} ")
            }
            for(index in 0 until rear){
                if(index == rear - 1)
                    sb.append(array[index])
                else
                    sb.append("${array[index]} ")
            }
        }

        sb.append("]")

        return sb.toString()
    }

    fun selectionSort(sortingType: SortingType = SortingType.ASCENDING){
        if(isEmpty())
            return

        val lessOrGreater: Int

        if(sortingType == SortingType.ASCENDING)
            lessOrGreater = 1
        else
            lessOrGreater = -1

        var i = front

        repeat(_size){
            if(i < rear){
                for(j in i + 1 until rear){
                    if(array[i]!!.compareTo(array[j]!!) == lessOrGreater)
                        swapValues(i, j)
                }
            }else if(i == array.size - 1){
                for(j in 0 until rear){
                    if(array[i]!!.compareTo(array[j]!!) == lessOrGreater)
                        swapValues(i, j)
                }
            }else if(i < array.size - 1){
                for(j in i + 1 until array.size){
                    if(array[i]!!.compareTo(array[j]!!) == lessOrGreater)
                        swapValues(i, j)
                }
                for(j in 0 until rear){
                    if(array[i]!!.compareTo(array[j]!!) == lessOrGreater)
                        swapValues(i, j)
                }
            }

            if(i == array.size - 1)
                i = 0
            else
                i++
        }
    }

    fun bubbleSort(sortingType: SortingType = SortingType.ASCENDING){
        if(isEmpty())
            return

        val lessOrGreater: Int

        if(sortingType == SortingType.ASCENDING)
            lessOrGreater = 1
        else
            lessOrGreater = -1

        repeat(_size){ i ->
            if(front < rear) {
                for (j in front until rear - i - 1) {
                    if (array[j]!!.compareTo(array[j + 1]!!) == lessOrGreater)
                        swapValues(j, j + 1)
                }
            }else{
                if(rear - 1 - i > 0){
                    for (j in front until array.size) {
                        if(j == array.size - 1)
                            swapValues(j, 0)
                        else{
                            if(array[j]!!.compareTo(array[j + 1]!!) == lessOrGreater)
                                swapValues(j, j + 1)
                        }
                    }
                    for (j in 0 until rear - 1 - i) {
                        if (array[j]!!.compareTo(array[j + 1]!!) == lessOrGreater)
                            swapValues(j, j + 1)
                    }
                }else if(rear - 1 - i == 0){
                    for (j in front until array.size) {
                        if(j == array.size - 1){
                            if (array[j]!!.compareTo(array[0]!!) == lessOrGreater)
                                swapValues(j, 0)
                        }else{
                            if (array[j]!!.compareTo(array[j + 1]!!) == lessOrGreater)
                                swapValues(j, j + 1)
                        }
                    }
                }else{
                    val i = i - rear - 1
                    for (j in front until array.size - i - 2) {
                        if (array[j]!!.compareTo(array[j + 1]!!) == lessOrGreater)
                            swapValues(j, j + 1)
                    }
                }
            }
        }
    }

    fun remove(@NotNull value: T): Boolean{
        if(isEmpty())
            return false

        var i = front
        if(front < rear){
            while(i < rear){
                if(array[i] == value){
                    removeAndShiftLeft(i - front)
                    _size--
                    return true
                }else{
                    i++
                }
            }
        }else if(front > rear){
            while(i < rear){
                if(array[i] == value){
                    removeAndShiftLeft(i - front)
                    _size--
                    return true
                }else{
                    i++
                }
            }
            i = 0
            while(i < rear){
                if(array[i] == value){
                    removeAndShiftLeft(array.size - 1 - front + i)
                    _size--
                    return true
                }else{
                    i++
                }
            }
        }

        _size--
        return false
    }


    @Throws(IndexOutOfBoundsException::class)
    fun removeAt(index: Int): Boolean{
        if(isEmpty())
            return false
        removeAndShiftLeft(index)
        _size--
        return true
    }

    fun indexOf(@NotNull value: T): Int{
        if(isEmpty())
            return -1

        var idx = 0

        if(front < rear){
            for(i in front until rear){
                if(array[i] == value){
                    return idx
                }
                idx++
            }
        }else{
            for(i in front until array.size){
                if(array[i] == value)
                    return idx
                idx++
            }
            for(i in 0 until rear){
                if(array[i] == value)
                    return idx
                idx++
            }
        }

        return -1
    }


    @Throws(IndexOutOfBoundsException::class)
    private fun removeAndShiftLeft(index: Int){
        val index = front + index

        if(front < rear){
            for(i in index until rear - 1){
                array[i] = array[i + 1]
            }
        }else if(index < array.size){
            for(i in index until array.size){
                if(i == array.size - 1){
                    array[i] = array[0]
                }else{
                    array[i] = array[i + 1]
                }
            }
            for(i in 0 until rear - 1){
                array[i] = array[i + 1]
            }
        }else if(index > array.size - 1){
            for(i in index - array.size until rear - 1){
                array[i] = array[i + 1]
            }
        }

        if(rear - 1 < 0)
            rear = array.size - 1
        else
            rear = rear - 1
    }

    private fun swapValues(leftIndex: Int, rightIndex: Int){
        val temp = array[leftIndex]
        array[leftIndex] = array[rightIndex]
        array[rightIndex] = temp
    }
}