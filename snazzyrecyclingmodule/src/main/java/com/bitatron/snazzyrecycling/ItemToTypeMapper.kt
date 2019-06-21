package com.bitatron.snazzyrecycling

import com.bitatron.snazzyrecycling.RecyclerItem
import com.bitatron.snazzyrecycling.RecyclerItemType


interface ItemToTypeMapper {

    fun map(baseItem: RecyclerItem): Int
    
    fun map(type: Int): RecyclerItemType

}