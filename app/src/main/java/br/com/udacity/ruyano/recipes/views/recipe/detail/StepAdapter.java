package br.com.udacity.ruyano.recipes.views.recipe.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import br.com.udacity.ruyano.recipes.BR;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.viewmodels.RecipeDetailsViewModel;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private RecipeDetailsViewModel viewModel;

    public StepAdapter(RecipeDetailsViewModel viewModel) {
        this.viewModel = viewModel;

    }

    @NonNull
    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new StepAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.ViewHolder holder, int position) {
        holder.bind(viewModel, position);

    }

    @Override
    public int getItemCount() {
        if (viewModel.getRecipeMutableLiveData() == null
                || viewModel.getRecipeMutableLiveData().getValue() == null
                || viewModel.getRecipeMutableLiveData().getValue().getSteps() == null)
            return 0;
        return viewModel.getRecipeMutableLiveData().getValue().getSteps().size();

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.step_list_item;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(RecipeDetailsViewModel viewModel, Integer position) {
            binding.setVariable(BR.position, position);
            binding.setVariable(BR.model, viewModel);
            binding.executePendingBindings();

        }
    }
}
