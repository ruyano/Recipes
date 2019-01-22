package br.com.udacity.ruyano.recipes.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.databinding.FragmentRecipesListBinding;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.utils.NetworkUtil;
import br.com.udacity.ruyano.recipes.viewmodels.RecipesViewModel;


public class RecipesListFragment extends Fragment {

    private RecipesViewModel viewModel;
    private FragmentRecipesListBinding fragmentRecipesListBinding;

    public static RecipesListFragment newInstance() {
        return new RecipesListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupBindings(savedInstanceState);
        return inflater.inflate(R.layout.fragment_recipes_list, container, false);

    }

    private void setupBindings(Bundle savedInstanceState) {
        fragmentRecipesListBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_recipes_list);
        viewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        if (savedInstanceState == null)
            viewModel.init();
        fragmentRecipesListBinding.setModel(viewModel);

        setupRecyclerView();
        setupRecipesList();
        setupOnItemSelected();

        if (NetworkUtil.isConected(getContext())) {
            viewModel.getRecipes();
        } else {
            viewModel.showNoInternetView();
        }

    }

    private void setupRecipesList() {
        viewModel.getRecipesLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null && recipes.size() > 0) {
                    viewModel.getAdapter().notifyDataSetChanged();
                    viewModel.showRecipesList();
                } else {
                    viewModel.showEmptyView();
                }
            }
        });

    }

    private void setupOnItemSelected() {
        viewModel.getSelectedRecipeMutableLiveData().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                Toast.makeText(getContext(), recipe.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentRecipesListBinding.recipesRecyclerview.setLayoutManager(linearLayoutManager);
        fragmentRecipesListBinding.recipesRecyclerview.setAdapter(viewModel.getAdapter());

    }

}
