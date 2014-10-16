package com.wargog.abeceda;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;


public class Splash extends Activity
{
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 400;

    /*Sound*/
    MediaPlayer mp;

    private int[] mAudio = new int[]
    {
        R.raw.abecedaintro
    };

    public ArrayList images = new ArrayList();
    public ArrayList imageItems = new ArrayList();

    ImageView imageView;
    AudioAndImagePlaceholder audioAndImagePlaceholder = new AudioAndImagePlaceholder();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*Sound main intro*/
        mp = new MediaPlayer();
        mp = MediaPlayer.create(this, mAudio[0]);
        mp.setLooping(false);
        mp.start();

         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent mainIntent = new Intent(Splash.this, MainActivity.class);

                /*DA BI POSLAO OVU LISTU U DRUGI ACTIVITY, MORAMO U DRUGOM ACTIVITY LISTU PROGLASIT STATIC*/
                retrieveImages();
                ImageViewPager.images = images;
                MainActivity.images = imageItems;

                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrieveImages()
    {
        TypedArray imgs = getResources().obtainTypedArray(R.array.cloudImage_ids);

        // Retrieve all the images
        for (int i = 0; i < audioAndImagePlaceholder.mThumbIds.length; i++)
        {
            imageView = new ImageView(this);
            imageView.setImageResource(audioAndImagePlaceholder.mThumbIds[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            images.add(imageView);

            Bitmap bitmap = BitmapFactory.decodeResource
                    (this.getResources(), imgs.getResourceId(i, -1));

            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
    }
}
