package com.sh.study.udacitynano.bakingapp.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;

import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {
    private static final String CLASS_NAME = "StepsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        SHDebug.debugTag(CLASS_NAME, "onCreate:End");


        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("StepsForSingleRecipe",
                    getIntent().getParcelableExtra("StepsForSingleRecipe"));

            StepsFragment fragment = new StepsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_steps_fragment, fragment)
                    .commit();
        }
    }
}