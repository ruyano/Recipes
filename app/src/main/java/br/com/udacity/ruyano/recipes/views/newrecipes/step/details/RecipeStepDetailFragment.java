package br.com.udacity.ruyano.recipes.views.newrecipes.step.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.databinding.FragmentRecipeStepDetailBinding;
import br.com.udacity.ruyano.recipes.models.Step;

public class RecipeStepDetailFragment extends Fragment {

    private static final String ARG_STEP = "ARG_STEP";

    private Step step;
    private FragmentRecipeStepDetailBinding fragmentRecipeStepDetailBinding;
    private RecipeStepDetailViewModel viewModel;

    public RecipeStepDetailFragment() {}

    public static RecipeStepDetailFragment newInstance(Step step) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(ARG_STEP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRecipeStepDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_detail, container, false);
        setupBindings(savedInstanceState);
        return fragmentRecipeStepDetailBinding.getRoot();
    }

    private void setupBindings(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(RecipeStepDetailViewModel.class);
        if (savedInstanceState == null)
            viewModel.init();
        fragmentRecipeStepDetailBinding.setModel(viewModel);
        if (step != null) {
            viewModel.setStepMutableLiveData(step);
        } else {
            // TODO - tratar erro quando não tiver recipe
        }

    }

}
