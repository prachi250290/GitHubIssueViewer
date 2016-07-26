package com.test.githubissueviewer.myapplication;

import android.graphics.Movie;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by prachi on 25/07/16.
 */
public interface ApiInterface {

    @GET("/repos/rails/rails/issues")
    Call<List<Issue>> getIssues(@QueryMap Map<String, String> options);
}
