package br.com.udacity.ruyano.recipes.views.main.di

import br.com.udacity.ruyano.recipes.networking.RetrofitConfig
import br.com.udacity.ruyano.recipes.networking.repositories.RecipeRepository
import br.com.udacity.ruyano.recipes.views.main.MainViewModel
import org.koin.dsl.module

val apiModule = module {
    single { RetrofitConfig.getInstance().api }
}
val repositoryModule = module {
    single { RecipeRepository(get()) }
}
val mainViewModelModule = module {
    single { MainViewModel(get()) }
}