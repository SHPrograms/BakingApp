package com.sh.study.udacitynano.bakingapp.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.Constants;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;

import butterknife.ButterKnife;

/**
 * Steps activity used to show:
 * - lists of steps for smartphones
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-29
 */
public class StepsActivity extends AppCompatActivity {
    private static final String CLASS_NAME = "StepsActivity";
    public boolean twoPane = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        SHDebug.debugTag(CLASS_NAME, "onCreate:End");

        this.setTitle(RecipesPreferences.getRecipePreferences(this).getName());
        
        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(Constants.RECIPE_STEPS,
                    getIntent().getParcelableArrayListExtra(Constants.RECIPE_STEPS)
            );
            arguments.putBoolean(Constants.TWO_PANE, twoPane);
            twoPane = getIntent().getBooleanExtra(Constants.TWO_PANE, false);

            StepsFragment fragment = new StepsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_steps_fragment, fragment)
                    .commit();
        }
    }
}
