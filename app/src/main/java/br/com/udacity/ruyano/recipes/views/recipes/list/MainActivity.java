package br.com.udacity.ruyano.recipes.views.recipes.list;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.views.recipe.detail.RecipeDetailActivity;
import br.com.udacity.ruyano.recipes.views.recipe.detail.RecipeDetailContainerFragment;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.OnRecipeClickListener {

    private Boolean isUsingTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tow panel layout only exists in tablets
        isUsingTablet = findViewById(R.id.two_panel_linear_layout) != null;
    }


    @Override
    public void onRecipeSelected(Recipe recipe) {
        if (isUsingTablet) {
            RecipeDetailContainerFragment recipeDetailContainerFragment = RecipeDetailContainerFragment.newInstance(recipe);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.master_detail_fragment, recipeDetailContainerFragment);
            ft.commit();

        } else {
            startActivity(RecipeDetailActivity.getIntent(this, recipe));

        }
    }

}
