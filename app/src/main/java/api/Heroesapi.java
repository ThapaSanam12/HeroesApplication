package api;

import model.Heroes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Heroesapi {
    @POST("heroes")
    Call<Void>register(@Body Heroes heroes);

@GET
}
