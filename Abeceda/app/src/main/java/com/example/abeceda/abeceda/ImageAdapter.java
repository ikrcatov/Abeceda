package com.example.abeceda.abeceda;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
    private Context mContext;

    /*Sound*/
    MediaPlayer mp;

    AudioAndImagePlaceholder audioAndImagePlaceholder = new AudioAndImagePlaceholder();

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return audioAndImagePlaceholder.mThumbIds.length;
    }

    public Object getItem(int position) {
    	return audioAndImagePlaceholder.mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if (convertView == null)
        {  // If it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(audioAndImagePlaceholder.mThumbIds[position]);
        return imageView;
    }
}
