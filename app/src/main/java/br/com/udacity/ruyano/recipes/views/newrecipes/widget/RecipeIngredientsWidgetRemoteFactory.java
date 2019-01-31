package br.com.udacity.ruyano.recipes.views.newrecipes.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.utils.RecipeWidgetUtil;

public class RecipeIngredientsWidgetRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private int appWidgetId;

    public RecipeIngredientsWidgetRemoteFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID) - RecipeWidget.randomNumber;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return RecipeWidgetUtil.getRecipeObject(context) == null ? 0 : RecipeWidgetUtil.getRecipeObject(context).getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.ingredient_list_item);

        row.setTextViewText(R.id.ingredient_textview, RecipeWidgetUtil.getRecipeObject(context).getIngredients().get(position).toString());

        return(row);

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

}
