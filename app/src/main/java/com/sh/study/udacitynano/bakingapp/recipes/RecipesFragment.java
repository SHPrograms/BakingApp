package com.sh.study.udacitynano.bakingapp.recipes;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipesFragment extends Fragment implements RecipesAdapter.RecipesAdapterOnClickHandler {

    // TODO: How it hsould be with Recyclers and Butterknife?
//    @BindView(R.id.recipes_list_rv) RecyclerView mRecipesRecyclerView;
    private Unbinder mUnbinder;

    private List<Recipe> mRecipes;
    private RecipesViewModel mViewModel;
    private RecipesAdapter mRecipesAdapter;
    private RecyclerView mRecipesRecyclerView;

    public RecipesFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        mUnbinder = ButterKnife.bind(view);

        mRecipesRecyclerView = view.findViewById(R.id.recipes_list_rv);

        mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipesAdapter = new RecipesAdapter(this);
        mRecipesRecyclerView.setAdapter(mRecipesAdapter);

        mViewModel = ViewModelProviders.of(this).get(RecipesViewModel.class);
        mViewModel.getRecipes().observe(this, recipes -> {
            mRecipesAdapter.setRecipes(recipes);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(Recipe recipe) {

    }
}
