package com.evilthreads.lists

import com.evilthreads.SortingType
import org.jetbrains.annotations.NotNull

class LinearArray<T: Comparable<T>>(initialSize: Int = 1000) : MutableCollection<T> {
    private var array = arrayOfNulls<Comparable<T>>(initialSize) as Array<T?>
    private var _size = 0
    override val size: Int
        get() = _size

    override fun contains(value: T): Boolean {
        for(index in 0 until _size){
            if(array[index] == value)
                return true
        }

        return false
    }

    override fun add(@NotNull value: T): Boolean {
        if(isFull())
            resize()

        array[_size++] = value
        return true
    }

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int): T{
        if(index >= _size || index < 0)
            throw IndexOutOfBoundsException()

        return array[index]!!
    }

    @Throws(IndexOutOfBoundsException::class)
    fun set(index: Int, @NotNull value: T) {
        if(index >= _size || index < 0)
            throw IndexOutOfBoundsException()

        array[index] = value
    }

    override fun addAll(@NotNull values: Collection<T>): Boolean {
        values.forEach { value ->
            if(isFull())
                resize()
            array[_size++] = value
        }

        return true
    }

    override fun clear() {
        array = arrayOfNulls<Comparable<T>>(array.size) as Array<T?>
        _size = 0
    }

    override fun isEmpty(): Boolean = _size == 0

    override fun iterator(): MutableIterator<T> {
        return object : MutableIterator<T>{
            private var index = 0

            override fun hasNext(): Boolean = index < _size

            override fun next(): T {
                return array[index++]!!
            }

            @Throws(IndexOutOfBoundsException::class)
            override fun remove() {
                removeAndShiftLeft(array[--index]!!)
            }
        }
    }

    override fun retainAll(@NotNull values: Collection<T>): Boolean {
        var index = 0

        while(index < _size){
            if(!values.contains(array[index]))
                removeByIndexAndShiftLeft(index)
            else
                index++
        }

        return true
    }

    override fun removeAll(@NotNull values: Collection<T>): Boolean {
        return removeAllAndShiftLeft(values)
    }

    override fun remove(@NotNull value: T): Boolean {
        return removeAndShiftLeft(value)
    }

    fun removeAt(index: Int): T = removeByIndexAndShiftLeft(index)

    override fun containsAll(@NotNull values: Collection<T>): Boolean {
        values.forEach { value ->
            if(!contains(value))
                return false
        }

        return true
    }

    fun indexOf(@NotNull value: T): Int = array.indexOf(value)

    fun selectionSort(sortingType: SortingType = SortingType.ASCENDING){
        if(isEmpty())
            return

        val lessOrGreater: Int

        if(sortingType == SortingType.ASCENDING)
            lessOrGreater = 1
        else
            lessOrGreater = -1

        repeat(_size){ i ->
            for(j in i + 1 until _size){
                if(array[i]!!.compareTo(array[j]!!) == lessOrGreater){
                    val temp = array[i]
                    array[i] = array[j]
                    array[j] = temp
                }
            }
        }
    }

    fun bubbleSort(sortingType: SortingType = SortingType.ASCENDING){
        if(isEmpty())
            return

        val lessOrGreater: Int

        if(sortingType == SortingType.ASCENDING)
            lessOrGreater = -1
        else
            lessOrGreater = 1

        repeat(_size){ i ->
            for(j in 0 until _size - i - 1){
                if(array[j]!!.compareTo(array[j + 1]!!) != lessOrGreater){
                    val temp = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = temp
                }
            }
        }
    }

    private fun isFull():Boolean = _size == array.size

    private fun resize(){
        val arr = arrayOfNulls<Comparable<T>>(array.size * 2) as Array<T?>
        array.forEachIndexed { index, value -> arr[index] = value }
        array = arr
    }

    private fun removeAllAndShiftLeft(@NotNull values: Collection<T>): Boolean{
        var removed = false
        var index = 0

        while(index < _size)
            if(values.contains(array[index])){
                for(i in index until _size - 1){
                    array[i] = array[i + 1]
                    if(index == _size - 2)
                        array[_size - 1] = null
                }

                removed = true
                _size--
            }else{
                index++
            }

        return removed
    }

    private fun removeAndShiftLeft(@NotNull value: T): Boolean{
        for(index in 0 until _size) {
            if (value == array[index]) {
                for (i in index until _size - 1) {
                    array[i] = array[i + 1]
                    if (i == _size - 2)
                        array[_size - 1] = null
                }
                _size--

                return true
            }
        }
        return false
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun removeByIndexAndShiftLeft(index: Int): T{
        if(index >= _size || index < 0)
            throw IndexOutOfBoundsException()

        val value = array[index]!!

        for(i in index until _size - 1){
            array[i] = array[i + 1]
            if(i == _size - 2)
                array[_size - 1] = null
        }

        _size--

        return value
    }

    override fun toString(): String {
        val sb = StringBuilder()

        if(isEmpty())
            return sb.toString()

        sb.append("[")

        for(index in 0 until _size){
            if(index == _size - 1)
                sb.append(array[index])
            else
                sb.append(array[index]).append(" ")
        }

        sb.append("]")

        return sb.toString()
    }

    override fun equals(other: Any?): Boolean {
        if(this === other)
            return true
        if(other == null)
            return false
        if(other !is LinearArray<*>)
            return false
        if(_size != other.size)
            return false

        for(index in 0 until _size){
            if(array[index] != other[index])
                return false
        }

        return true
    }
}