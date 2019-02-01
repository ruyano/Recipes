package br.com.udacity.ruyano.recipes.views.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.databinding.ActivityNewMainBinding;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.utils.NetworkUtil;
import br.com.udacity.ruyano.recipes.views.recipe.details.RecipeDetailsActivity;

public class MainActivity extends AppCompatActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;

    }

    private ActivityNewMainBinding activityNewMainBinding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);

        setupViewBinding(savedInstanceState);
    }

    private void setupViewBinding(Bundle savedInstanceState) {
        activityNewMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityNewMainBinding.setModel(viewModel);

        if (getResources().getBoolean(R.bool.isTablet)) {
            setupGridRecyclerView();
        } else {
            setupLinearRecyclerView();
        }

        setupRecipesList();
        setupOnItemSelected();

        if (NetworkUtil.isConected(this)) {
            viewModel.getRecipes();
        } else {
            // TODO - melhorar view de no internet
            viewModel.showNoInternetView();
        }

    }

    private void setupLinearRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityNewMainBinding.recipesRecyclerview.setLayoutManager(linearLayoutManager);
        activityNewMainBinding.recipesRecyclerview.setAdapter(viewModel.getAdapter());

    }

    private void setupGridRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        activityNewMainBinding.recipesRecyclerview.setLayoutManager(gridLayoutManager);
        activityNewMainBinding.recipesRecyclerview.setAdapter(viewModel.getAdapter());

    }

    private void setupRecipesList() {
        viewModel.getRecipesLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null && recipes.size() > 0) {
                    viewModel.getAdapter().notifyDataSetChanged();
                    viewModel.showRecipesList();
                } else {
                    // TODO - melhor view de empty view
                    viewModel.showEmptyView();
                }
            }
        });

    }

    private void setupOnItemSelected() {
        viewModel.getSelectedRecipeMutableLiveData().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                startActivity(RecipeDetailsActivity.getIntent(MainActivity.this, recipe));
                finish();
            }
        });

    }

}
