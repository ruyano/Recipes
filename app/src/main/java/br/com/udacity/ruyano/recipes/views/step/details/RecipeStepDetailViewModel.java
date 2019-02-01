package br.com.udacity.ruyano.recipes.views.step.details;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.recipes.models.Step;

public class RecipeStepDetailViewModel extends ViewModel {

    private MutableLiveData<Step> stepMutableLiveData;

    public void init() {
        stepMutableLiveData = new MutableLiveData<>();

    }

    public MutableLiveData<Step> getStepMutableLiveData() {
        return stepMutableLiveData;

    }

    public void setStepMutableLiveData(Step step) {
        this.stepMutableLiveData.setValue(step);

    }
}
