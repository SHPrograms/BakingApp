package com.sh.study.udacitynano.bakingapp.recipes;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.Constants;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;
import com.sh.study.udacitynano.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment used to show list of recipes in Recycler View in Recipe activity.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-22
 */
public class RecipesFragment extends Fragment implements RecipesInterface {
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recipes_list_rv)
    RecyclerView recipesRecyclerView;

    private static final String CLASS_NAME = "RecipesFragment";
    private Unbinder unbinder;
    private RecipesViewModel recipesViewModel;
    private RecipesAdapter recipesAdapter;

    private RecipesInterface recipeClickListener;

    public RecipesFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RecipesInterface) {
            recipeClickListener = (RecipesInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RecipesInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recipeClickListener = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate");
        try {
            recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SHDebug.debugTag(CLASS_NAME, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        unbinder = ButterKnife.bind(this, view);

        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipesAdapter = new RecipesAdapter(this);
        recipesRecyclerView.setAdapter(recipesAdapter);

        recipesViewModel.getRecipes().observe(this, recipes -> recipesAdapter.setRecipes(recipes));

        if (RecipesPreferences.getRecipePreferences(getActivity()) != null)
            recipesViewModel.setRecipe(RecipesPreferences.getRecipePreferences(getActivity()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        unbinder.unbind();
    }

    /**
     * Ingredient Button for recipe was clicked.
     * Implement {@link RecipesInterface}
     *
     * @param recipe Clicked recipe
     */
    @Override
    public void onClickIngedient(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "onClickIngedient");
        recipesViewModel.setRecipe(recipe);
        recipeClickListener.onClickIngedient(recipe);
    }

    /**
     * Step Button for recipe was clicked.
     * Implement {@link RecipesInterface}
     *
     * @param recipe Clicked recipe
     */
    @Override
    public void onClickStep(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "onClickStep");
        recipesViewModel.setRecipe(recipe);
        recipeClickListener.onClickStep(recipe);
        Intent intent = new Intent(this.getContext(), StepsActivity.class);
        ArrayList<Step> listOfsteps = new ArrayList<>(recipe.getSteps().size());
        listOfsteps.addAll(recipe.getSteps());
        intent.putParcelableArrayListExtra(Constants.RECIPE_STEPS, listOfsteps);
        intent.putExtra(Constants.RECIPE_NAME, recipe.getName());
        startActivity(intent);
    }
}
