package br.com.udacity.ruyano.recipes.views.step.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.SimpleExoPlayer;

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
    private SimpleExoPlayer player;

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

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        initializePlayer();
//        if (step != null && step.getVideoURL() != null && !step.getVideoURL().isEmpty()) {
//            Uri uri = Uri.parse(step.getVideoURL());
//            buildMediaSource(uri);
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        if (player!=null) {
//            player.release();
//            player = null;
//        }
//    }
//
//    private void initializePlayer() {
//        if (player == null) {
//            // 1. Create a default TrackSelector
//            LoadControl loadControl = new DefaultLoadControl(
//                    new DefaultAllocator(true, 16),
//                    VideoPlayerConfig.MIN_BUFFER_DURATION,
//                    VideoPlayerConfig.MAX_BUFFER_DURATION,
//                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
//                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);
//
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//            TrackSelection.Factory videoTrackSelectionFactory =
//                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
//            TrackSelector trackSelector =
//                    new DefaultTrackSelector(videoTrackSelectionFactory);
//            // 2. Create the player
//            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultRenderersFactory(getContext()), trackSelector, loadControl);
//            fragmentRecipeStepDetailBinding.playerView.setPlayer(player);
//        }
//
//    }
//
//    private void buildMediaSource(Uri mUri) {
//        // Produces DataSource instances through which media data is loaded.
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
//                Util.getUserAgent(getContext(), getString(R.string.app_name)));
//        // This is the MediaSource representing the media to be played.
//        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(mUri);
//        // Prepare the player with the source.
//        player.prepare(videoSource);
//        player.setPlayWhenReady(true);
//
//    }
}
