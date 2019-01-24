package br.com.udacity.ruyano.recipes.views.recipes.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
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

    public static RecipesListFragment newInstance() {
        return new RecipesListFragment();

    }

    private OnRecipeClickListener mCallback;
    private RecipesViewModel viewModel;
    private FragmentRecipesListBinding fragmentRecipesListBinding;


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
                mCallback.onRecipeSelected(recipe);
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentRecipesListBinding.recipesRecyclerview.setLayoutManager(linearLayoutManager);
        fragmentRecipesListBinding.recipesRecyclerview.setAdapter(viewModel.getAdapter());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);

    }

}
