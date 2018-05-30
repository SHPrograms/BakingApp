package com.sh.study.udacitynano.bakingapp.recipes;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.sh.study.udacitynano.bakingapp.model.Recipe;
import com.sh.study.udacitynano.bakingapp.widget.RecipeWidgetProvider;

/**
 * Collected preferences for Recipes activity
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-05
 */
public final class RecipesPreferences {
    private RecipesPreferences() {
        throw new AssertionError();
    }

    static final String RECIPE = "recipe";

    public static Recipe getRecipePreferences(Context context) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(RECIPE, "");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        return recipe;
    }

    static void setRecipePreferences(Context context, Recipe recipe, Application application) {
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(RECIPE, json)
                .apply();

        Intent intent = new Intent(context, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(application)
                .getAppWidgetIds(new ComponentName(application, RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        application.sendBroadcast(intent);
    }
}
