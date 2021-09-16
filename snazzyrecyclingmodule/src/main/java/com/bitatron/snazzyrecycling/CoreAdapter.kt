package com.bitatron.snazzyrecycling

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import io.reactivex.subjects.PublishSubject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

open class CoreAdapter(
    private val viewHolderInflater: CoreViewHolderInflater,
    private val itemToTypeMapper: ItemToTypeMapper
) : RecyclerView.Adapter<CoreViewHolder>() {

    protected var items = emptyList<RecyclerItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoreViewHolder =
            viewHolderInflater.map(itemToTypeMapper.map(viewType), parent)

    override fun getItemViewType(position: Int): Int = itemToTypeMapper.map(items[position])

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CoreViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        val baseItem = items[position]
        return when (baseItem) {
            is StringIdRecyclerItem -> baseItem.id.toLong()
            is IntIdRecyclerItem -> baseItem.id.hashCode().toLong()
            else -> super.getItemId(position)
        }
    }

    open fun setData(newBaseItems: List<RecyclerItem>) {
        val diffResult = DiffUtil.calculateDiff(RecyclerItemDiffUtils(this.items, newBaseItems))

        this.items = newBaseItems

        diffResult.dispatchUpdatesTo(this)
    }
}

@KoinApiExtension
abstract class CoreViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root),
    KoinComponent {

    abstract fun bind(item: RecyclerItem)
}

@KoinApiExtension
abstract class ClickableCoreViewHolder(binding: ViewBinding) : CoreViewHolder(binding) {

    protected val itemClicked: PublishSubject<ClickedRecyclerItem> by inject(named(PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED))

    protected lateinit var item: RecyclerItem

    override fun bind(item: RecyclerItem) {
        this.item = item
    }

}

interface RecyclerItemType

abstract class RecyclerItem(open val itemViewType: RecyclerItemType)

abstract class StringIdRecyclerItem(open val id: String, override val itemViewType: RecyclerItemType) : RecyclerItem(itemViewType)

abstract class IntIdRecyclerItem(open val id: Int, override val itemViewType: RecyclerItemType) : RecyclerItem(itemViewType)