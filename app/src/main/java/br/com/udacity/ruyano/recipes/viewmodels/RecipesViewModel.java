package br.com.udacity.ruyano.recipes.viewmodels;

import android.view.View;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.networking.repositories.RecipeRepository;
import br.com.udacity.ruyano.recipes.views.RecipesAdapter;

public class RecipesViewModel extends ViewModel {

    public ObservableInt recipesRecyclerViewVisibility;
    public ObservableInt noInternetViewVisibility;
    public ObservableInt emptyViewVisibility;

    private RecipeRepository repository;
    private RecipesAdapter recipesAdapter;

    public void init() {
        recipesRecyclerViewVisibility = new ObservableInt(View.GONE);
        noInternetViewVisibility = new ObservableInt(View.GONE);
        emptyViewVisibility = new ObservableInt(View.GONE);
        repository = new RecipeRepository();
        recipesAdapter = new RecipesAdapter(this);

    }

    public void getRecipes() {
        repository.getRecipes();

    }

    public MutableLiveData<List<Recipe>> getRecipesLiveData() {
        return repository.getRecipesLiveData();

    }

    public RecipesAdapter getAdapter() {
        return recipesAdapter;

    }

    public Object getRecipeAt(Integer position) {
        if (repository.getRecipesLiveData().getValue() != null
                && repository.getRecipesLiveData().getValue().size() > position) {
            return repository.getRecipesLiveData().getValue().get(position);
        }
        return null;
    }
}
