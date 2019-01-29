package br.com.udacity.ruyano.recipes.views.newrecipes.recipe.details;

import androidx.appcompat.app.AppCompatActivity;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;
import br.com.udacity.ruyano.recipes.views.newrecipes.recipe.details.phone.RecipeDetailFragment;
import br.com.udacity.ruyano.recipes.views.newrecipes.recipe.details.tablet.RecipeDetailListFragment;
import br.com.udacity.ruyano.recipes.views.newrecipes.step.details.RecipeStepDetailFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;

public class NewRecipeDetailsActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener, RecipeDetailListFragment.OnFragmentInteractionListener {

    private static final String RECIPE_EXTRA = "RECIPE_EXTRA";

    public static Intent getIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, NewRecipeDetailsActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        return intent;

    }

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        setActionBarTitle();

        if (savedInstanceState == null) {
            setupFragment();
        }

    }

    private void getExtras() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(RECIPE_EXTRA)) {
            recipe = getIntent().getExtras().getParcelable(RECIPE_EXTRA);
        }

    }

    private void setActionBarTitle() {
        if (recipe != null && !recipe.getName().isEmpty()) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(recipe.getName());
        }

    }

    private void setupFragment() {
        if (recipe != null) {
            if (getResources().getBoolean(R.bool.isTablet)) {
                setupTabletFragments();
            } else {
                setupPhoneFragments();
            }
        } else {
            // TODO - tratar erro, quando não tiver uma receita
        }
    }

    private void setupTabletFragments() {

        // Setup master
        RecipeDetailListFragment recipeDetailListFragment = RecipeDetailListFragment.newInstance(recipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.master_fragment, recipeDetailListFragment)
                .commit();

        // Setup detail

    }

    private void setupPhoneFragments() {
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_fragment, recipeDetailFragment)
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStepSelected(Step step) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(step);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, recipeStepDetailFragment)
                    .commit();
        } else {
            Toast.makeText(this, step.getShortDescription(), Toast.LENGTH_SHORT).show();
        }

    }

}
