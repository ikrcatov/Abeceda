package com.example.abeceda.abeceda;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener
{
    private GestureDetector gestureDetector;
    Context context;
    int position;

    /*Sound*/
    MediaPlayer mp;

    AudioAndImagePlaceholder audioAndImagePlaceholder = new AudioAndImagePlaceholder();

    private final int interval = 1000; // 1 Second
    private final int swipeInterval = 100;
    private Handler handler = new Handler();

    boolean singleTapped = false;

    private float pointX;
    private float pointY;
    private int tolerance = 50;

    public OnSwipeTouchListener(Context c, int position)
    {
        this.context = c;
        this.position = position;
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent)
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
                    if(position > 0 && position <= (audioAndImagePlaceholder.mAudio.length - 2))
                    {
                        playMusic();

                        singleTapped = true;

                        handler.postDelayed(runnable, interval);
                    }
                }
            }
        }

        return gestureDetector.onTouchEvent(motionEvent);
    }

    private void playMusic()
    {
        if (mp != null && mp.isPlaying())
        {
            mp.stop();
            mp.release();
        }

        mp = new MediaPlayer();
        mp = MediaPlayer.create(context, audioAndImagePlaceholder.mAudio[position]);
        mp.start();
    }

    private final class GestureListener extends SimpleOnGestureListener
    {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            boolean result = false;
            try
            {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();

                if (Math.abs(diffX) > Math.abs(diffY))
                {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD)
                    {
                        if (diffX > 0)
                        {
                            onSwipeRight();
                        }

                        else
                        {
                            onSwipeLeft();
                        }
                    }
                }

                else
                {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD)
                    {
                        if (diffY > 0)
                        {
                            onSwipeDown();
                        }

                        else
                        {
                            onSwipeUp();
                        }
                    }
                }
            }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight()
    {
        if(position > 0)
        {
            position--;
            handler.postDelayed(swipeRunnable, swipeInterval);
        }
    }

    public void onSwipeLeft()
    {
        if(position <= (audioAndImagePlaceholder.mAudio.length - 2))
        {
            position++;
            handler.postDelayed(swipeRunnable, swipeInterval);
        }
    }

    public void onSwipeUp() {}

    public void onSwipeDown() {}

    private Runnable runnable = new Runnable(){
        public void run()
        {
            singleTapped = false;
        }
    };

    private Runnable swipeRunnable = new Runnable(){
        public void run()
        {
            playMusic();
        }
    };
}
