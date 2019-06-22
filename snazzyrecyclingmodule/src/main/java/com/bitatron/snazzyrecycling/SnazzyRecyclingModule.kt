package com.bitatron.snazzyrecycling

import android.view.View
import io.reactivex.subjects.PublishSubject
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED = "PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED"

val recyclerItemTypeList = ArrayList<RecyclerItemType>()
val recyclerViewHolderMap: HashMap<RecyclerItemType, Pair<Int, (itemView: View) -> CoreViewHolder>> = HashMap()

fun addViewHolder(type: RecyclerItemType, layout: Int, create: (itemView: View) -> CoreViewHolder) {
    recyclerItemTypeList.add(type)
    recyclerViewHolderMap[type] = Pair(layout, create)
}

val snazzyRecyclingModule = module {
    factory { CoreAdapter(get(), get()) }
    factory { AsyncCoreAdapter(get(), get()) }
    single<PublishSubject<ClickedRecyclerItem>>(named(PUBLISH_SUBJECT_RECYCLER_ITEM_CLICKED)) { PublishSubject.create() }
    single<CoreViewHolderInflater> { CoreViewHolderInflaterImpl() }
    factory<ItemToTypeMapper> { ItemToTypeMapperImpl(recyclerItemTypeList) }
}