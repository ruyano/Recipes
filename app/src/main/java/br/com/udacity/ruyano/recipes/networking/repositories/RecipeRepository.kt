package br.com.udacity.ruyano.recipes.networking.repositories

import androidx.lifecycle.MutableLiveData
import br.com.udacity.ruyano.recipes.models.Recipe
import br.com.udacity.ruyano.recipes.networking.RetrofitConfig
import br.com.udacity.ruyano.recipes.networking.services.IAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RecipeRepository(
        private val api: IAPIService
) {
    val recipesLiveData: MutableLiveData<List<Recipe>> = MutableLiveData()

    fun recipes() {
        api.recipes.enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                recipesLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                recipesLiveData.postValue(ArrayList())
            }
        })
    }
}