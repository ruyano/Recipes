package br.com.udacity.ruyano.recipes.views.recipe.details.phone;

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.recipes.models.Ingredient;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;

public class RecipeDetailsViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipeMutableLiveData;
    private IngredientsAdapter ingredientsAdapter;
    private StepAdapter stepAdapter;
    private MutableLiveData<Step> selectedStepMutableLiveData;
    private ObservableInt showSteps;

    public void init() {
        recipeMutableLiveData = new MutableLiveData<>();
        ingredientsAdapter = new IngredientsAdapter(this);
        stepAdapter = new StepAdapter(this);
        selectedStepMutableLiveData = new MutableLiveData<>();
        showSteps = new ObservableInt(View.GONE);

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

    public MutableLiveData<Step> getSelectedStepMutableLiveData() {
        return selectedStepMutableLiveData;
    }

    public ObservableInt getShowSteps() {
        return showSteps;

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

    public void onStepClick(Integer index) {
        Step step = getStepAtPosition(index);
        if (step != null)
            selectedStepMutableLiveData.setValue(step);

    }

    public void showSteps() {
        showSteps.set(View.VISIBLE);

    }

    public void hideSteps() {
        showSteps.set(View.GONE);

    }

}
