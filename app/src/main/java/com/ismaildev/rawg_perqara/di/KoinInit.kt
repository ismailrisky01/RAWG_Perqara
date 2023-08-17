package com.ismaildev.rawg_perqara.di

import android.content.Context
import androidx.startup.Initializer
import com.ismaildev.rawg_perqara.di.referenceModules
import com.ismaildev.rawg_perqara.di.useCaseModules
import com.ismaildev.rawg_perqara.di.viewModelProject
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.dsl.koinApplication

internal class KoinInit : Initializer<Koin> {
    override fun create(context: Context): Koin {
        return koinApplication {
            androidContext(context)
            modules( listOf(viewModelProject, useCaseModules, referenceModules ))
        }.koin
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}