package com.sh.study.udacitynano.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.recipes.RecipesPreferences;

/**
 * Implementation of App Widget functionality.
 * Used Android studio widget template.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-29
 */
public class RecipeWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        if (RecipesPreferences.getRecipePreferences(context) == null) {
            views.setTextViewText(R.id.widget_title, "The recipe hasn't been selected.");
            views.setViewVisibility(R.id.widget_lv, View.GONE);
        } else {
            views.setTextViewText(R.id.widget_title, "Ingredients for "+ RecipesPreferences.getRecipePreferences(context).getName());
            views.setViewVisibility(R.id.widget_lv, View.VISIBLE);

            Intent intent = new Intent(context, IngredientsViewsService.class);
            views.setRemoteAdapter(R.id.widget_lv, intent);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

