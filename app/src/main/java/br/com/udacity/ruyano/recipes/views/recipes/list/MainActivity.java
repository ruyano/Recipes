package br.com.udacity.ruyano.recipes.views.recipes.list;

import androidx.appcompat.app.AppCompatActivity;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.networking.repositories.RecipeRepository;
import br.com.udacity.ruyano.recipes.views.recipe.detail.RecipeDetailActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RecipesListFragment.OnRecipeClickListener {

    private Boolean isUsingTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // tow panel layout only exists in tablets
        isUsingTablet = findViewById(R.id.two_panel_linear_layout) != null;

        new RecipeRepository().getRecipes();
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        if (isUsingTablet) {
            Toast.makeText(this, "Tablet - " + recipe.getName(), Toast.LENGTH_SHORT).show();

        } else {
            startActivity(RecipeDetailActivity.getIntent(this, recipe));

        }
    }
}
