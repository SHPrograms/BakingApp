package com.sh.study.udacitynano.bakingapp.recipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;
import com.sh.study.udacitynano.bakingapp.network.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RecipesViewModel extends ViewModel {
    private static final String CLASS_NAME = "RecipesViewModel";
    private static final String RECIPES_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private MutableLiveData<Recipe> selectedRecipe = new MutableLiveData<>();

    public RecipesViewModel() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        setRecipes();
    }

    public void setRecipe(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "setRecipe");
        selectedRecipe.setValue(recipe);
    }

    public LiveData<Recipe> getRecipe() {
        SHDebug.debugTag(CLASS_NAME, "getRecipe");
        return selectedRecipe;
    }

    public LiveData<List<Recipe>> getRecipes() {
        SHDebug.debugTag(CLASS_NAME, "getRecipes");
        if (recipes != null) return recipes;
        else {
            setRecipes();
            return recipes;
        }
    }

    private void setRecipes() {
        SHDebug.debugTag(CLASS_NAME, "setRecipes");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RECIPES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        NetworkService service = retrofit.create(NetworkService.class);

        Call<List<Recipe>> reviewResultCallback = service.getRecipes();

        reviewResultCallback.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                SHDebug.debugTag(CLASS_NAME, "onResponse");
                recipes.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                SHDebug.errorTag(CLASS_NAME, "onFailure: " + t.getMessage());
            }
        });
    }
}
