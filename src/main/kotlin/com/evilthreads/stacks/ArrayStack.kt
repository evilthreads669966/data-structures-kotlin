package com.evilthreads.stacks

import com.evilthreads.iterators.ArrayStackIterator
import com.evilthreads.SortingType
import org.jetbrains.annotations.NotNull

class ArrayStack<T: Comparable<T>>(private val initialCapacity: Int = 1000): Collection<T> {
    private var array: Array<T?> = arrayOfNulls<Comparable<T>>(initialCapacity) as Array<T?>
    private var top = -1
    override val size: Int
        get() = top + 1

    fun push(@NotNull value: T){
        if(isFull())
            resize()

        array[++top] = value
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun pop():T{
        if(isEmpty())
            throw NoSuchElementException()

        val value = array[top--]
        array[top + 1] = null

        return value!!
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun peek():T{
        if(isEmpty())
            throw NoSuchElementException()

        return array[top]!!
    }

    fun clear(){
        array = arrayOfNulls<Comparable<T>>(initialCapacity) as Array<T?>
        top = -1
    }

    private fun isFull(): Boolean = top == array.size - 1

    private fun resize(){
        val arr = arrayOfNulls<Comparable<T>>(array.size + initialCapacity) as Array<T?>
        array.forEachIndexed { index, value -> arr[index] = value }
        array = arr
    }

    override fun contains(value: T): Boolean = array.contains(value)

    override fun containsAll(values: Collection<T>): Boolean{
        if(isEmpty() || values.isEmpty())
            return false

        return values.all { value -> contains(value) }
    }

    override fun isEmpty(): Boolean = top == -1

    override fun iterator(): Iterator<T> = ArrayStackIterator<T>(array, top)

    fun selectionSort(sortingType: SortingType = SortingType.ASCENDING){
        if(isEmpty())
            return

        val lessOrGreater: Int

        if(sortingType == SortingType.ASCENDING)
            lessOrGreater = 1
        else
            lessOrGreater = -1

        repeat(top){ i ->
            for(j in (i + 1)..top){
                if(array[i]!!.compareTo(array[j]!!) == lessOrGreater){
                    val temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    override fun toString(): String {
        val sb = StringBuilder("")

        if(isEmpty())
            return sb.toString()

        sb.append("[")

        for(i in 0 until top + 1){
            if(i == top)
                sb.append(array[i])
            else
                sb.append("${array[i]} ")
        }

        sb.append("]")

        return sb.toString()
    }
}