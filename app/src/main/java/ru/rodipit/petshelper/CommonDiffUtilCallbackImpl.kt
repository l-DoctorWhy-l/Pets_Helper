package ru.rodipit.petshelper

import androidx.recyclerview.widget.DiffUtil

class CommonDiffUtilCallbackImpl<T>(
    private val oldData: List<T>,
    private val newData: List<T>,
    private val areItemsTheSameImpl: (oldItem: T, newItem: T) -> Boolean =
    {oldItem, newItem -> oldItem == newItem },
    private val areContentsTheSameImpl: (oldItem: T, newItem: T) -> Boolean =
        {oldItem, newItem -> oldItem == newItem }
): DiffUtil.Callback() {
    override fun getOldListSize() = oldData.size

    override fun getNewListSize() = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return areItemsTheSameImpl(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return areContentsTheSameImpl(oldItem, newItem)
    }
}