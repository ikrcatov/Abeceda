package com.example.abeceda.abeceda;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPager extends Activity
{
	// Declare Variable
	int position;

    /*Sound*/
    MediaPlayer mp;

    ViewPager viewpager;

    ImageAdapter imageAdapter;
    ImageViewPager context;

    static List<ImageView> images = new ArrayList<ImageView>();
    AudioAndImagePlaceholder audioAndImagePlaceholder = new AudioAndImagePlaceholder();

    private final int interval = 1000; // 1 Second
    private final int swipeInterval = 100;
    private Handler handler = new Handler();

    boolean singleTapped = false;

    private float pointX;
    private float pointY;
    private int tolerance = 50;

    private final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener());

    @Override
	public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		// Set title for the ViewPager
		setTitle("ViewPager");
		// Get the view from view_pager.xml
		setContentView(R.layout.activity_image_slide);

		// Retrieve data from MainActivity on item click event
        Bundle bdl = getIntent().getExtras();
        position = bdl.getInt("position");

        context = this;

        imageAdapter = new ImageAdapter(context);
        viewpager = (ViewPager) findViewById(R.id.view_pager);

        setViewPagerValuesAndStartMediaPlayer(viewpager, imageAdapter);

        //viewpager.setOnTouchListener(new OnSwipeTouchListener(this, position){});

        viewpager.setOnPageChangeListener(mOnPageListener);
        viewpager.setOnTouchListener(onTouchListener);
	}

    public ViewPager.OnPageChangeListener mOnPageListener = new ViewPager.OnPageChangeListener()
    {
        @Override public void onPageSelected(int positionForSwipe)
        {
            position = positionForSwipe;
            playMusic(positionForSwipe);
        }

        @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override public void onPageScrollStateChanged(int state) {}
    };

    public ViewPager.OnTouchListener onTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent motionEvent)
        {
            if (motionEvent != null && motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            {
                pointX = motionEvent.getX();
                pointY = motionEvent.getY();
            }

            else if (motionEvent != null && motionEvent.getAction() == MotionEvent.ACTION_UP)
            {
                boolean sameX = pointX + tolerance > motionEvent.getX() && pointX - tolerance < motionEvent.getX();
                boolean sameY = pointY + tolerance > motionEvent.getY() && pointY - tolerance < motionEvent.getY();

                if (sameX && sameY)
                {
                    if (singleTapped == false)
                    {
                        if (position > 0 && position <= (audioAndImagePlaceholder.mAudio.length - 2))
                        {
                            playMusic(position);

                            singleTapped = true;

                            handler.postDelayed(runnable, interval);
                        }
                    }
                }
            }
            return gestureDetector.onTouchEvent(motionEvent);
        }
    };

    private void playMusic(int positionForSwipe)
    {
        if (mp != null && mp.isPlaying())
        {
            mp.stop();
            mp.release();
        }

        mp = new MediaPlayer();
        mp = MediaPlayer.create(context, audioAndImagePlaceholder.mAudio[positionForSwipe]);
        mp.start();
    }

    private void setViewPagerValuesAndStartMediaPlayer(ViewPager viewPager, ImageAdapter imageAdapter)
    {
       // Set the images into ViewPager
        ImagePagerAdapter pageradapter = new ImagePagerAdapter(images, this);
        viewpager.setAdapter(pageradapter);
        // Show images following the position
        viewpager.setCurrentItem(position);
        viewpager.setOffscreenPageLimit(2);

        mp = new MediaPlayer();
        mp = MediaPlayer.create(context, audioAndImagePlaceholder.mAudio[position]);
        mp.start();
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