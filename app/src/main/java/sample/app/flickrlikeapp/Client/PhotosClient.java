package sample.app.flickrlikeapp.Client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit Client to fetch photos from Flickr getRecent method
 * endpoint: api.flickr.com
 * method: getRecent
 */
public class PhotosClient {

    private static Retrofit retrofit;
    private static final String BASE_URL="https://api.flickr.com/";

    public static String getKey(){
        return "";
    }

    public static String getMethod(){
        return "flickr.photos.getRecent";
    }

    public static boolean getNoJsonCallBack(){
        return true;
    }

    public static String getFormat(){
        return "json";
    }

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
