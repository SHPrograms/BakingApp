package com.sh.study.udacitynano.bakingapp.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

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

    final private RecipesAdapterOnClickHandler clickHandler;

    /**
     * Single recipe was clicked
     */
    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe, View v);
    }

    public RecipesAdapter(RecipesAdapterOnClickHandler mClickHandler) {
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

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        @BindView(R.id.recipe_name)
        Button recipeName;

        @BindView(R.id.recipe_steps)
        ImageButton recipeSteps;

        RecipesAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            recipeName.setOnClickListener(this);
            recipeSteps.setOnClickListener(this);
        }

        /**
         * Single recipe was clicked so we need:
         * - show ingredients or steps for recipe send as parameter

         * @see OnClickListener
         * @param v clicked view
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = recipes.get(adapterPosition);
            // TODO: change second parameter to static int value ingredient or step and...
            // implement situation when both are visible (in the future for tablets)
            clickHandler.onClick(recipe, v);
        }
    }
}
