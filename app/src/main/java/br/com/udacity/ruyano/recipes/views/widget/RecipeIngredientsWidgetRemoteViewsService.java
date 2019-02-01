package br.com.udacity.ruyano.recipes.views.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeIngredientsWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeIngredientsWidgetRemoteFactory(getApplicationContext(), intent);

    }

}
