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
import android.widget.LinearLayout;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;
import com.sh.study.udacitynano.bakingapp.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipesFragment extends Fragment implements RecipesAdapter.RecipesAdapterOnClickHandler {
    @BindView(R.id.recipes_list_rv)
    RecyclerView recipesRecyclerView;

    private static final String CLASS_NAME = "RecipesFragment";
    private Unbinder unbinder;
    private RecipesViewModel recipesViewModel;
    private RecipesAdapter recipesAdapter;

    OnRecipeClickListener recipeClickListener;

    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }

    public RecipesFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            recipeClickListener = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }

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

//        recipesViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        recipesViewModel.getRecipes().observe(this, recipes -> recipesAdapter.setRecipes(recipes));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void onClick(Recipe recipe, View v) {
        SHDebug.debugTag(CLASS_NAME, "onClick");
//        recipesViewModel.setRecipe(recipe);
        if (v instanceof Button) {
            // List of ingredients
            recipesViewModel.setRecipe(recipe);
            recipeClickListener.onRecipeSelected(recipe);
        } else if (v instanceof ImageButton) {
            // Steps
            Intent intent = new Intent(this.getContext(), StepsActivity.class);
            ArrayList<Step> listOfsteps = new ArrayList<>(recipe.getSteps().size());
            listOfsteps.addAll(recipe.getSteps());
            intent.putParcelableArrayListExtra("sfsr", listOfsteps);
            startActivity(intent);
        }
    }
}
