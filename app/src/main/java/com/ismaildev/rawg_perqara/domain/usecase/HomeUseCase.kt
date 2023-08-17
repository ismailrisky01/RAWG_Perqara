package com.ismaildev.rawg_perqara.domain.usecase

import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameDetail
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameList
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameListLoadMore
import com.ismaildev.rawg_perqara.domain.feature.remote.GetSearch

data class HomeUseCase (val getGameList: GetGameList, val getGameDetail: GetGameDetail, val getSearch: GetSearch,val getGameListLoadMore: GetGameListLoadMore)