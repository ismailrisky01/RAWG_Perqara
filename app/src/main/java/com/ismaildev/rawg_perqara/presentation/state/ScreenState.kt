package com.ismaildev.rawg_perqara.presentation.state

import com.ismaildev.rawg_perqara.data.model.DetailResponse
import com.ismaildev.rawg_perqara.data.model.GameItem
import com.ismaildev.rawg_perqara.data.model.ModelGame

sealed class ScreenState {
    data class OnShowGame(val data:List<GameItem>): ScreenState()
    data class OnShowGameLoadMore(val data:List<GameItem>): ScreenState()
    data class OnShowDetailGame(val data: DetailResponse): ScreenState()
    data class OnShowFavorite(val data:List<ModelGame>):ScreenState()
    data class OnChangeFavoriteIcon(val isChange:Boolean):ScreenState()
    object OnShowLoading : ScreenState()
    object OnHideLoading : ScreenState()
    data class OnShowError(val message:String):ScreenState()
}