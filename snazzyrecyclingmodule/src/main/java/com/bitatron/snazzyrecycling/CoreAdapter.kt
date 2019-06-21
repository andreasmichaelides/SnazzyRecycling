package com.bitatron.snazzyrecycling

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

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

abstract class CoreViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer,
    KoinComponent {

    abstract fun bind(item: RecyclerItem)
}

abstract class ClickableCoreViewHolder(override val containerView: View) : CoreViewHolder(containerView) {

    protected val itemClicked: PublishSubject<ClickedRecyclerItem> by inject(PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED)

    protected lateinit var item: RecyclerItem

    override fun bind(item: RecyclerItem) {
        this.item = item
    }

}

interface RecyclerItemType

abstract class RecyclerItem(open val itemViewType: RecyclerItemType)

abstract class StringIdRecyclerItem(open val id: String, override val itemViewType: RecyclerItemType) : RecyclerItem(itemViewType)

abstract class IntIdRecyclerItem(open val id: Int, override val itemViewType: RecyclerItemType) : RecyclerItem(itemViewType)