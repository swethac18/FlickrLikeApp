package sample.app.flickrlikeapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import sample.app.flickrlikeapp.Adapter.PhotoListAdapter;
import sample.app.flickrlikeapp.Client.PhotosClient;
import sample.app.flickrlikeapp.Listener.IImageClickListener;
import sample.app.flickrlikeapp.Listener.ListViewOnScrollListener;
import sample.app.flickrlikeapp.Model.Example;
import sample.app.flickrlikeapp.Model.Photo;
import sample.app.flickrlikeapp.Service.PhotosService;

public class MainActivity extends AppCompatActivity implements IImageClickListener {

    List<Pair<String, String>> mImageURLSnTitle = Collections.EMPTY_LIST;
    List<Photo> mListPhoto = Collections.EMPTY_LIST;
    ListView mListView;
    TextView mRecentPhotoTitle;
    private FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecentPhotoTitle = findViewById(R.id.tv_title);
        mListView = findViewById(R.id.list_view);
        //android:id="@+id/frameLayout"

        frameLayout = findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.VISIBLE);
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
                    mImageURLSnTitle = new ArrayList<>();
                    for (Photo photo: mListPhoto) {
                        Integer farm = photo.getFarm();
                        String secret = photo.getSecret();
                        String id = photo.getId();
                        String server = photo.getServer();
                        String URL =  "https://farm" + farm.toString() + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_z.jpg";
                        mImageURLSnTitle.add(new Pair<String,String>(URL, photo.getTitle()));
                    }
                }




                PhotoListAdapter adapter = new PhotoListAdapter(MainActivity.this,
                        R.layout.list_item,
                        mListPhoto,
                        (IImageClickListener) MainActivity.this);
                mListView.setAdapter(adapter);
                ListViewOnScrollListener scrollListener = new ListViewOnScrollListener(mListView, frameLayout, mRecentPhotoTitle);
                mListView.setOnScrollListener(scrollListener);


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.i("Failure",t.getMessage());
            }
        });


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
                    mRecentPhotoTitle.setText(getString(R.string.seen)+" "+data.getStringExtra("Recent_Title"));
                }
            }
        }
    }

    @Override
    public void onListItemImageClicked(int position) {
        Log.i("inside","onPhotoClick");
        Intent startDetails = new Intent(MainActivity.this,DetailsActivity.class);
        startDetails.putExtra("ImageURL", mImageURLSnTitle.get(position).first);
        startDetails.putExtra(Intent.EXTRA_TEXT, (Parcelable) mListPhoto.get(position));
        startActivityForResult(startDetails,1);
    }


}
