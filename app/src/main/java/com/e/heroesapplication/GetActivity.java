package com.e.heroesapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import api.Heroesapi;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetActivity extends AppCompatActivity {
    private final static String BASE_URL="http://10.0.2.2:3000/";
private TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        tvData=findViewById(R.id.tvData);


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Heroesapi heroesapi=retrofit.create(Heroesapi.class);
Call<List<Heroes>>listCall=heroesapi.getHero();

listCall.enqueue(new Callback<List<Heroes>>() {
    @Override
    public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
        if (!response.isSuccessful()){
            tvData.setText("Code:"+response.code());
            return;
        }

        List<Heroes>heroesList=response.body();
        for (Heroes heroes:heroesList){
            String content="";
            content +="name:"+heroes.getName()+ "\n";
            content +="desc:"+heroes.getDesc()+ "\n";

            tvData.append(content);
        }
    }

    @Override
    public void onFailure(Call<List<Heroes>> call, Throwable t) {
tvData.setText("error :" +t.getMessage());
    }
});
    }
}
