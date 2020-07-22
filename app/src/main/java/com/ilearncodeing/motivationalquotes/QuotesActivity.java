package com.ilearncodeing.motivationalquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuotesActivity extends AppCompatActivity {

    Quotesrecycler quotesrecycler;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);


        recyclerView = findViewById(R.id.quotesrecylerview);
        TextView textView = findViewById(R.id.categoryname);

        Intent i = getIntent();
        String category = i.getStringExtra("category");

        textView.setText(category+ "Quotes");

        layoutManager = new LinearLayoutManager(this);



        Httprequest httprequest = ApiController.getpojoClient().create(Httprequest.class);
        Call<Quotes> call = httprequest.getquotes("https://raw.githubusercontent.com/frankamania/simple-motivationalquotes/master/"+category+".json");
        call.enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {
                Quotes quote = response.body();
                assert quote != null;
                quotesrecycler = new Quotesrecycler(quote.getQuotes(),QuotesActivity.this);
                recyclerView.setAdapter(quotesrecycler);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {

            }
        });
    }
}