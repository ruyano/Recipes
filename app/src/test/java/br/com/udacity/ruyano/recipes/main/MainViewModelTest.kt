package br.com.udacity.ruyano.recipes.main

import android.view.View.GONE
import android.view.View.VISIBLE
import br.com.udacity.ruyano.recipes.networking.repositories.RecipeRepository
import br.com.udacity.ruyano.recipes.views.main.MainViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val repository: RecipeRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun testCall() {
        viewModel.callRecipes()

        verify {
            repository.recipes()
        }
    }

    @Test
    fun testShowRecipesList() {
        viewModel.showRecipesList()

        Assert.assertEquals(VISIBLE, viewModel.recipesRecyclerViewVisibility.get())
        Assert.assertEquals(GONE, viewModel.noInternetViewVisibility.get())
        Assert.assertEquals(GONE, viewModel.emptyViewVisibility.get())
    }

    @Test
    fun testShowEmptyView() {
        viewModel.showEmptyView()

        Assert.assertEquals(VISIBLE, viewModel.emptyViewVisibility.get())
        Assert.assertEquals(GONE, viewModel.recipesRecyclerViewVisibility.get())
        Assert.assertEquals(GONE, viewModel.noInternetViewVisibility.get())
    }

    @Test
    fun testShowNoInternetView() {
        viewModel.showNoInternetView()

        Assert.assertEquals(VISIBLE, viewModel.noInternetViewVisibility.get())
        Assert.assertEquals(GONE, viewModel.recipesRecyclerViewVisibility.get())
        Assert.assertEquals(GONE, viewModel.emptyViewVisibility.get())
    }
}