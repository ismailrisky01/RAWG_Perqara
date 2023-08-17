package com.ismaildev.rawg_perqara.presentation.event

import com.ismaildev.rawg_perqara.data.model.ModelGame

sealed class ScreenEvent {
    object OnShowGameList:ScreenEvent()
    object OnLoadMore:ScreenEvent()
    data class OnShowGameDetail(val id:Int):ScreenEvent()
    data class OnSearch(val key:String):ScreenEvent()
    data class OnShowDetail(val id:Int):ScreenEvent()
    object OnAddFavourite:ScreenEvent()
    object OnDeleteFavourite:ScreenEvent()
    object OnShowFavorite:ScreenEvent()

}