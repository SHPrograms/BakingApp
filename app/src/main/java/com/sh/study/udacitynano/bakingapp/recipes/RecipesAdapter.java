package com.sh.study.udacitynano.bakingapp.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter used to show list of recipes in Recycler View in Recipe activity.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-22
 */
class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {
    private List<Recipe> recipes;

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        this.notifyDataSetChanged();
    }

    final private RecipesInterface clickHandler;

    RecipesAdapter(RecipesInterface mClickHandler) {
        this.clickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_recipe, parent, false);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder holder, int position) {
        holder.recipeName.setText(String.valueOf(recipes.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        else return recipes.size();
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name)
        Button recipeName;

        @BindView(R.id.recipe_steps)
        ImageButton recipeSteps;

        private static final int INGREDIENTS = 1;
        private static final int STEPS = 2;

        RecipesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recipeName.setOnClickListener((View v) -> {Clicked(INGREDIENTS);});
            recipeSteps.setOnClickListener((View v) -> {Clicked(STEPS);});
        }

        /**
         * Single recipe was clicked so we need:
         * - show ingredients or steps for recipe send as parameter
         *
         * @param source Ingredients or steps view
         */
        private void Clicked(int source) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = recipes.get(adapterPosition);
            if (source == INGREDIENTS) clickHandler.onClickIngedient(recipe);
            else if (source == STEPS) clickHandler.onClickStep(recipe);
        }
    }
}
