package br.com.udacity.ruyano.recipes.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.recipes.models.Ingredient;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;
import br.com.udacity.ruyano.recipes.views.recipe.detail.IngredientsAdapter;
import br.com.udacity.ruyano.recipes.views.recipe.detail.StepAdapter;

public class RecipeDetailsViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipeMutableLiveData;
    private IngredientsAdapter ingredientsAdapter;
    private StepAdapter stepAdapter;

    public void init() {
        recipeMutableLiveData = new MutableLiveData<>();
        ingredientsAdapter = new IngredientsAdapter(this);
        stepAdapter = new StepAdapter(this);

    }

    public MutableLiveData<Recipe> getRecipeMutableLiveData() {
        return recipeMutableLiveData;

    }

    public void setRecipeMutableLiveData(Recipe recipe) {
        this.recipeMutableLiveData.postValue(recipe);

    }

    public IngredientsAdapter getIngredientsAdapter() {
        return ingredientsAdapter;

    }

    public StepAdapter getStepAdapter() {
        return stepAdapter;

    }

    public Ingredient getIngredentAtPosition(Integer position) {
        if (recipeMutableLiveData.getValue() != null
                && recipeMutableLiveData.getValue().getIngredients() != null
                && recipeMutableLiveData.getValue().getIngredients().size() >= position) {
            return recipeMutableLiveData.getValue().getIngredients().get(position);
        }
        return null;

    }

    public Step getStepAtPosition(Integer position) {
        if (recipeMutableLiveData.getValue() != null
                && recipeMutableLiveData.getValue().getSteps() != null
                && recipeMutableLiveData.getValue().getSteps().size() >= position) {
            return recipeMutableLiveData.getValue().getSteps().get(position);
        }
        return null;

    }

}
