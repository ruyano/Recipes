package br.com.udacity.ruyano.recipes.views.recipe.details.tablet;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;

public class RecipeDetailListViewModel extends ViewModel {

    private MutableLiveData<Recipe> recipeMutableLiveData;
    private RecipeDetailListAdapter adapter;
    private MutableLiveData<Step> selectedStep;

    public void init() {
        recipeMutableLiveData = new MutableLiveData<>();
        adapter = new RecipeDetailListAdapter(this);
        selectedStep = new MutableLiveData<>();

    }

    public void setRecipeMutableLiveData(Recipe recipe) {
        if (!recipe.getSteps().get(0).getShortDescription().equals("Ingredients")) {
            Step ingredients = new Step();
            ingredients.setShortDescription("Ingredients");
            List<Step> newSteps = new ArrayList<>();
            newSteps.add(ingredients);
            newSteps.addAll(recipe.getSteps());
            recipe.setSteps(newSteps);
        }
        this.recipeMutableLiveData.setValue(recipe);

    }

    public MutableLiveData<Recipe> getRecipeMutableLiveData() {
        return recipeMutableLiveData;

    }

    public RecipeDetailListAdapter getAdapter() {
        return adapter;

    }

    public MutableLiveData<Step> getSelectedStep() {
        return selectedStep;
    }

    public Step getStepAt(Integer position) {
        if (recipeMutableLiveData != null
                && recipeMutableLiveData.getValue() != null
                && recipeMutableLiveData.getValue().getSteps() != null
                && recipeMutableLiveData.getValue().getSteps().size() > position) {
            return recipeMutableLiveData.getValue().getSteps().get(position);
        }
        return null;

    }

    public void onItemClick(Integer index) {
        Step step = getStepAt(index);
        if (step != null) {
            selectedStep.setValue(step);
            adapter.setSelectedPos(index);
        }

    }


}
