package br.com.udacity.ruyano.recipes.di

import br.com.udacity.ruyano.recipes.views.main.di.apiModule
import br.com.udacity.ruyano.recipes.views.main.di.mainViewModelModule
import br.com.udacity.ruyano.recipes.views.main.di.repositoryModule

object Injection {

    val modules = listOf(
            apiModule,
            repositoryModule,
            mainViewModelModule
    )

}