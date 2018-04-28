package com.sh.study.udacitynano.bakingapp.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.model.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class SingleRecipeAdapter extends RecyclerView.Adapter<SingleRecipeAdapter.SingleRecipeAdapterViewHolder> {
    private List<Ingredient> ingredients;

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SingleRecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_recipe_details, parent, false);
        return new SingleRecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleRecipeAdapterViewHolder holder, int position) {
        holder.ingredientName.setText(String.valueOf(ingredients.get(position).getIngredient()));
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) return 0;
        else return ingredients.size();
    }

    public class SingleRecipeAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_details_name) TextView ingredientName;

        public SingleRecipeAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
