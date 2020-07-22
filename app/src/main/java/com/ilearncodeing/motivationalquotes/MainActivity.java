package com.ilearncodeing.motivationalquotes;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    Gridadptor categoryAdaptor;
    GridView category_gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_gridview = findViewById(R.id.mycategorygridview);



        Httprequest httprequest = ApiController.getpojoClient().create(Httprequest.class);
        Call<Categorys> call = httprequest.getcategorys("https://raw.githubusercontent.com/frankamania/simple-motivationalquotes/master/cat.json");
        call.enqueue(new Callback<Categorys>() {
            @Override
            public void onResponse(Call<Categorys> call, Response<Categorys> response) {

                Categorys categorys = response.body();

                System.out.println("model size"+categorys.getCategory());

                categoryAdaptor = new Gridadptor(categorys.getCategory(),MainActivity.this);
                category_gridview.setAdapter(categoryAdaptor);

            }

            @Override
            public void onFailure(Call<Categorys> call, Throwable t) {

            }
        });
    }
}