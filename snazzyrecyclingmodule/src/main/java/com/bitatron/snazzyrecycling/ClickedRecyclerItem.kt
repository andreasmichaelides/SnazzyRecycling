package com.bitatron.snazzyrecycling

interface ClickAction

data class ClickedRecyclerItem(
        val clickAction: ClickAction,
        val recyclerItem: RecyclerItem
)