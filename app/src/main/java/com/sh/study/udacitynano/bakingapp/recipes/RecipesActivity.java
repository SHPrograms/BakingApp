package com.sh.study.udacitynano.bakingapp.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sh.study.udacitynano.bakingapp.R;

import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);


/*
                <ImageView
        android:id="@+id/welcome_iv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:adjustViewBounds="true"
        android:contentDescription="@string/welcome_iv"
        android:scaleType="centerCrop"
        android:src="@drawable/healthy_recipe_tablet"/>

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recipes_list_rv"
        android:layout_width="400dp"
        android:layout_height="match_parent" />
*/

    }
}
