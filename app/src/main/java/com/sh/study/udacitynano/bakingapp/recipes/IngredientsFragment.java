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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IngredientsFragment extends Fragment {
    @BindView(R.id.single_recipe_rv) RecyclerView singleRecipeRecyclerView;

    private static final String CLASS_NAME = "IngredientsFragment";
    private Unbinder unbinder;
    private RecipesViewModel recipesViewModel;
    private SingleRecipeAdapter singleRecipeAdapter;

    public IngredientsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, view);

        singleRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        singleRecipeAdapter = new SingleRecipeAdapter();
        singleRecipeRecyclerView.setAdapter(singleRecipeAdapter);

        recipesViewModel.getRecipe().observe(this, recipe -> {
            singleRecipeAdapter.setIngredients(recipe.getIngredients());
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        unbinder.unbind();
    }
}
