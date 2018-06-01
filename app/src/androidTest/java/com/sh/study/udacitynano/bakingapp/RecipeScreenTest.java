package com.sh.study.udacitynano.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.sh.study.udacitynano.bakingapp.recipes.RecipesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

/**
 * Espresso Tests
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-31
 */
@RunWith(AndroidJUnit4.class)
public class RecipeScreenTest {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void checkRecyclerView() {
        onView(withId(R.id.recipes_list_rv)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipeItem_CheckName() {
        onView(withId(R.id.recipes_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.recipe_name)));

        String name = String.valueOf(mActivityTestRule.getActivity().getTitle());

        assertEquals(name, "Brownies");

        onView(withId(R.id.recipes_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.recipe_name)));

        String name2 = String.valueOf(mActivityTestRule.getActivity().getTitle());

        assertNotSame(name, name2);
    }

    @Test
    public void clickRecipeItem_CheckIngredients() {
        onView(withId(R.id.recipes_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, MyViewAction.clickChildViewWithId(R.id.recipe_name)));

        onView(withId(R.id.ingredients_servings_tv))
                .check(matches(withText("Recipe for 8 servings")));
    }

    @Test
    public void clickRecipeItem_CheckSteps() {
        onView(withId(R.id.recipes_list_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, MyViewAction.clickChildViewWithId(R.id.recipe_steps)));

        onView(withId(R.id.steps_rv))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }
}
