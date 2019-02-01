package br.com.udacity.ruyano.recipes.networking.repositories;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.networking.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private final MutableLiveData<List<Recipe>> recipesLiveData;

    public RecipeRepository() {
        this.recipesLiveData = new MutableLiveData<>();

    }

    public void getRecipes() {
        RetrofitConfig.getInstance().getApi().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipesLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                recipesLiveData.postValue(new ArrayList<Recipe>());

            }
        });

    }

    public MutableLiveData<List<Recipe>> getRecipesLiveData() {
        return recipesLiveData;

    }
}
