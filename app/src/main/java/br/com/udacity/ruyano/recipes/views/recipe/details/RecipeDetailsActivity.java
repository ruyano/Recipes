package br.com.udacity.ruyano.recipes.views.recipe.details;

import androidx.appcompat.app.AppCompatActivity;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;
import br.com.udacity.ruyano.recipes.utils.RecipeWidgetUtil;
import br.com.udacity.ruyano.recipes.views.main.MainActivity;
import br.com.udacity.ruyano.recipes.views.recipe.details.phone.RecipeDetailFragment;
import br.com.udacity.ruyano.recipes.views.recipe.details.tablet.RecipeDetailListFragment;
import br.com.udacity.ruyano.recipes.views.step.details.RecipeStepActivity;
import br.com.udacity.ruyano.recipes.views.step.details.RecipeStepDetailFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener, RecipeDetailListFragment.OnFragmentInteractionListener {

    private static final String RECIPE_EXTRA = "RECIPE_EXTRA";

    public static Intent getIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        return intent;

    }

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        setActionBarTitle();

        if (savedInstanceState == null) {
            setupFragment();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_activity_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.pin_to_widget:
                updateWidget();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        startActivity(MainActivity.getIntent(this));
        finish();
    }

    private void updateWidget() {
        if (recipe != null) {
            RecipeWidgetUtil.updateRecipeWidget(this, recipe);
            Toast.makeText(this, getString(R.string.recipe_pinned_to_widget), Toast.LENGTH_SHORT).show();
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
    public void onStepSelected(Step step) {
        if (getResources().getBoolean(R.bool.isTablet)) {
            RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(step);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, recipeStepDetailFragment)
                    .commit();
        } else {
            startActivity(RecipeStepActivity.getIntent(this, recipe, recipe.getSteps().indexOf(step)));
        }

    }

}
