package com.a.b.moviesappstudio.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a.b.moviesappstudio.Activities.DetailActivity2;
import com.a.b.moviesappstudio.Domain.GenresItem;
import com.a.b.moviesappstudio.Domain.ListFilm;
import com.a.b.moviesappstudio.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.viewHolder> {
ArrayList <GenresItem> items;
Context context;



    public CategoryListAdapter(ArrayList<GenresItem> items) {
        this.items = items;

    }

    @NonNull

    @Override
    public CategoryListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      context=parent.getContext();
      View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
      return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.viewHolder holder, int position) {
  holder.titleTxt.setText(items.get(position).getName());
        RequestOptions requestOptions=new RequestOptions();
       /* requestOptions=requestOptions.transform(new CenterCrop(),new RoundedCorners(30));

        Glide.with(context)
                .load(items.getData().get(position).getPoster())
                .apply(requestOptions)
                .into(holder.pic);
        Glide.with(context)
                .load(items.getData().get(position).getPoster())
                .override(500, 700)

                .into(holder.pic);*/

        holder.itemView.setOnClickListener(v -> {
            /*  Intent intent = new Intent(holder.itemView.getContext(), DetailActivity2.class);
            intent.putExtra("id", items.getData().get(position).getId());
            context.startActivity(intent);*/


        });


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
