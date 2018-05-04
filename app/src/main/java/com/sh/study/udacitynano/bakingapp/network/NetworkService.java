package com.sh.study.udacitynano.bakingapp.network;

import com.sh.study.udacitynano.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Network calls to import JSON data about recipes
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-04-22
 */
public interface NetworkService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
