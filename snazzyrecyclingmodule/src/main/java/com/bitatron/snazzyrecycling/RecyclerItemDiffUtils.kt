package com.bitatron.snazzyrecycling

import androidx.recyclerview.widget.DiffUtil
import com.bitatron.snazzyrecycling.IntIdRecyclerItem
import com.bitatron.snazzyrecycling.RecyclerItem
import com.bitatron.snazzyrecycling.StringIdRecyclerItem

open class RecyclerItemDiffUtils(private val oldBaseItems: List<RecyclerItem>,
                                 private val newBaseItems: List<RecyclerItem>) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldBaseItems[oldItemPosition] == newBaseItems[newItemPosition]

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldBaseItems[oldItemPosition]
        val newItem = newBaseItems[newItemPosition]

        return when (oldItem) {
            is StringIdRecyclerItem -> when (newItem) {
                is StringIdRecyclerItem -> oldItem.id == newItem.id
                else -> false
            }
            is IntIdRecyclerItem -> when (newItem) {
                is IntIdRecyclerItem -> oldItem.id == newItem.id
                else -> false
            }
            else -> false
        }
    }

    override fun getOldListSize(): Int = oldBaseItems.size

    override fun getNewListSize(): Int = newBaseItems.size
}