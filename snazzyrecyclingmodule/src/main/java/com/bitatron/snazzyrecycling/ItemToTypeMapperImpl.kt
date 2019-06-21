package com.bitatron.snazzyrecycling

import com.bitatron.snazzyrecycling.ItemToTypeMapper
import com.bitatron.snazzyrecycling.RecyclerItem
import com.bitatron.snazzyrecycling.RecyclerItemType

class ItemToTypeMapperImpl(private val list: List<RecyclerItemType>) : ItemToTypeMapper {

    override fun map(baseItem: RecyclerItem): Int {
        return list.indexOf(baseItem.itemViewType)
    }

    override fun map(type: Int): RecyclerItemType {
        return list[type]
    }

}