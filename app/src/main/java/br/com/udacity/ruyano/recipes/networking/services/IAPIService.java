package br.com.udacity.ruyano.recipes.networking.services;

import java.util.List;

import br.com.udacity.ruyano.recipes.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IAPIService {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}
