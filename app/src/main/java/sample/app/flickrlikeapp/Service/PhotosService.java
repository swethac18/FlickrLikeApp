package sample.app.flickrlikeapp.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sample.app.flickrlikeapp.Model.Example;
import sample.app.flickrlikeapp.Model.Photos;

/**
 * Created by swetha on 5/11/19.
 */

public interface PhotosService {

    @GET("services/rest/")
    Call<Example> getAllPhotos(@Query("method") String method, @Query("api_key") String api_key,
                               @Query("format") String format, @Query("nojsoncallback") boolean nojsoncallback );

}
