package com.example.abeceda.abeceda;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter
{
	Context context;
    int imagePosition = 0;

    public int[] IMAGES = new int[]
    {
        R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.cjj, R.drawable.cj, R.drawable.d, R.drawable.dzj,
        R.drawable.dj, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j,
        R.drawable.k, R.drawable.l, R.drawable.lj, R.drawable.m, R.drawable.n, R.drawable.nj, R.drawable.o, R.drawable.p,
        R.drawable.r, R.drawable.s, R.drawable.sj, R.drawable.t, R.drawable.u, R.drawable.v, R.drawable.z,
        R.drawable.zj
    };

    ImageAdapter(Context context, int position)
    {
    	this.context = context;
        this.imagePosition = position;
    }

    @Override
    public int getCount()
    {
      return IMAGES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(
                R.dimen.padding_medium);
        //imageView.setPadding(padding, padding, padding, padding);
        //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(IMAGES[imagePosition]);

        //Calculation of ImageView Size - density independent.
        //maybe you should do this calculation not exactly in this method but put is somewhere else.
        Resources r = Resources.getSystem();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics());

        imageView.setLayoutParams(new GridView.LayoutParams((int)px, (int)px));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        ((ViewPager) container).addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
