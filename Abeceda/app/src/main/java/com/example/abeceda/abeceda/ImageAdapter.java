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

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
    	return mThumbIds[position];
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

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // References to our images in res > drawable
    public Integer[] mThumbIds =
    {
        R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.cjj, R.drawable.cj, R.drawable.d, R.drawable.dzj,
        R.drawable.dj, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
        R.drawable.k, R.drawable.l, R.drawable.lj, R.drawable.m, R.drawable.n, R.drawable.nj, R.drawable.o, R.drawable.p,
        R.drawable.r, R.drawable.s, R.drawable.sj, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.z,
        R.drawable.zj
    };

    public int[] mAudio = new int[]
    {
        R.raw.a, R.raw.b, R.raw.c, R.raw.cjj, R.raw.cj, R.raw.d, R.raw.dzj,
        R.raw.dj, R.raw.e, R.raw.f, R.raw.g, R.raw.h, R.raw.i, R.raw.j,
        R.raw.k, R.raw.l, R.raw.lj, R.raw.m, R.raw.n, R.raw.nj, R.raw.o, R.raw.p,
        R.raw.r, R.raw.s, R.raw.sj, R.raw.t, R.raw.u, R.raw.v, R.raw.z,
        R.raw.zj
    };
}
