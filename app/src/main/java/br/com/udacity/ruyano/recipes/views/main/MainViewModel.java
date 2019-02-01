package br.com.udacity.ruyano.recipes.views.main;

import android.view.View;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.networking.repositories.RecipeRepository;

public class MainViewModel extends ViewModel {

    public ObservableInt recipesRecyclerViewVisibility;
    public ObservableInt noInternetViewVisibility;
    public ObservableInt emptyViewVisibility;

    private RecipeRepository repository;
    private RecipesAdapter recipesAdapter;
    private MutableLiveData<Recipe> selectedRecipeMutableLiveData;

    public void init() {
        recipesRecyclerViewVisibility = new ObservableInt(View.GONE);
        noInternetViewVisibility = new ObservableInt(View.GONE);
        emptyViewVisibility = new ObservableInt(View.GONE);
        repository = new RecipeRepository();
        recipesAdapter = new RecipesAdapter(this);
        selectedRecipeMutableLiveData = new MutableLiveData<>();

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

    public Recipe getRecipeAt(Integer position) {
        if (repository.getRecipesLiveData().getValue() != null
                && repository.getRecipesLiveData().getValue().size() > position) {
            return repository.getRecipesLiveData().getValue().get(position);
        }
        return null;
    }

    public void onItemClick(Integer index) {
        Recipe selected = getRecipeAt(index);
        if (selected != null) {
            selectedRecipeMutableLiveData.setValue(selected);

        }

    }


    public MutableLiveData<Recipe> getSelectedRecipeMutableLiveData() {
        return selectedRecipeMutableLiveData;

    }

    public void showRecipesList() {
        recipesRecyclerViewVisibility.set(View.VISIBLE);
        noInternetViewVisibility.set(View.GONE);
        emptyViewVisibility.set(View.GONE);

    }

    public void showNoInternetView() {
        recipesRecyclerViewVisibility.set(View.GONE);
        noInternetViewVisibility.set(View.VISIBLE);
        emptyViewVisibility.set(View.GONE);

    }

    public void showEmptyView() {
        recipesRecyclerViewVisibility.set(View.GONE);
        noInternetViewVisibility.set(View.GONE);
        emptyViewVisibility.set(View.VISIBLE);

    }

}
