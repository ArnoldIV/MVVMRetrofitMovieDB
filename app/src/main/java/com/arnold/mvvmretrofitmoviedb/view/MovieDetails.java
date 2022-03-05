package com.arnold.mvvmretrofitmoviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arnold.mvvmretrofitmoviedb.R;
import com.arnold.mvvmretrofitmoviedb.databinding.ActivityMovieDetailsBinding;
import com.arnold.mvvmretrofitmoviedb.model.Result;
import com.bumptech.glide.Glide;

public class MovieDetails extends AppCompatActivity {

    private Result result;
    private ActivityMovieDetailsBinding movieDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        //setting content view for movieDetailsBinding
        movieDetailsBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_movie_details);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("movieData")) {
            result = intent.getParcelableExtra("movieData");

            movieDetailsBinding.setResult(result);


        }
    }
}