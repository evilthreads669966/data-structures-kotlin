package com.evilthreads

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class TreeNode<T: Comparable<T>>(@NotNull var value: T, @Nullable var left: TreeNode<T>? = null, @Nullable var right: TreeNode<T>? = null)