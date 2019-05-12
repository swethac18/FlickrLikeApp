package sample.app.flickrlikeapp.Client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by swetha on 5/11/19.
 */

public class PhotoURLClient {

    public static Retrofit getRetrofitInstance(String baseURL) {
        return new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
