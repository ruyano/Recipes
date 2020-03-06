package br.com.udacity.ruyano.recipes.views.main

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import br.com.udacity.ruyano.recipes.models.Recipe
import br.com.udacity.ruyano.recipes.networking.repositories.RecipeRepository
import br.com.udacity.ruyano.recipes.utils.NetworkUtil

class MainViewModel(
        private var repository: RecipeRepository
) : ViewModel() {

    var recipesRecyclerViewVisibility = ObservableField<Int>()
    var noInternetViewVisibility = ObservableField<Int>()
    var emptyViewVisibility = ObservableField<Int>()

    var adapter: RecipesAdapter? = null

    var selectedRecipeMutableLiveData = MutableLiveData<Recipe>()

    fun init() {
        recipesRecyclerViewVisibility = ObservableField(View.GONE)
        noInternetViewVisibility = ObservableField(View.GONE)
        emptyViewVisibility = ObservableField(View.GONE)
        adapter = RecipesAdapter(this)
    }

    fun callRecipes() {
        repository.recipes()
    }

    val recipesLiveData = repository.recipesLiveData

    fun received(list : List<Recipe>) {
        if (list.isNotEmpty()) {
            adapter?.notifyDataSetChanged()
            showRecipesList()
        } else {
            showEmptyView()
        }
    }

    fun hasInternetConnection(value: Boolean) {
        if (value) {
            callRecipes()
        } else {
            showNoInternetView()
        }
    }

    fun getRecipeAt(position: Int): Recipe? =
        repository.recipesLiveData.value?.let {
            if (it.size > position) {
                it[position]
            } else {
                null
            }
        } ?: run {
            null
        }

    fun onItemClick(index: Int) {
        getRecipeAt(index)?.let {
            selectedRecipeMutableLiveData?.value = it
        }
    }

    fun showRecipesList() {
        recipesRecyclerViewVisibility.set(View.VISIBLE)
        noInternetViewVisibility.set(View.GONE)
        emptyViewVisibility.set(View.GONE)
    }

    fun showEmptyView() {
        emptyViewVisibility.set(View.VISIBLE)
        recipesRecyclerViewVisibility.set(View.GONE)
        noInternetViewVisibility.set(View.GONE)
    }

    fun showNoInternetView() {
        noInternetViewVisibility.set(View.VISIBLE)
        recipesRecyclerViewVisibility.set(View.GONE)
        emptyViewVisibility.set(View.GONE)
    }
}