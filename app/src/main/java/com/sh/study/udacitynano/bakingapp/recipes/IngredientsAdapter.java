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

/**
 * Adapter used to show list of ingredients in Recycler View in Recipe activity.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-27
 */
class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientAdapterViewHolder> {
    private List<Ingredient> ingredients;

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_inredient, parent, false);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapterViewHolder holder, int position) {
        //TODO: Add Quantity and measure to ingredient name
        holder.ingredientName.setText(String.valueOf(ingredients.get(position).getIngredient()));
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) return 0;
        else return ingredients.size();
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_details_name)
        TextView ingredientName;

        IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
