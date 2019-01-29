package br.com.udacity.ruyano.recipes.views.recipes.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import br.com.udacity.ruyano.recipes.BR;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.viewmodels.RecipesViewModel;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private RecipesViewModel viewModel;
    private int selectedPos = RecyclerView.NO_POSITION;

    public RecipesAdapter(RecipesViewModel viewModel) {
        this.viewModel = viewModel;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(viewModel, position);

    }

    @Override
    public int getItemCount() {
        if (viewModel.getRecipesLiveData() == null
                || viewModel.getRecipesLiveData().getValue() == null)
            return 0;
        return Objects.requireNonNull(viewModel.getRecipesLiveData().getValue()).size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.recipe_list_item;

    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(RecipesViewModel viewModel, Integer position) {
            binding.setVariable(BR.position, position);
            binding.setVariable(BR.model, viewModel);
            binding.setVariable(BR.isSelected, position == selectedPos);
            binding.executePendingBindings();

        }
    }
}
