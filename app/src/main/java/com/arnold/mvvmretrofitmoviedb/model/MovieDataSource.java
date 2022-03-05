package com.arnold.mvvmretrofitmoviedb.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.arnold.mvvmretrofitmoviedb.R;
import com.arnold.mvvmretrofitmoviedb.service.MovieApiService;
import com.arnold.mvvmretrofitmoviedb.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Result> {

    private MovieApiService movieApiService;
    private Application application;

    public MovieDataSource(MovieApiService movieApiService, Application application) {
        this.movieApiService = movieApiService;
        this.application = application;
    }

    //for loading pages
    @Override
    public void loadAfter(@NonNull LoadParams<Long> loadParams,
                          @NonNull final LoadCallback<Long, Result> loadCallback) {
        movieApiService = RetrofitInstance.getService();
        Call<MovieApiResponse> call = movieApiService.getPopularMoviesWithPaging(
                application.getApplicationContext().getString(R.string.api_key),
                loadParams.key
        );
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                MovieApiResponse movieApiResponse = response.body();
                ArrayList<Result> results = new ArrayList<>();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<Result>) movieApiResponse.getResults();
                    loadCallback.onResult(results, loadParams.key + 1);
                }

            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> loadParams,
                           @NonNull LoadCallback<Long, Result> loadCallback) {

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> loadInitialParams,
                            @NonNull LoadInitialCallback<Long, Result> loadInitialCallback) {
        movieApiService = RetrofitInstance.getService();
        Call<MovieApiResponse> call = movieApiService.getPopularMoviesWithPaging(
                application.getApplicationContext().getString(R.string.api_key), 1
        );
        call.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                MovieApiResponse movieApiResponse = response.body();
                ArrayList<Result> results = new ArrayList<>();
                if (movieApiResponse != null && movieApiResponse.getResults() != null) {
                    results = (ArrayList<Result>) movieApiResponse.getResults();
                    loadInitialCallback.onResult(results, null, (long) 2);
                }
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {

            }
        });
    }
}
