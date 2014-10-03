package com.example.abeceda.abeceda;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageViewPager extends Activity
{
	// Declare Variable
	int position;

    /*Sound*/
    MediaPlayer mp;

    private final int interval = 1000; // 1 Second
    private Handler handler = new Handler();

    boolean swiped = false;
    boolean singleTapped = false;

    /*LENGTH OF IMAGE ARRAY*/
    int imageArrayLength;

    ViewPager viewpager;

    ImageAdapter imageAdapter;
    ImageViewPager context;

    int savePosition = 0;

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

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {}

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

                if(savePosition > pos)
                {
                    Toast.makeText(getApplicationContext(), "Swiped Right", Toast.LENGTH_SHORT).show();
                }

                else if(savePosition < pos)
                {
                    Toast.makeText(getApplicationContext(), "Swiped Left", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Tap", Toast.LENGTH_SHORT).show();
                }

                savePosition = pos;
                position = pos;
            }
        });
	}

    private void setViewPagerValuesAndStartMediaPlayer(ViewPager viewPager, ImageAdapter imageAdapter)
    {
        List<ImageView> images = new ArrayList<ImageView>();

        // Retrieve all the images
        for (int i = 0; i < 15; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageAdapter.mThumbIds[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            images.add(imageView);
        }

        // Set the images into ViewPager
        ImagePagerAdapter pageradapter = new ImagePagerAdapter(images);
        viewpager.setAdapter(pageradapter);
        // Show images following the position
        viewpager.setCurrentItem(position);

        mp = new MediaPlayer();
        mp = MediaPlayer.create(context, imageAdapter.mAudio[position]);
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