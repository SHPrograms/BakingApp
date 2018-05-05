package com.sh.study.udacitynano.bakingapp.network;

import com.google.gson.GsonBuilder;
import com.sh.study.udacitynano.bakingapp.constants.SHDebug;
import com.sh.study.udacitynano.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Utilities to communicate with the Google site for JSON object used for this project.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-05-04
 */
public final class NetworkConnection {
    private static final String CLASS_NAME = "NetworkConnection";
    private static final String RECIPES_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public Call<List<Recipe>> downloadRecipes() {
        SHDebug.debugTag(CLASS_NAME, "downloadRecipes");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RECIPES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        NetworkInterface service = retrofit.create(NetworkInterface.class);
        return service.getRecipes();
    }
}

