package com.wargog.abeceda;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter
{
    public List<ImageView> images = new ArrayList<ImageView>();
    Context context;

    public ImagePagerAdapter(List<ImageView> images, Context context)
    {
        this.images = images;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        try
        {
            View v = images.get (position);
            container.addView (v);
            return v;
        }

        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        try
        {
            container.removeView (images.get (position));
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount()
    {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o)
    {
        return view == o;
    }
}
