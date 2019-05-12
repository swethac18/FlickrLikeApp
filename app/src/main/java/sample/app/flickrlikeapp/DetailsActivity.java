package sample.app.flickrlikeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import sample.app.flickrlikeapp.Model.Photo;

public class DetailsActivity extends AppCompatActivity {

    Photo mPhoto;
    String mImageURL;
    ImageView mPhotoImage;
    TextView mTitleName;
    TextView mOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mPhotoImage = findViewById(R.id.iv_PhotoDetailView);
        mOwner = findViewById(R.id.tv_owner);
        mTitleName = findViewById(R.id.tv_TiltleName);

        if(getIntent().hasExtra(Intent.EXTRA_TEXT)){
            mPhoto = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        }
        else{
            Toast.makeText(this,"Content not available",Toast.LENGTH_LONG).show();
        }

        if(getIntent().hasExtra("ImageURL")){
            mImageURL = getIntent().getStringExtra("ImageURL");
        }
        else{
            Toast.makeText(this,"Image not available",Toast.LENGTH_LONG).show();
        }

        Picasso.with(this)
                .load(mImageURL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(mPhotoImage);
        mTitleName.setText(getString(R.string.title)+mPhoto.getTitle());
        mOwner.setText(getString(R.string.owner)+mPhoto.getOwner());

    }

    @Override
    public void onBackPressed() {
        Log.i("inside","onBackPressed");
        Intent backIntent = new Intent();
        backIntent.putExtra("Recent_Title",mPhoto.getTitle());
        setResult(RESULT_OK,backIntent);
        finish();
    }
}
