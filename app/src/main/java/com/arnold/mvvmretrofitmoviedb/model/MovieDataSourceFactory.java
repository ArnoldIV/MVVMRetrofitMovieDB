package com.arnold.mvvmretrofitmoviedb.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.arnold.mvvmretrofitmoviedb.service.MovieApiService;

public class MovieDataSourceFactory extends DataSource.Factory {

    private MovieDataSource movieDataSource;
    private Application application;
    private MovieApiService movieApiService;
    private MutableLiveData<MovieDataSource> mutableLiveData;

    public MovieDataSourceFactory(Application application, MovieApiService movieApiService) {
        this.application = application;
        this.movieApiService = movieApiService;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    //creating of dataSource
    public DataSource create() {
        movieDataSource = new MovieDataSource(movieApiService,application);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
