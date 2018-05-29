package com.sh.study.udacitynano.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.model.Ingredient;
import com.sh.study.udacitynano.bakingapp.recipes.RecipesPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality - service for Ingredients in ListView.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-29
 */
class IngredientsListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    List<String> mIngredients;

    IngredientsListViewRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
        mIngredients = new ArrayList();
        loadIngredients();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        loadIngredients();
    }

    @Override
    public void onDestroy() {
        }

    @Override
    public int getCount() {
        if (mIngredients != null) return mIngredients.size();
        else return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null) return null;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list_item);
        views.setTextViewText(R.id.widget_ingredient_tv, mIngredients.get(position));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void loadIngredients() {
        if (RecipesPreferences.getRecipePreferences(mContext) != null) {
            mIngredients.clear();
            for (Ingredient ingredientItem : RecipesPreferences.getRecipePreferences(mContext).getIngredients()) {
                mIngredients.add(String.valueOf(ingredientItem.getQuantity()) +
                        " " +
                        String.valueOf(ingredientItem.getMeasure()) +
                        " " +
                        ingredientItem.getIngredient());
            }
        } else {
            mIngredients = null;
        }
    }
}
