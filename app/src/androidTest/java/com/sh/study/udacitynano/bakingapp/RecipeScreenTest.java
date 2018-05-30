package com.sh.study.udacitynano.bakingapp;

import android.support.test.runner.AndroidJUnit4;

import com.sh.study.udacitynano.bakingapp.recipes.RecipesActivity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class RecipeScreenTest {
    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void clickRecipeItem_OpensOrderActivity() {
        onData(anything()).inAdapterView(withId(R.id.recipes_list_rv)).atPosition(1).perform(click());

        // Checks that the OrderActivity opens with the correct tea name displayed
//        onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)));


    }

}
