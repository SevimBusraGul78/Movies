package com.a.b.moviesappstudio.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a.b.moviesappstudio.Adapters.ActorsListAdapter;
import com.a.b.moviesappstudio.Adapters.CategoryEachFilmListAdapter;

import com.a.b.moviesappstudio.Domain.FilmItem;
import com.a.b.moviesappstudio.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class DetailActivity2 extends AppCompatActivity {
    private RequestQueue mRequesQueue;
    private StringRequest mStringRequest;
    private int idFilm;

    private ProgressBar progressBar;

    private TextView titleTxt,MoiveRateTxt,movieTimeTxt,MovieSummaryInfo,movieActors;
    private ImageView pic2,backImg;
    private RecyclerView.Adapter adapterActorList,adapterCategory;
    private RecyclerView recyclerViewActors,recyclerViewCategory;
 private NestedScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);
        idFilm=getIntent().getIntExtra("id",0);
        initView();
        sendRequest();

    }

    private void sendRequest() {
        mRequesQueue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies/" + idFilm, response -> {
            Gson gson=new Gson();
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            FilmItem item=gson.fromJson(response, FilmItem.class);

            Glide.with(DetailActivity2.this)
                    .load(item.getPoster())
                    .into(pic2);

            titleTxt.setText(item.getTitle());
            MoiveRateTxt.setText(item.getImdbRating());
         //   movieTimeTxt.setText(item.getRuntime());
            MovieSummaryInfo.setText(item.getPlot());
            movieActors.setText(item.getActors());
            if(item.getImages()!=null){
               adapterActorList=new ActorsListAdapter(item.getImages());
               recyclerViewActors.setAdapter(adapterActorList);


            }
            if(item.getGenres()!=null){
             adapterCategory=new CategoryEachFilmListAdapter(item.getGenres());
             recyclerViewCategory.setAdapter(adapterCategory);

            }

        }, error -> progressBar.setVisibility(View.GONE));
        mRequesQueue.add(mStringRequest);

    }

    private void initView() {
        titleTxt=findViewById(R.id.movieNameTxt);
        progressBar=findViewById(R.id.progressBarDetail);
        scrollView=findViewById(R.id.scrollView);
        pic2=findViewById(R.id.picDetail);
        MoiveRateTxt=findViewById(R.id.textView2);
        MovieSummaryInfo=findViewById(R.id.movieSummart);
        movieActors=findViewById(R.id.movieActories);
        backImg=findViewById(R.id.imageView4);
        recyclerViewCategory=findViewById(R.id.genreView);
       recyclerViewActors=findViewById(R.id.imagesRecycler);
       recyclerViewActors.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        backImg.setOnClickListener(v -> finish());

    }
}