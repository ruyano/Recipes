package br.com.udacity.ruyano.recipes.views.recipe.detail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.databinding.FragmentRecipeDetailBinding;
import br.com.udacity.ruyano.recipes.models.Ingredient;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;
import br.com.udacity.ruyano.recipes.utils.NetworkUtil;
import br.com.udacity.ruyano.recipes.viewmodels.RecipeDetailsViewModel;
import br.com.udacity.ruyano.recipes.viewmodels.RecipesViewModel;

public class RecipeDetailFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";

    private Recipe recipe;
    private OnFragmentInteractionListener mListener;
    private RecipeDetailsViewModel viewModel;
    private FragmentRecipeDetailBinding fragmentRecipeDetailBinding;

    public RecipeDetailFragment() {}

    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARG_RECIPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupBindings(savedInstanceState);
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void setupBindings(Bundle savedInstanceState) {
        fragmentRecipeDetailBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_recipe_detail);
        viewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        if (savedInstanceState == null)
            viewModel.init();

        fragmentRecipeDetailBinding.setModel(viewModel);

        if (recipe != null) {
            viewModel.setRecipeMutableLiveData(recipe);
            setupIngredentsListView();
            setupStepsRecyclerView();
            observeRecipes();
        } else {
            // TODO - tratar erro quando não tiver recipe

        }

    }

    private void setupIngredentsListView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentRecipeDetailBinding.ingredientsRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentRecipeDetailBinding.ingredientsRecyclerView.setAdapter(viewModel.getIngredientsAdapter());

    }

    private void setupStepsRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentRecipeDetailBinding.stepsRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentRecipeDetailBinding.stepsRecyclerView.setAdapter(viewModel.getStepAdapter());

    }

    private void observeRecipes() {
        viewModel.getRecipeMutableLiveData().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                viewModel.getIngredientsAdapter().notifyDataSetChanged();
                viewModel.getStepAdapter().notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
