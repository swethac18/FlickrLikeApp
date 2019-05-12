package sample.app.flickrlikeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.nio.charset.StandardCharsets;
import java.util.List;

import sample.app.flickrlikeapp.Model.Photo;
import sample.app.flickrlikeapp.R;

/**
 * Created by swetha on 5/11/19.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    Context mContext;
    List<String> mImageUrl;
    List<Photo> mPhotoList;
    OnClickPhotoListener mOnClickPhotoListener;

    public PhotoAdapter(Context mContext, List<String> mImageUrl,List<Photo> mPhotoList, OnClickPhotoListener mOnClickPhotoListener){
        this.mOnClickPhotoListener=mOnClickPhotoListener;
        this.mContext=mContext;
        this.mImageUrl=mImageUrl;
        this.mPhotoList=mPhotoList;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        PhotoViewHolder photoViewHolder = new PhotoViewHolder(view);
        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
       // Log.i("inside","OnBindViewHolder");
        Picasso.with(mContext)
                .load(mImageUrl.get(position))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.errorimage)
                .into(holder.photoImageView);
        holder.title.setText(mPhotoList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size() == 0?0:mPhotoList.size();
    }

    public interface OnClickPhotoListener{
        public void onPhotoClick(int position);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView photoImageView;
        TextView title;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            photoImageView=itemView.findViewById(R.id.iv_photo);
            title=itemView.findViewById(R.id.tv_name);
            photoImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("inside","View-onClick");
            int position = getAdapterPosition();
            mOnClickPhotoListener.onPhotoClick(position);
        }
    }
}
