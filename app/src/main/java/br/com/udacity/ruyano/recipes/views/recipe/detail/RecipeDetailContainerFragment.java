package br.com.udacity.ruyano.recipes.views.recipe.detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentTransaction;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;
import br.com.udacity.ruyano.recipes.views.newrecipes.recipe.details.tablet.RecipeDetailListFragment;
import br.com.udacity.ruyano.recipes.views.newrecipes.step.details.RecipeStepDetailFragment;

public class RecipeDetailContainerFragment extends Fragment implements RecipeDetailListFragment.OnFragmentInteractionListener {

    private static final String ARG_RECIPE = "recipe";

    private Recipe recipe;
    private Boolean isUsingTablet = false;

    public RecipeDetailContainerFragment() {}

    public static RecipeDetailContainerFragment newInstance(Recipe recipe) {
        RecipeDetailContainerFragment fragment = new RecipeDetailContainerFragment();
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
        View view = inflater.inflate(R.layout.fragment_recipe_detail_container, container, false);
        isUsingTablet = view.findViewById(R.id.two_panel_linear_layout) != null;
        setupRecipeDetailListFragment();
        return view;

    }

    private void setupRecipeDetailListFragment() {
//        RecipeDetailListFragment recipeDetailListFragment = RecipeDetailListFragment.newInstance(recipe, this);
//        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.recipe_detail_list_frame, recipeDetailListFragment);
//        ft.commit();

    }


    @Override
    public void onStepSelected(Step step) {
        if (isUsingTablet) {
            RecipeStepDetailFragment recipeStepDetailFragment = RecipeStepDetailFragment.newInstance(step);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.master_detail_step_fragment, recipeStepDetailFragment);
            ft.commit();
        } else {
//            startActivity(RecipeDetailActivity.getIntent(this, recipe))

        }
    }

}
