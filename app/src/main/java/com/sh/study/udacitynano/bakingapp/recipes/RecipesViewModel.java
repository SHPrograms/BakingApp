package com.sh.study.udacitynano.bakingapp.recipes;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.GsonBuilder;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;
import com.sh.study.udacitynano.bakingapp.network.NetworkConnection;
import com.sh.study.udacitynano.bakingapp.network.NetworkInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fetching data from JSON
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-22
 */
class RecipesViewModel extends ViewModel {
    private static final String CLASS_NAME = "RecipesViewModel";

    private final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private final MutableLiveData<Recipe> selectedRecipe = new MutableLiveData<>();

    public RecipesViewModel() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        setRecipes();
    }

    /**
     * Set chosen recipe
     *
     * @param recipe {@link Recipe}
     */
    public void setRecipe(Recipe recipe) {
        SHDebug.debugTag(CLASS_NAME, "setRecipe");
        selectedRecipe.setValue(recipe);
    }

    /**
     * Get chosen recipe
     *
     * @return {@link Recipe}
     */
    public LiveData<Recipe> getRecipe() {
        SHDebug.debugTag(CLASS_NAME, "getRecipe");
        return selectedRecipe;
    }

    /**
     * Get all recipes. Fetch if not exist.
     *
     * @return {@link List} of {@link Recipe}
     */
    public LiveData<List<Recipe>> getRecipes() {
        SHDebug.debugTag(CLASS_NAME, "getRecipes");
        if (recipes == null)
            setRecipes();
        return recipes;
    }

    /**
     * Download JSON data from internet
     */
    private void setRecipes() {
        SHDebug.debugTag(CLASS_NAME, "setRecipes");
        NetworkConnection networkConnection = new NetworkConnection();
        networkConnection.downloadRecipes().enqueue(new Callback<List<Recipe>>() {
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
