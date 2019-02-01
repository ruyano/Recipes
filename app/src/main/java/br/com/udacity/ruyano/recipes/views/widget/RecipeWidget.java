package br.com.udacity.ruyano.recipes.views.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.utils.RecipeWidgetUtil;
import br.com.udacity.ruyano.recipes.views.recipe.details.RecipeDetailsActivity;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    public static Integer randomNumber = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

        Recipe recipe = RecipeWidgetUtil.getRecipeObject(context);

        if (recipe != null) {
            views.setTextViewText(R.id.recipe_name_widget_textview, recipe.getName());

            randomNumber = (int)(Math.random()*1000);

            Intent svcIntent=new Intent(context, RecipeIngredientsWidgetRemoteViewsService.class);
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId + randomNumber);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.recipe_ingredients_widget_listView, svcIntent);

        } else {
            CharSequence widgetText = context.getString(R.string.appwidget_text);
            views.setTextViewText(R.id.recipe_name_widget_textview, widgetText);
        }

        PendingIntent pendingIntent = getPendingIntent(context, recipe);
        if (pendingIntent != null)
            views.setOnClickPendingIntent(R.id.recipe_name_widget_textview, pendingIntent);

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

    private static PendingIntent getPendingIntent(Context context, Recipe recipe) {
        if (recipe != null) {
            Intent intent = RecipeDetailsActivity.getIntent(context, recipe);
            return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return null;
    }

}

