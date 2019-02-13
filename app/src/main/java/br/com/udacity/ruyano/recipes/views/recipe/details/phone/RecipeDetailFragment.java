package br.com.udacity.ruyano.recipes.views.recipe.details.phone;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.databinding.FragmentRecipeDetailBinding;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;

public class RecipeDetailFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_SHOW_STEPS = "ARG_SHOW_STEPS";

    private Recipe recipe;
    private Boolean showSteps;
    private OnFragmentInteractionListener mListener;
    private RecipeDetailsViewModel viewModel;
    private FragmentRecipeDetailBinding fragmentRecipeDetailBinding;

    public RecipeDetailFragment() {}

    public static RecipeDetailFragment newInstance(Recipe recipe, Boolean showSteps) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putBoolean(ARG_SHOW_STEPS, showSteps);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARG_RECIPE);
            showSteps = getArguments().getBoolean(ARG_SHOW_STEPS);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRecipeDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        setupBindings(savedInstanceState);
        return fragmentRecipeDetailBinding.getRoot();

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

    private void setupBindings(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(RecipeDetailsViewModel.class);
        if (savedInstanceState == null)
            viewModel.init();

        fragmentRecipeDetailBinding.setModel(viewModel);

        if (recipe != null) {
            viewModel.setRecipeMutableLiveData(recipe);
            setupIngredentsListView();
            observeRecipes();
            setupStepsRecyclerView();
            setupOnStepSelected();
        }

        if (showSteps) {
            viewModel.showSteps();
        } else {
            viewModel.hideSteps();
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
                fragmentRecipeDetailBinding.recipeNameTextview.setText(recipe.getName());

            }
        });

    }

    private void setupOnStepSelected() {
        viewModel.getSelectedStepMutableLiveData().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(Step step) {
                mListener.onStepSelected(step);
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onStepSelected(Step step);

    }
}
