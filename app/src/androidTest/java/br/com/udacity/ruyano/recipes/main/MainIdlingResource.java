package br.com.udacity.ruyano.recipes.main;

import androidx.test.espresso.IdlingResource;

public class MainIdlingResource implements IdlingResource {



    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isIdleNow() {
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

    }

}
