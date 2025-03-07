package com.evilthreads.trees

import com.evilthreads.TreeNode
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class BinarySearchTree<T: Comparable<T>> {
    @Nullable
    private var root: TreeNode<T>? = null
    private var _size: Int = 0
    val size: Int
        get() = _size


    fun insert(@NotNull value: T){
        root = insert(root, value)
        _size++
    }

    @NotNull
    private fun insert(@NotNull node: TreeNode<T>?, @NotNull value: T): TreeNode<T>{
        if(node == null)
            return TreeNode<T>(value)

        if(value < node.value)
            node.left = insert(node.left, value)
        else
            node.right = insert(node.right, value)

        return node
    }

    fun contains(@NotNull value: T): Boolean = contains(root, value)

    private fun contains(@Nullable node: TreeNode<T>?, @NotNull value: T): Boolean{
        if(node == null)
            return false

        if(node.value == value)
            return true

        if(value < node.value)
            return contains(node.left, value)
        else
            return contains(node.right, value)
    }

    fun containsAll(@NotNull values: Collection<T>): Boolean = containsAll(root, values) == values.size

    private fun containsAll(@Nullable node: TreeNode<T>?, @NotNull values: Collection<T>): Int{
        var count = 0

        if(node == null || count == values.size)
            return count

        if(node.left != null)
            count +=  containsAll(node.left, values)

        if(node.right != null)
            count +=  containsAll(node.right, values)

        if(values.contains(node.value))
            ++count

        return count
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun max(): T{
        if(root == null)
            throw NoSuchElementException()

        return max(root!!)
    }

    @NotNull
    private fun max(@Nullable node: TreeNode<T>): T{
        if(node.right == null)
            return node.value

        return max(node.right!!)
    }

    @NotNull
    @Throws(NoSuchElementException::class)
    fun min(): T{
        if(root == null)
            throw NoSuchElementException()

        return min(root!!)
    }

    @NotNull
    private fun min(@NotNull node: TreeNode<T>): T{
        if(node.left == null)
            return node.value

        return min(node.left!!)
    }

    fun remove(@NotNull value: T){
        root = remove(root,value)
    }

    @Nullable
    private fun remove(@Nullable node: TreeNode<T>?, @NotNull value: T, valueRemoved: Boolean = false): TreeNode<T>?{
        if(node == null)
            return null

        if(value < node.value)
            node.left = remove(node.left, value, valueRemoved)
        else if(value > node.value)
            node.right = remove(node.right, value, valueRemoved)
        else{
            if(!valueRemoved)
                _size--

            if(node.left == null && node.right == null)
                return null
            else if(node.left == null)
                return node.right
            else if(node.right == null)
                return node.left
            else{
                node.value = min(node.right!!)
                node.right = remove(node.right, node.value, true)
            }
        }

        return node
    }

    @Throws(IndexOutOfBoundsException::class)
    fun removeAt(index: Int){
        removeAt(root, index)
    }

    @Nullable
    @Throws(IndexOutOfBoundsException::class)
    private fun removeAt(@Nullable node: TreeNode<T>?, index: Int, count: Int = 0): TreeNode<T>?{
        if(index < 0 || index >= _size)
            throw IndexOutOfBoundsException()

        if(node == null)
            return null

        var count = count

        if(root!!.right == node && root!!.left != null)
            count++

        if(count == index){
            return remove(node, node.value)
        }else if(count < index){
            if(node.left != null)
                node.left = removeAt(node.left, index, ++count)

            if(node.right != null)
                node.right = removeAt(node.right, index, ++count)
        }

        return node
    }

    fun removeAll(@NotNull values: Collection<T>): Boolean{
        val oldSize = _size
        removeAll(root, values)

        return _size < oldSize
    }

    private fun removeAll(@Nullable node: TreeNode<T>?, @NotNull values: Collection<T>): TreeNode<T>?{
        if(node == null)
            return null

        if(node.left != null){
            node.left = removeAll(node.left, values)
        }

        if(node.right != null){
            node.right = removeAll(node.right, values)
        }

        if(values.contains(node.value)){
            return remove(node, node.value)
        }

        return node
    }

    fun indexOf(@NotNull value: T): Int{
        if(contains(value))
            return indexOf(root, value)

        return -1
    }

    private fun indexOf(@NotNull node: TreeNode<T>?, @NotNull value: T): Int{
        if(node == null){
            return -1
        }

        var index = 0

        if(value < node.value)
            index += indexOf(node.left, value) + 1
        else if(value > node.value){
            index += indexOf(node.left, value) + 1
            index += indexOf(node.right, value) + 1
        }

        return index
    }

    fun retainAll(@NotNull values: Collection<T>){
        retainAll(root, values)
    }

    @Nullable
    private fun retainAll(@Nullable node: TreeNode<T>?, @NotNull values: Collection<T>): TreeNode<T>?{
        if(node == null)
            return null

        node.left = retainAll(node.left, values)
        node.right = retainAll(node.right, values)

        if(!values.contains(node.value)){
            return remove(node, node.value)
        }

        return node
    }

    @Throws(IndexOutOfBoundsException::class)
    fun set(index: Int, @NotNull value: T){
        removeAt(index)
        insert(value)
    }

    @NotNull
    @Throws(IndexOutOfBoundsException::class)
    operator fun get(index: Int): T{
        if(index < 0 || index >= _size)
            throw IndexOutOfBoundsException()

        return get(root, index)!!
    }

    @Nullable
    @Throws(IndexOutOfBoundsException::class)
    private fun get(@Nullable node: TreeNode<T>?, index: Int, count: Int = 0, @NotNull wrapper: ValueWrapper<T> = ValueWrapper<T>()): T?{
        if(node == null)
            throw IndexOutOfBoundsException()

        var count = count

        if(root!!.right == node && root!!.left != null)
            count++

        if(count < index){
            if(node.left != null)
                get(node.left, index, ++count, wrapper)
            if(node.right != null)
                get(node.right, index, ++count, wrapper)
        }else if(count == index){
            wrapper.value = node.value
        }

        return wrapper.value
    }

    @NotNull
    fun inOrderTraversal(): List<T> = inOrderTraversal(root)

    @NotNull
    private fun inOrderTraversal(@Nullable node: TreeNode<T>?, @NotNull list: ArrayList<T> = ArrayList<T>()): List<T>{
        if (node != null) {
            if(node.left != null)
                inOrderTraversal(node.left!!, list)

            list.add(node.value)

            if(node.right != null)
                inOrderTraversal(node.right!!, list)
        }

        return list
    }

    @NotNull
    fun preOrderTraversal(): List<T> = preOrderTraversal(root)

    private fun preOrderTraversal(@Nullable node: TreeNode<T>?, @NotNull values: ArrayList<T> = ArrayList<T>()): List<T>{
        if(node != null)
            values.add(node.value)

        if(node?.left != null)
            preOrderTraversal(node.left, values)

        if(node?.right != null)
            preOrderTraversal(node.right, values)

        return values
    }

    @NotNull
    fun postOrderTraversal(): List<T> = postOrderTraversal(root)

    private fun postOrderTraversal(@Nullable node: TreeNode<T>?, @NotNull values: ArrayList<T> = ArrayList<T>()): List<T>{
        if(node?.left != null)
            postOrderTraversal(node.left, values)

        if(node?.right != null)
            postOrderTraversal(node.right, values)

        if(node != null)
            values.add(node.value)

        return values
    }
    
    fun clear(){
        root = null
        _size = 0
    }

    fun isEmpty(): Boolean = root == null
}

data class ValueWrapper<T>(@Nullable var value: T? = null)