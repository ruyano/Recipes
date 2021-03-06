package br.com.udacity.ruyano.recipes.views.step.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;

public class RecipeStepActivity extends AppCompatActivity {

    private static final String RECIPE_EXTRA = "RECIPE_EXTRA";
    private static final String STEP_POSITION_EXTRA = "STEP_POSITION_EXTRA";
    private static final String DETAILS_FRAGMENT_TAG = "DETAILS_FRAGMENT_TAG";
    private static final String STEP_POSITION = "STEP_POSITION";

    public static Intent getIntent(Context context, Recipe recipe, Integer stepPosition) {
        Intent intent = new Intent(context, RecipeStepActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        intent.putExtra(STEP_POSITION_EXTRA, stepPosition);
        return intent;

    }

    private Recipe recipe;
    private Integer stepPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        verifySavedInstance(savedInstanceState);
        setActionBarTitle();
        setupPhoneFragments();
        setupButtons();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION, stepPosition);

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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void verifySavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            stepPosition = savedInstanceState.getInt(STEP_POSITION, 0);
        }
    }

    private void setupButtons() {
        if (stepPosition == 0)
            findViewById(R.id.previous_button).setVisibility(View.INVISIBLE);
        else
            findViewById(R.id.previous_button).setVisibility(View.VISIBLE);

        if (stepPosition == recipe.getSteps().size() - 1)
            findViewById(R.id.next_button).setVisibility(View.INVISIBLE);
        else
            findViewById(R.id.next_button).setVisibility(View.VISIBLE);

    }

    private void getExtras() {
        if (getIntent().getExtras() != null
                && getIntent().hasExtra(RECIPE_EXTRA)
                && getIntent().hasExtra(STEP_POSITION_EXTRA)) {
            recipe = getIntent().getExtras().getParcelable(RECIPE_EXTRA);
            stepPosition = getIntent().getIntExtra(STEP_POSITION_EXTRA, 0);
        }

    }

    private void setActionBarTitle() {
        if (recipe != null
                && recipe.getSteps() != null
                && recipe.getSteps().size() >= stepPosition
                && !recipe.getSteps().get(stepPosition).getShortDescription().isEmpty()) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(recipe.getSteps().get(stepPosition).getShortDescription());
        }

    }

    private void setupPhoneFragments() {
        if (recipe != null
                && recipe.getSteps() != null
                && recipe.getSteps().size() >= stepPosition
                && recipe.getSteps().get(stepPosition) != null) {

            Fragment recipeStepDetailFragment = getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT_TAG + stepPosition);
            if (recipeStepDetailFragment == null) {
                recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(recipe, recipe.getSteps().get(stepPosition));
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_fragment, recipeStepDetailFragment, DETAILS_FRAGMENT_TAG + stepPosition)
                    .commit();

        }

    }

    public void onCLickPrevious(View view) {
        if (stepPosition > 0) {
            stepPosition--;
            setupPhoneFragments();
            setupButtons();
        }

    }

    public void onCLickNex(View view) {
        if (stepPosition < recipe.getSteps().size()) {
            stepPosition++;
            setupPhoneFragments();
            setupButtons();
        }

    }
}
