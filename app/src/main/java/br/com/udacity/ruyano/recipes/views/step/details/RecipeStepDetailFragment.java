package br.com.udacity.ruyano.recipes.views.step.details;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.databinding.FragmentRecipeStepDetailBinding;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.models.Step;
import br.com.udacity.ruyano.recipes.views.recipe.details.phone.RecipeDetailFragment;

public class RecipeStepDetailFragment extends Fragment {

    private static final String ARG_RECIPE = "ARG_RECIPE";
    private static final String ARG_STEP = "ARG_STEP";

    private Recipe recipe;
    private Step step;
    private FragmentRecipeStepDetailBinding fragmentRecipeStepDetailBinding;
    private RecipeStepDetailViewModel viewModel;
    private SimpleExoPlayer player;

    public RecipeStepDetailFragment() {}

    public static RecipeStepDetailFragment newInstance(Recipe recipe, Step step) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putParcelable(ARG_STEP, step);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARG_RECIPE);
            step = getArguments().getParcelable(ARG_STEP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRecipeStepDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_detail, container, false);
        setupBindings(savedInstanceState);
        return fragmentRecipeStepDetailBinding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.release();
            player = null;
        }
    }

    private void setupBindings(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(RecipeStepDetailViewModel.class);
        if (savedInstanceState == null)
            viewModel.init();
        fragmentRecipeStepDetailBinding.setModel(viewModel);
        if (step != null) {
            viewModel.setStepMutableLiveData(step);
        }

        validateIngredientsOnTablet();
        showVideoOrImage();

    }

    private void validateIngredientsOnTablet() {
        if (step.getShortDescription().equals(getString(R.string.ingredients_list_description))) {
            viewModel.showIngredients();
            RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(recipe, false);
            getFragmentManager().beginTransaction()
                    .replace(R.id.ingredients_framelayout, recipeDetailFragment)
                    .commit();
        } else {
            viewModel.showstepDetails();
        }
    }

    private void showVideoOrImage() {
        if (step != null && step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
            Uri uri = Uri.parse(step.getVideoURL());
            setupVideo(uri);
            viewModel.showVideo();
        } else {
            viewModel.showImage();
        }
    }

    private void setupVideo(Uri mUri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getContext());
            fragmentRecipeStepDetailBinding.playerView.setPlayer(player);
        }

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getString(R.string.app_name)));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);
        // Prepare the player with the source.
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

    }
}
