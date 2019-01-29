package br.com.udacity.ruyano.recipes.views.newrecipes.recipe.details.tablet;

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
import br.com.udacity.ruyano.recipes.databinding.FragmentRecipeDetailListBinding;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;

public class RecipeDetailListFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";

    private Recipe recipe;
    private RecipeDetailListViewModel viewModel;
    private FragmentRecipeDetailListBinding fragmentRecipeDetailListBinding;
    private OnFragmentInteractionListener mListener;

    public static RecipeDetailListFragment newInstance(Recipe recipe) {
        RecipeDetailListFragment fragment = new RecipeDetailListFragment();
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
        fragmentRecipeDetailListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail_list, container, false);
        setupBindings(savedInstanceState);
        return fragmentRecipeDetailListBinding.getRoot();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeDetailListFragment.OnFragmentInteractionListener) {
            mListener = (RecipeDetailListFragment.OnFragmentInteractionListener) context;
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
        viewModel = ViewModelProviders.of(this).get(RecipeDetailListViewModel.class);
        if (savedInstanceState == null)
            viewModel.init();

        fragmentRecipeDetailListBinding.setModel(viewModel);

        if (recipe != null) {
            viewModel.setRecipeMutableLiveData(recipe);
            setupRecipeDetailRecyclerView();
            setupOnStepSelected();

        } else {
            // TODO - tratar erro quando não tiver recipe

        }
    }

    private void setupOnStepSelected() {
        viewModel.getSelectedStep().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(Step step) {
                mListener.onStepSelected(step);
            }
        });
    }

    private void setupRecipeDetailRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentRecipeDetailListBinding.recipeDetailListRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentRecipeDetailListBinding.recipeDetailListRecyclerView.setAdapter(viewModel.getAdapter());

    }

    public interface OnFragmentInteractionListener {
        void onStepSelected(Step step);

    }


}
