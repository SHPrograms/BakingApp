package com.sh.study.udacitynano.bakingapp.recipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.google.gson.GsonBuilder;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Ingredient;
import com.sh.study.udacitynano.bakingapp.model.Recipe;
import com.sh.study.udacitynano.bakingapp.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RecipesViewModel extends ViewModel {
    //TODO: More OOP
    private static final String RECIPES_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private final MutableLiveData<List<Recipe>> mRecipes = new MutableLiveData<>();
    private final MutableLiveData<Recipe> selected = new MutableLiveData<>();

    public void selectRecipe(Recipe recipe) {
        selected.setValue(recipe);
    }

    public LiveData<Recipe> getIngredients() {
        return selected;
    }

    public LiveData<List<Recipe>> getRecipes() {
//        final MutableLiveData<List<Recipe>> data = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RECIPES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        NetworkService service = retrofit.create(NetworkService.class);

        Call<List<Recipe>> reviewResultCallback = service.getRecipes();
        reviewResultCallback.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                mRecipes.setValue(response.body());
//                data.setValue(response.body());
                SHDebug.debugTag("RecipesViewModel - OK: ", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                SHDebug.debugTag("RecipesViewModel - Error: ", t.toString());
            }
        });
//        mRecipes = data;
//        return data;
        return mRecipes;
    }
}
