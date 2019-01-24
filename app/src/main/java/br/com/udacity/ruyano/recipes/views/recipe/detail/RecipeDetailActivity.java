package br.com.udacity.ruyano.recipes.views.recipe.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnFragmentInteractionListener {

    private static final String RECIPE_EXTRA = "RECIPE_EXTRA";

    public static Intent getIntent(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);
        return intent;

    }

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getExtras();
        setActionBarTitle();

        if (savedInstanceState == null) {
            setupFragment();
        }
    }

    private void getExtras() {
        if (getIntent().getExtras() != null && getIntent().hasExtra(RECIPE_EXTRA)) {
            this.recipe = getIntent().getExtras().getParcelable(RECIPE_EXTRA);
        }

    }

    private void setActionBarTitle() {
        if (recipe != null && !recipe.getName().isEmpty()) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(recipe.getName());
        }

    }

    private void setupFragment() {
        if (recipe != null) {
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_detail_fragment, recipeDetailFragment)
                    .commit();
        } else {
            // TODO - tratar erro, quando não tiver uma receita
        }
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
    public void onFragmentInteraction(Uri uri) {

    }
}
