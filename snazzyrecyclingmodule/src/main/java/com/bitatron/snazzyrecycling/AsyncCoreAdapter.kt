package com.bitatron.snazzyrecycling

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

val diffUtil = object: DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean {
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

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: RecyclerItem, newItem: RecyclerItem): Boolean =
            oldItem == newItem
}

open class AsyncCoreAdapter(
    private val viewHolderInflater: CoreViewHolderInflater,
    private val itemToTypeMapper: ItemToTypeMapper
) : RecyclerView.Adapter<CoreViewHolder>() {

    protected val mDiffer = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoreViewHolder =
            viewHolderInflater.map(itemToTypeMapper.map(viewType), parent)

    override fun getItemViewType(position: Int): Int = itemToTypeMapper.map(mDiffer.currentList[position])

    override fun getItemCount(): Int = mDiffer.currentList.size

    override fun onBindViewHolder(holder: CoreViewHolder, position: Int) {
        holder.bind(mDiffer.currentList[position])
    }

    override fun getItemId(position: Int): Long {
        val baseItem = mDiffer.currentList[position]
        return when (baseItem) {
            is StringIdRecyclerItem -> baseItem.id.toLong()
            is IntIdRecyclerItem -> baseItem.id.hashCode().toLong()
            else -> super.getItemId(position)
        }
    }

    open fun setData(newBaseItems: List<RecyclerItem>) {
        mDiffer.submitList(newBaseItems)
    }
}