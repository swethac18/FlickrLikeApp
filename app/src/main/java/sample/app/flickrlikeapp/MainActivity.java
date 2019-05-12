package sample.app.flickrlikeapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import sample.app.flickrlikeapp.Adapter.PhotoAdapter;
import sample.app.flickrlikeapp.Client.PhotosClient;
import sample.app.flickrlikeapp.Model.Example;
import sample.app.flickrlikeapp.Model.Photo;
import sample.app.flickrlikeapp.Model.Photos;
import sample.app.flickrlikeapp.Service.PhotosService;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.OnClickPhotoListener {

    List<String> mImageURLS = Collections.EMPTY_LIST;
    List<Photo> mListPhoto = Collections.EMPTY_LIST;
    RecyclerView mRecyclerView;
    TextView mRecentPhotoTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecentPhotoTitle = findViewById(R.id.tv_title);
        populateLists();
    }

    public void populateLists(){
        Retrofit retrofit = PhotosClient.getRetrofitInstance();
        PhotosService service = retrofit.create(PhotosService.class);

        Call<Example> call = service.getAllPhotos(PhotosClient.getMethod(), PhotosClient.getKey(),
                PhotosClient.getFormat(), PhotosClient.getNoJsonCallBack());
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(!response.isSuccessful()){
                    Log.i("res-NotSuccessful",response.errorBody()+String.valueOf(response.code()));
                }
                else{
                    int page  =response.body().getPhotos().getPage();
                    int pages = response.body().getPhotos().getPages();
                    mListPhoto = response.body().getPhotos().getPhoto();
                    mImageURLS = new ArrayList<>();
                    for (Photo photo: mListPhoto) {
                        Integer farm = photo.getFarm();
                        String secret = photo.getSecret();
                        String id = photo.getId();
                        String server = photo.getServer();

                       // System.out.println(title);
                        String URL =  "https://farm" + farm.toString() + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_z.jpg";
                       // System.out.println(URL);
                        mImageURLS.add(URL);
                    }
                    initRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.i("Failure",t.getMessage());
            }
        });
    }

    public void initRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        PhotoAdapter adapter = new PhotoAdapter(MainActivity.this,mImageURLS,mListPhoto,this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public void onPhotoClick(int position) {
        Log.i("inside","onPhotoClick");
        Intent startDetails = new Intent(MainActivity.this,DetailsActivity.class);
        startDetails.putExtra("ImageURL",mImageURLS.get(position));
        startDetails.putExtra(Intent.EXTRA_TEXT, (Parcelable) mListPhoto.get(position));
        startActivityForResult(startDetails,1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("called","onSavedInstanceState");
        outState.putString("recent_title",mRecentPhotoTitle.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("called","onRestoreInstanceState");
        if(savedInstanceState != null)
            mRecentPhotoTitle.setText(savedInstanceState.getString("recent_title"));
        else
            mRecentPhotoTitle.setText("title");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("inside","OnActivityResult");
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                if(data != null){
                    mRecentPhotoTitle.setText(getString(R.string.title)+" "+data.getStringExtra("Recent_Title"));
                }
            }
        }
    }
}
