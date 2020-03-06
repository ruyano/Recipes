package br.com.udacity.ruyano.recipes.views.main

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.udacity.ruyano.recipes.models.Recipe
import br.com.udacity.ruyano.recipes.networking.repositories.RecipeRepository

class MainViewModel(
        private var repository: RecipeRepository
) : ViewModel() {

    var recipesRecyclerViewVisibility = ObservableField<Int>()
    var noInternetViewVisibility = ObservableField<Int>()
    var emptyViewVisibility = ObservableField<Int>()

    var adapter: RecipesAdapter? = null

    private var _selectedRecipeMutableLiveData: MutableLiveData<Recipe>? = null
    var selectedRecipeMutableLiveData: LiveData<Recipe>? = null

    fun init() {
        recipesRecyclerViewVisibility = ObservableField(View.GONE)
        noInternetViewVisibility = ObservableField(View.GONE)
        emptyViewVisibility = ObservableField(View.GONE)
        adapter = RecipesAdapter(this)
        _selectedRecipeMutableLiveData = MutableLiveData()
        selectedRecipeMutableLiveData = _selectedRecipeMutableLiveData
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
            _selectedRecipeMutableLiveData?.value = it
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