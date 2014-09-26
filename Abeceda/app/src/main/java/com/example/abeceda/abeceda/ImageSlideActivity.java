package com.example.abeceda.abeceda;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class ImageSlideActivity extends FragmentActivity
{
    int position;
    ImageAdapter adapter;
    ImageSlideActivity context;

    /*LENGTH OF IMAGE ARRAY*/
    int imageArrayLength;
    boolean swiped = false;
    boolean singleTapped = false;

    /*Sound*/
    MediaPlayer mp;

    private int[] mAudio = new int[]
    {
        R.raw.a, R.raw.b, R.raw.c, R.raw.cjj, R.raw.cj, R.raw.d, R.raw.dzj,
        R.raw.dj, R.raw.e, R.raw.f, R.raw.g, R.raw.h, R.raw.i, R.raw.j,
        R.raw.k, R.raw.l, R.raw.lj, R.raw.m, R.raw.n, R.raw.nj, R.raw.o, R.raw.p,
        R.raw.r, R.raw.s, R.raw.sj, R.raw.t, R.raw.u, R.raw.v, R.raw.z,
        R.raw.zj
    };

    View.OnTouchListener gestureListener;

    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide);

        Bundle bdl = getIntent().getExtras();
        position = bdl.getInt("position");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        context = this;

        viewPager.setOnTouchListener(gestureListener);

        /*SWIPE events*/
        viewPager.setOnTouchListener(new OnSwipeTouchListener(this)
        {
            private float pointX;
            private float pointY;
            private int tolerance = 50;


            public void onSwipeRight()
            {
                if(position > 0)
                {
                    swiped = true;
                    position = position - 1;

                    adapter = new ImageAdapter(context, position);
                    viewPager.setAdapter(adapter);

                    //Necessary or the pager will only have one extra page to show
                    // make this at least however many pages you can see
                    viewPager.setOffscreenPageLimit(adapter.getCount());
                    //A little space between pages
                    viewPager.setPageMargin(15);

                    //If hardware acceleration is enabled, you should also remove
                    // clipping on the pager for its children.
                    viewPager.setClipChildren(false);
                    viewPager.setCurrentItem(position);

                    mp = new MediaPlayer();
                    mp = MediaPlayer.create(context, mAudio[position]);
                    mp.start();
                }
            }
            public void onSwipeLeft()
            {
                imageArrayLength = getResources().obtainTypedArray(R.array.image_ids).length();

                if(position < imageArrayLength - 1)
                {
                    swiped = true;
                    position = position + 1;

                    adapter = new ImageAdapter(context, position);
                    viewPager.setAdapter(adapter);

                    //Necessary or the pager will only have one extra page to show
                    // make this at least however many pages you can see
                    viewPager.setOffscreenPageLimit(adapter.getCount());
                    //A little space between pages
                    viewPager.setPageMargin(15);

                    //If hardware acceleration is enabled, you should also remove
                    // clipping on the pager for its children.
                    viewPager.setClipChildren(false);
                    viewPager.setCurrentItem(position);

                    mp = new MediaPlayer();
                    mp = MediaPlayer.create(context, mAudio[position]);
                    mp.start();
                }
            }

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if(motionEvent != null && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    pointX = motionEvent.getX();
                    pointY = motionEvent.getY();
                }

                else if(motionEvent != null && motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    boolean sameX = pointX + tolerance > motionEvent.getX() && pointX - tolerance < motionEvent.getX();
                    boolean sameY = pointY + tolerance > motionEvent.getY() && pointY - tolerance < motionEvent.getY();

                    if(sameX && sameY)
                    {
                        if(singleTapped == false)
                        {
                            mp = new MediaPlayer();
                            mp = MediaPlayer.create(context, mAudio[position]);
                            mp.start();

                            singleTapped = true;

                            handler.postDelayed(runnable, interval);
                        }
                    }
                }

                return super.onTouch(view, motionEvent);
            }
        });

        if(swiped == false)
        {
            adapter = new ImageAdapter(this, position);
            viewPager.setAdapter(adapter);

            //Necessary or the pager will only have one extra page to show
            // make this at least however many pages you can see
            viewPager.setOffscreenPageLimit(adapter.getCount());
            //A little space between pages
            viewPager.setPageMargin(15);

            //If hardware acceleration is enabled, you should also remove
            // clipping on the pager for its children.
            viewPager.setClipChildren(false);

            viewPager.setCurrentItem(position);

            mp = new MediaPlayer();
            mp = MediaPlayer.create(context, mAudio[position]);
            mp.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.image_slide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.exitApplication)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Runnable runnable = new Runnable(){
        public void run()
        {
            singleTapped = false;
        }
    };
}
