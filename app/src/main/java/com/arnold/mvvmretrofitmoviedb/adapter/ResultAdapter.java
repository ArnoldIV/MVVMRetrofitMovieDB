package com.arnold.mvvmretrofitmoviedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.arnold.mvvmretrofitmoviedb.R;
import com.arnold.mvvmretrofitmoviedb.databinding.ResultListItemBinding;
import com.arnold.mvvmretrofitmoviedb.model.Result;
import com.arnold.mvvmretrofitmoviedb.view.MovieDetails;


public class ResultAdapter
        extends PagedListAdapter<Result, ResultAdapter.ResultViewHolder> {

    private Context context;

    public ResultAdapter(Context context) {
        super(Result.CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                               int viewType) {

        ResultListItemBinding resultListItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.result_list_item, parent, false);

        return new ResultViewHolder(resultListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        Result result = getItem(position);
        holder.resultListItemBinding.setResult(result);


    }


    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private ResultListItemBinding resultListItemBinding;

        //implementation of redirecting to movies details
        public ResultViewHolder(@NonNull ResultListItemBinding resultListItemBinding) {
            super(resultListItemBinding.getRoot());
            this.resultListItemBinding = resultListItemBinding;

            resultListItemBinding.getRoot()
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int position = getBindingAdapterPosition();

                            if (position != RecyclerView.NO_POSITION) {

                                Result result = getItem(position);
                                Intent intent = new Intent(context,
                                        MovieDetails.class);
                                intent.putExtra("movieData", result);
                                context.startActivity(intent);


                            }

                        }
                    });
        }
    }
}