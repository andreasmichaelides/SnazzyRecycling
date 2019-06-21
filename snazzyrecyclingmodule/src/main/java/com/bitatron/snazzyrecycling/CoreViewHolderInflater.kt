package com.bitatron.snazzyrecycling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitatron.snazzyrecycling.CoreViewHolder
import com.bitatron.snazzyrecycling.RecyclerItemType

abstract class CoreViewHolderInflater {

    abstract fun map(itemViewType: RecyclerItemType, parent: ViewGroup): CoreViewHolder

    protected fun inflateView(layoutId: Int, parent: ViewGroup): View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

}