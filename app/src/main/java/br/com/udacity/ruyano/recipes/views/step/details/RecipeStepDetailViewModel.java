package br.com.udacity.ruyano.recipes.views.step.details;

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.udacity.ruyano.recipes.models.Step;

public class RecipeStepDetailViewModel extends ViewModel {

    private MutableLiveData<Step> stepMutableLiveData;
    public ObservableInt ingredientsVisibility;
    public ObservableInt stepDetailsVisibility;
    public ObservableInt videoVisibility;
    public ObservableInt imageVisibility;

    public void init() {
        stepMutableLiveData = new MutableLiveData<>();
        ingredientsVisibility = new ObservableInt(View.GONE);
        stepDetailsVisibility = new ObservableInt(View.GONE);
        videoVisibility = new ObservableInt(View.GONE);
        imageVisibility = new ObservableInt(View.GONE);

    }

    public MutableLiveData<Step> getStepMutableLiveData() {
        return stepMutableLiveData;

    }

    public void setStepMutableLiveData(Step step) {
        this.stepMutableLiveData.setValue(step);

    }

    public void showIngredients() {
        ingredientsVisibility = new ObservableInt(View.VISIBLE);
        stepDetailsVisibility = new ObservableInt(View.GONE);

    }

    public void showstepDetails() {
        ingredientsVisibility = new ObservableInt(View.GONE);
        stepDetailsVisibility = new ObservableInt(View.VISIBLE);

    }

    public void showVideo() {
        videoVisibility = new ObservableInt(View.VISIBLE);
        imageVisibility = new ObservableInt(View.GONE);

    }

    public void showImage() {
        videoVisibility = new ObservableInt(View.GONE);
        imageVisibility = new ObservableInt(View.VISIBLE);

    }

}
