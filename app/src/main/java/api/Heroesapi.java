package api;

import java.util.List;
import java.util.Map;

import model.Heroes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Heroesapi {
    @POST("heroes")
    Call<Void>register(@Body Heroes heroes);


    @GET("heroes")
    Call<List<Heroes>> getHeroes();

    //field ko
    @FormUrlEncoded
    @POST("heroes")
    Call<Void> addHeroes(@Field("name") String name, @Field("desc") String desc);

    @FormUrlEncoded
    @POST("heroes")
    Call<Void>addHeroes(@FieldMap Map<String,String> map);


}
