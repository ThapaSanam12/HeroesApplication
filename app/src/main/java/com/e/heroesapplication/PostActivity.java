package com.e.heroesapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
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
private ImageView ImageProfile;
String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

etname=findViewById(R.id.etname);
etdesc=findViewById(R.id.etdesc);
ImageProfile=findViewById(R.id.ImageProfile);
btnregister=findViewById(R.id.btnregister);

//ImageView=findViewById(R.id.ImageView);


btnregister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        register();
    }
});

ImageProfile.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        BrowseImage();
    }
});


    }


    private void BrowseImage(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if(data==null){
                Toast.makeText(this, "please select an image", Toast.LENGTH_SHORT).show();
            }
        }

        Uri uri=data.getData();
        imagePath= getRealPathFromUri(uri);
        previewImage(imagePath);

    }

    private String getRealPathFromUri(Uri uri) {

        String[] projection ={MediaStore.Images.Media.DATA};
        CursorLoader loader =new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor=loader.loadInBackground();
        int colIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result=cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void previewImage(String imagePath){
        File imgFile=new File(imagePath);
        if (imgFile.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageProfile.setImageBitmap(bitmap);
        }
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




            Toast.makeText(PostActivity.this, "register success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(PostActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    });





}

}
