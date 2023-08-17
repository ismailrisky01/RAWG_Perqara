package com.ismaildev.rawg_perqara.di


import androidx.room.Room
import com.ismaildev.rawg_perqara.data.local.RoomDatabase
import com.ismaildev.rawg_perqara.data.remote.ApiService
import com.ismaildev.rawg_perqara.data.repository.LocalRepositoryImpl
import com.ismaildev.rawg_perqara.data.repository.RemoteRepositoryImpl
import com.ismaildev.rawg_perqara.domain.feature.local.DeleteFavorite
import com.ismaildev.rawg_perqara.domain.feature.local.GetFavorite
import com.ismaildev.rawg_perqara.domain.feature.local.GetFavoriteByIdGame
import com.ismaildev.rawg_perqara.domain.feature.local.SaveFavourite
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameDetail
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameList
import com.ismaildev.rawg_perqara.domain.feature.remote.GetGameListLoadMore
import com.ismaildev.rawg_perqara.domain.feature.remote.GetSearch
import com.ismaildev.rawg_perqara.domain.repository.LocalRepository
import com.ismaildev.rawg_perqara.domain.repository.Repository
import com.ismaildev.rawg_perqara.domain.usecase.DetailUseCase
import com.ismaildev.rawg_perqara.domain.usecase.FavoriteUseCase
import com.ismaildev.rawg_perqara.domain.usecase.HomeUseCase
import com.ismaildev.rawg_perqara.viewmodel.DetailViewModel
import com.ismaildev.rawg_perqara.viewmodel.FavoriteViewModel
import com.ismaildev.rawg_perqara.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val referenceModules = module {
    single { ApiService.getService() }
    single {
        Room.databaseBuilder(
            get(),
            RoomDatabase::class.java,
            RoomDatabase.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
    single {
        get<RoomDatabase>().roomDao
    }
    single<Repository> { RemoteRepositoryImpl(get()) }
    single<LocalRepository> { LocalRepositoryImpl(get()) }
}

val viewModelProject = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }

}
val useCaseModules = module {

    single {
        HomeUseCase(getGameList = GetGameList(get()), getGameDetail = GetGameDetail(get()), getSearch = GetSearch(get()), getGameListLoadMore = GetGameListLoadMore(get()))

    }
    single {
        FavoriteUseCase(
            getFavourite = GetFavorite(get()),
            saveFavourite = SaveFavourite(get()),
            deleteFavorite = DeleteFavorite(get())
        )
    }
    single {
        DetailUseCase(
            getGameDetail = GetGameDetail(get()),
            saveFavourite = SaveFavourite(get()),
            getFavoriteByIdGame = GetFavoriteByIdGame(get()),
            deleteFavorite = DeleteFavorite(get())
        )
    }
}
