package com.sh.study.udacitynano.bakingapp.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipesFragment extends Fragment implements RecipesAdapter.RecipesAdapterOnClickHandler {
    @BindView(R.id.recipes_list_rv) RecyclerView recipesRecyclerView;

    private static final String CLASS_NAME = "RecipesFragment";
    private Unbinder unbinder;
    private RecipesViewModel recipesViewModel;
    private RecipesAdapter recipesAdapter;

    OnRecipeClickListener callback;

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position);
    }

    public RecipesFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate");
        recipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
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
        recipesViewModel.getRecipes().observe(this, recipes -> {
            recipesAdapter.setRecipes(recipes);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void onClick(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "onClick");
        recipesViewModel.setRecipe(recipe);
    }
}
