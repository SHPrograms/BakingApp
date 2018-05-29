package com.sh.study.udacitynano.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Implementation of App Widget functionality - service for Ingredients in ListView.
  *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-29
 */
public class IngredientsViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListViewRemoteViewsFactory(this.getApplicationContext());
    }
}
