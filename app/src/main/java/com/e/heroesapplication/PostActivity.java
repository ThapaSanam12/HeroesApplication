package com.e.heroesapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import api.Heroesapi;
import model.Heroes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
private final static String BASE_URL="http://10.0.2.2:3000/";
private EditText etname,etdesc;
private Button btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

etname=findViewById(R.id.etname);
etdesc=findViewById(R.id.etdesc);
btnregister=findViewById(R.id.btnregister);

btnregister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        register();
    }
});


    }
private void register(){
        String name=etname.getText().toString();
        String desc=etdesc.getText().toString();

    Map<String,String> map=new HashMap<>();
    map.put("name",name);
    map.put("desc",desc);




    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Heroesapi heroesapi=retrofit.create(Heroesapi.class);
Call<Void>heroesCall=heroesapi.addHeroes(map);

    heroesCall.enqueue(new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (!response.isSuccessful()) {
                Toast.makeText(PostActivity.this, "code"+ response.code(), Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(PostActivity.this, "register success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(PostActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    });



}

}
