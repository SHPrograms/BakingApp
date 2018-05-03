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
import android.widget.ImageView;
import android.widget.TextView;

import com.sh.study.udacitynano.bakingapp.R;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class IngredientsFragment extends Fragment {
    @BindView(R.id.ingredients_rv)
    RecyclerView ingredientsRecyclerView;

    @BindView(R.id.ingredients_servings_tv)
    TextView ingredientsServingsTextView;

    @BindView(R.id.ingredients_recipe_iv)
    ImageView ingredientsRecipeImageView;

    private static final String CLASS_NAME = "IngredientsFragment";
    private Unbinder unbinder;
    private RecipesViewModel recipesViewModel;
    private IngredientsAdapter ingredientsAdapter;

    public IngredientsFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
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
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, view);

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientsAdapter = new IngredientsAdapter();
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        recipesViewModel.getRecipe().observe(this, recipe -> {
            try {
                ingredientsAdapter.setIngredients(recipe.getIngredients());
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }

            ingredientsServingsTextView.setText(getResources().getString(
                    R.string.ingredients_servings_tv_caption, recipe.getServings()));

            if ((recipe.getImage() != null) && (!recipe.getImage().isEmpty())) {
                Picasso.get()
                        .load(recipe.getImage())
                        .resize(50,50)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_50x50)
                        .error(R.drawable.placeholder_50x50)
                        .into(ingredientsRecipeImageView);
            } else ingredientsRecipeImageView.setVisibility(View.GONE);
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
