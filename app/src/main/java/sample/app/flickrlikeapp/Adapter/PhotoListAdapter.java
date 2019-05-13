package sample.app.flickrlikeapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import sample.app.flickrlikeapp.Listener.IImageClickListener;
import sample.app.flickrlikeapp.Model.Photo;
import sample.app.flickrlikeapp.R;

public class PhotoListAdapter extends ArrayAdapter<Photo> {

    static class ViewHolder {
        TextView title;
        ImageView image;

    }
    private int resourceLayout;
    private Context mContext;
    private List<Photo> allitems;
    private IImageClickListener listener;

    public PhotoListAdapter(Context context, int resource, List<Photo> mListPhoto, IImageClickListener listener) {
        super(context, resource, mListPhoto);
        this.resourceLayout = resource;
        this.mContext = context;
        this.allitems = mListPhoto;
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater vi = LayoutInflater.from(mContext);
            convertView = vi.inflate(resourceLayout, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_name);
            holder.image = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Photo photo = getItem(position);
        Integer farm = photo.getFarm();
        String secret = photo.getSecret();
        String id = photo.getId();
        String server = photo.getServer();

        // construct the URL for loading in picasso in View Holder
        final String URL =  "https://farm" + farm.toString() + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_z.jpg";
        final IImageClickListener imageClickListener = this.listener;
        if (photo != null) {
            Picasso.with(this.mContext).load(URL).into(holder.image);
            holder.title.setText(photo.getTitle());
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageClickListener.onListItemImageClicked(position);
                }
            });
        }

        return convertView;
    }

}