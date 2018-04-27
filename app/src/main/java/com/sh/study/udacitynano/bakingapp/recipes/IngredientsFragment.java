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
import android.widget.LinearLayout;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IngredientsFragment extends Fragment {

    @BindView(R.id.single_recipe_rv)
    RecyclerView mSingleRecipeRecyclerView;

    private Unbinder mUnbinder;
    private RecipesViewModel mViewModel;
    private SingleRecipeAdapter mSingleRecipeAdapter;

    public IngredientsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mSingleRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSingleRecipeAdapter = new SingleRecipeAdapter();
        mSingleRecipeRecyclerView.setAdapter(mSingleRecipeAdapter);

        mViewModel.getIngredients().observe(this, recipe -> {
            mSingleRecipeAdapter.setIngredients(recipe.getIngredients());
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
