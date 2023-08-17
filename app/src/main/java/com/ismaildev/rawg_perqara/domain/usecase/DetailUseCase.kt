package com.ismaildev.rawg_perqara.domain.usecase

import com.ismaildev.rawg_perqara.domain.feature.local.DeleteFavorite
import com.ismaildev.rawg_perqara.domain.feature.local.GetFavoriteByIdGame
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameDetail
import com.ismaildev.rawg_perqara.domain.feature.local.SaveFavourite

data class DetailUseCase (val getGameDetail: GetGameDetail, val saveFavourite: SaveFavourite,val getFavoriteByIdGame: GetFavoriteByIdGame,val deleteFavorite:DeleteFavorite)