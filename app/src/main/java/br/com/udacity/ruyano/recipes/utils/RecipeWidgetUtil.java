package br.com.udacity.ruyano.recipes.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.views.widget.RecipeWidget;

public class RecipeWidgetUtil {

    private static final String RECIPE_WIDGET_DATA = "RECIPE_WIDGET_DATA";
    private static final String RECIPE_OBJECT = "RECIPE_OBJECT";

    public static void updateRecipeWidget(Context context, Recipe recipe) {
        SharedPreferences.Editor editor = context.getSharedPreferences(RECIPE_WIDGET_DATA, Context.MODE_PRIVATE).edit();
        editor.remove(RECIPE_OBJECT);
        editor.putString(RECIPE_OBJECT, new Gson().toJson(recipe));
        editor.apply();

        updateRecipeWidget(context);

    }

    public static Recipe getRecipeObject(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(RECIPE_WIDGET_DATA, Context.MODE_PRIVATE);
        String recipeString = prefs.getString(RECIPE_OBJECT, null);
        return recipeString == null ? null : new Gson().fromJson(recipeString, Recipe.class);

    }

    private static void updateRecipeWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);

    }

}
