package com.arnold.mvvmretrofitmoviedb.service;

import com.arnold.mvvmretrofitmoviedb.model.MovieApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {
    //method to get popular movies
    @GET("movie/popular")
    Call<MovieApiResponse> getPopularMovies(@Query("api_key") String apiKey);

    //method to get popular movies with paging
    @GET("movie/popular")
    Call<MovieApiResponse> getPopularMoviesWithPaging(
            @Query("api_key") String apiKey, @Query("page") long page);
}
