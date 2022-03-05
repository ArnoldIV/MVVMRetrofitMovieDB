package com.arnold.mvvmretrofitmoviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;

import com.arnold.mvvmretrofitmoviedb.R;
import com.arnold.mvvmretrofitmoviedb.adapter.ResultAdapter;
import com.arnold.mvvmretrofitmoviedb.databinding.ActivityMainBinding;
import com.arnold.mvvmretrofitmoviedb.model.MovieApiResponse;
import com.arnold.mvvmretrofitmoviedb.model.Result;
import com.arnold.mvvmretrofitmoviedb.service.MovieApiService;
import com.arnold.mvvmretrofitmoviedb.service.RetrofitInstance;
import com.arnold.mvvmretrofitmoviedb.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private PagedList<Result> results;

    private RecyclerView recyclerView;
    private ResultAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

        getPopularMovies();
        //implementation of swipe refresh layout
        refreshLayout = activityMainBinding.swiperefresh;
        refreshLayout.setColorSchemeResources(R.color.design_default_color_primary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();

            }
        });
    }

    //this method get popular movies with page and fill them in recycler view
    public void getPopularMovies() {
        mainActivityViewModel.getPagedListLiveData()
                .observe(this, new Observer<PagedList<Result>>() {
                    @Override
                    public void onChanged(PagedList<Result> resultList) {
                        results = resultList;
                        fillRecyclerView();

                    }
                });

    }

    //method for filling movies in recycler view
    private void fillRecyclerView() {
        recyclerView = activityMainBinding.recyclerView;
        adapter = new ResultAdapter(this);
        adapter.submitList(results);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }
}