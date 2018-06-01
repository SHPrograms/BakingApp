package com.sh.study.udacitynano.bakingapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Author unknown. Borrowed from
 * - {@see http://blogs.quovantis.com/how-to-use-espresso-for-android-ui-testing/}
 * - {@see https://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item}
 */
public class MyViewAction {
    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}
