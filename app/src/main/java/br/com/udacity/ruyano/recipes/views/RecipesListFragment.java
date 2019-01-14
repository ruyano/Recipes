package br.com.udacity.ruyano.recipes.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.databinding.FragmentRecipesListBinding;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.viewmodels.RecipesViewModel;


public class RecipesListFragment extends Fragment {

    private RecipesViewModel viewModel;
    private FragmentRecipesListBinding fragmentRecipesListBinding;

    public static RecipesListFragment newInstance() {
        return new RecipesListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

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

        // TODO:- verificar conexão com a internet

        setupRecipesList();
        viewModel.getRecipes();

    }

    private void setupRecipesList() {
        viewModel.getRecipesLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {

            }
        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentRecipesListBinding.recipesRecyclerview.setLayoutManager(linearLayoutManager);
        fragmentRecipesListBinding.recipesRecyclerview.setAdapter(viewModel.getAdapter());

    }
}
