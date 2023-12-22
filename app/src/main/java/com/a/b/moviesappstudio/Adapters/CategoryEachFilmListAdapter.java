package com.a.b.moviesappstudio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a.b.moviesappstudio.Domain.GenresItem;
import com.a.b.moviesappstudio.R;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CategoryEachFilmListAdapter extends RecyclerView.Adapter<CategoryEachFilmListAdapter.viewHolder> {
List<String> items;
Context context;




    public CategoryEachFilmListAdapter(List<String> items) {
        this.items = items;

    }

    @NonNull

    @Override
    public CategoryEachFilmListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      context=parent.getContext();
      View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
      return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryEachFilmListAdapter.viewHolder holder, int position) {
  holder.titleTxt.setText(items.get(position));

       /* requestOptions=requestOptions.transform(new CenterCrop(),new RoundedCorners(30));

        Glide.with(context)
                .load(items.getData().get(position).getPoster())
                .apply(requestOptions)
                .into(holder.pic);
        Glide.with(context)
                .load(items.getData().get(position).getPoster())
                .override(500, 700)

                .into(holder.pic);*/




    }

    @Override
    public int getItemCount() {
       return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.Titletxt);


        }
    }
}
