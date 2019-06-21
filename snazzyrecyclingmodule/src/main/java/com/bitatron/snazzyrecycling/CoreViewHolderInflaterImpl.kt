package com.bitatron.snazzyrecycling

import android.view.ViewGroup


class CoreViewHolderInflaterImpl : CoreViewHolderInflater() {

    override fun map(itemViewType: RecyclerItemType, parent: ViewGroup): CoreViewHolder {
        return recyclerViewHolderMap[itemViewType]?.let {
            val view = inflateView(it.first, parent)
            it.second.invoke(view)
        } ?: throw Exception("No ViewHolder found")

    }
}