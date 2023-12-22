package com.a.b.moviesappstudio.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.a.b.moviesappstudio.Adapters.CategoryListAdapter;
import com.a.b.moviesappstudio.Adapters.FilmListAdapter;
import com.a.b.moviesappstudio.Adapters.SliderAdapter;
import com.a.b.moviesappstudio.Domain.GenresItem;
import com.a.b.moviesappstudio.Domain.ListFilm;
import com.a.b.moviesappstudio.Domain.SliderItems;
import com.a.b.moviesappstudio.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView.Adapter adapterBestMovies,AdapterUpComing,adapterCategory;
    private RecyclerView recyclerViewBestMovies,recyclerviewUpcoming,recyclerviewCategory;
    private RequestQueue mRequestQueue;

    private StringRequest mStringRequest,mStringRequest2,mStringRequest3;
    private ProgressBar loading1,loading2,loading3;
    private ViewPager2 viewPager2;
    private Handler slideHandler=new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        banners();
        sendRequestBestMovies();
        sendRequestUpComming();
        sendRequestCategory();

    }

    private void sendRequestBestMovies() {
        mRequestQueue= Volley.newRequestQueue(this);
        loading1.setVisibility(View.VISIBLE);
        mStringRequest=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                loading1.setVisibility(View.GONE);
                ListFilm items=gson.fromJson(response,ListFilm.class);
                adapterBestMovies=new FilmListAdapter(items);
                recyclerViewBestMovies.setAdapter(adapterBestMovies);

            }
        }, error -> {
          loading1.setVisibility(View.GONE);
          Log.i("uilover","onerrorResponse"+error.toString());

        });
        mRequestQueue.add(mStringRequest);
    }
    private void sendRequestUpComming() {
        mRequestQueue= Volley.newRequestQueue(this);
        loading3.setVisibility(View.VISIBLE);
        mStringRequest3=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=2", response -> {
            Gson gson=new Gson();
            loading3.setVisibility(View.GONE);
            ListFilm items=gson.fromJson(response,ListFilm.class);
            AdapterUpComing=new FilmListAdapter(items);
            recyclerviewUpcoming.setAdapter(AdapterUpComing);

        }, error -> {
            loading3.setVisibility(View.GONE);
            Log.i("uilover","onerrorResponse"+error.toString());

        });
        mRequestQueue.add(mStringRequest3);
    }
    private void sendRequestCategory() {
        mRequestQueue= Volley.newRequestQueue(this);
        loading2.setVisibility(View.VISIBLE);
        mStringRequest2=new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/genres", response -> {
            Gson gson=new Gson();
            loading2.setVisibility(View.GONE);
           ArrayList<GenresItem> catList=gson.fromJson(response,new TypeToken<ArrayList<GenresItem>>(){

           }.getType());
            adapterCategory=new CategoryListAdapter(catList);
            recyclerviewCategory.setAdapter(adapterCategory);

        }, error -> {
            loading2.setVisibility(View.GONE);
            Log.i("uilover","onerrorResponse"+error.toString());

        });
        mRequestQueue.add(mStringRequest2);
    }


    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide));
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide3));


        viewPager2.setAdapter(new SliderAdapter(sliderItems,viewPager2));//SliderItems öğelerini ViewPager2 içinde görüntülemek için tasarlanmıştır.
        viewPager2.setClipToPadding(false);//false olarak ayarlanmışsa, iç boşluk sayfaların kenarlarına kadar genişleyebilir.
      viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);//kaç sayfanın önceden yüklenmesi gerektiğini belirler
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);//,aşırı kaydırmanın etkin oldmadığı bir moddur.



        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();//Birleşik sayfa dönüştürücüsü oluşturuluyor. Bu, birden çok sayfa dönüştürücüsünü bir araya getirmek için kullanılır.
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));//İlk dönüştürücü olarak MarginPageTransformer ekleniyor. Bu, sayfa kenarlarına bir marj (iç boşluk) ekleyen bir sayfa dönüştürücüsüdür
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {//
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1-Math.abs(position);//position değeri, sayfanın konumunu temsil eder ve -1 ile 1 arasında bir değer alır.
                page.setScaleY(0.85f+r*0.15f);
                //pagetransform sayfa dönüşümlerini özelleştirmek için
                //Bu kod, sayfa geçişlerine özel bir animasyon eklemek amacıyla sayfaların ölçeklenmesini kullanır. Sayfanın konumuna bağlı olarak ölçek faktörü değişir, bu da bir tür paralaks veya derinlik efekti yaratır. Sayfa ortadaysa büyük, sayfa kenarlara gittikçe küçük hale gelir
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);//CompositePageTransformer örneği, sayfa dönüştürücülerini birleştirir. Bu, sayfalar arasındaki geçişlerde farklı efektler elde etmek için kullanılır.
        viewPager2.setCurrentItem(1);//de görüntülenen başlangıç sayfasını belirler. Bu durumda, sayfa indeksi 1 olarak ayarlanmıştır.
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {


            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                slideHandler.removeCallbacks(sliderRunnable);//slideHandler, muhtemelen bir Handler örneğidir ve bu örneğin üzerinde çalışan bir zamanlanmış görevi (runnable) durdurur.
            }
        });
    }

         private Runnable sliderRunnable=new Runnable() {
             @Override
             public void run() {
                 viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
             }
         };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);//removeCallbacks metodu, belirli bir Runnable'ın (yani, sliderRunnable) Handler üzerindeki çalışmasını iptal eder. Bu, sayfa değiştiğinde belirli bir zamanlanmış görevi durdurmak için kullanılır.
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable,2000);//Yani, her 2 saniyede bir belirli bir işlemi gerçekleştirmek için kullanılabilir. Bu tip yapılar, örneğin otomatik bir slayt gösterisi oluşturmak için yaygın olarak kullanılır.
    }
    private void initView() {
        viewPager2=findViewById(R.id.ilk);//findViewById(R.id.ilk); ifadesi, XML dosyasında tanımlanan R.id.ilk kimliğine sahip bir View öğesini bulur ve bu öğeyi viewPager2 adlı sınıf değişkenine atar. Bu, XML dosyasında tanımlı bir ViewPager2 öğesine erişimi sağlar.


        recyclerViewBestMovies=findViewById(R.id.view1);
       recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       recyclerviewUpcoming=findViewById(R.id.view3);
       recyclerviewUpcoming.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       recyclerviewCategory=findViewById(R.id.view2);
       recyclerviewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       loading1=findViewById(R.id.progressBar1);
       loading2=findViewById(R.id.progressBar2);
       loading3=findViewById(R.id.progressBar3);




    }
}