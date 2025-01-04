package com.evilthreads.arrays

import org.jetbrains.annotations.NotNull

class LinearArray<T: Comparable<T>>(initialSize: Int) : MutableCollection<T> {
    private var array = arrayOfNulls<Comparable<T>>(initialSize) as Array<T?>
    private var _size = 0
    override val size: Int
        get() = _size

    override fun contains(value: T): Boolean {
        array.forEach { v ->
            if(v == value)
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

    override fun addAll(values: Collection<T>): Boolean {
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

            override fun remove() {
                removeAndShiftLeft(array[index - 1]!!)
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

    override fun containsAll(@NotNull values: Collection<T>): Boolean {
        values.forEach { value ->
            if(!contains(value))
                return false
        }

        return true
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
                }

                removed = true
                _size--
            }else{
                index++
            }

        return removed
    }

    private fun removeAndShiftLeft(@NotNull value: T): Boolean{
        var index = 0

        while(index < _size)
            if(value == array[index]){
                for(i in index until _size - 1){
                    array[i] = array[i + 1]
                }
                _size--

                return true
            }else {
                index++
            }

        return false
    }

    @Throws(IndexOutOfBoundsException::class)
    private fun removeByIndexAndShiftLeft(index: Int): Boolean{
        if(index >= _size || index < 0)
            throw IndexOutOfBoundsException()

        for(i in index until _size - 1){
            array[i] = array[i + 1]
        }

        _size--

        return true
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
        if(other !is Array<*>)
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