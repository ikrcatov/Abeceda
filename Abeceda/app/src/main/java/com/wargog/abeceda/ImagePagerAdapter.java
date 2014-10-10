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

    AudioAndImagePlaceholder audioAndImagePlaceholder = new AudioAndImagePlaceholder();

    public ImagePagerAdapter(List<ImageView> images, Context context)
    {
        this.images = images;
        this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        ImageView imageView = images.get(position);

        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        if (container != null)
        {
            if(images.get(position) != null)
                container.removeView(images.get(position));
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
