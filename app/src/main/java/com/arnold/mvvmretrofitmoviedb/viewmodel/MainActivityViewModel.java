package com.arnold.mvvmretrofitmoviedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.arnold.mvvmretrofitmoviedb.model.MovieDataSource;
import com.arnold.mvvmretrofitmoviedb.model.MovieDataSourceFactory;
import com.arnold.mvvmretrofitmoviedb.model.MovieRepository;
import com.arnold.mvvmretrofitmoviedb.model.Result;
import com.arnold.mvvmretrofitmoviedb.service.MovieApiService;
import com.arnold.mvvmretrofitmoviedb.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<MovieDataSource> movieDataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<Result>> pagedListLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);

        MovieApiService movieApiService = RetrofitInstance.getService();
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(application,
                movieApiService);
        movieDataSourceLiveData = movieDataSourceFactory.getMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder().setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(3)
                .build();
        executor = Executors.newCachedThreadPool();

        pagedListLiveData = new LivePagedListBuilder<Long, Result>(movieDataSourceFactory,
                config)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<List<Result>> getAllMovieData() {

        return movieRepository.getMutableLiveData();
    }

    public LiveData<PagedList<Result>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
