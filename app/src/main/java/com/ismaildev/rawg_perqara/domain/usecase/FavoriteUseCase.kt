package com.ismaildev.rawg_perqara.domain.usecase

import com.ismaildev.rawg_perqara.domain.feature.local.DeleteFavorite
import com.ismaildev.rawg_perqara.domain.feature.local.GetFavorite
import com.ismaildev.rawg_perqara.domain.feature.local.SaveFavourite

data class  FavoriteUseCase (val getFavourite: GetFavorite, val saveFavourite: SaveFavourite, val deleteFavorite: DeleteFavorite)