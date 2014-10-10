package com.wargog.abeceda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    private GridView gridView;
    private GridViewAdapter customGridAdapter;

    public ArrayList images = new ArrayList();
    ImageView imageView;

    AudioAndImagePlaceholder audioAndImagePlaceholder = new AudioAndImagePlaceholder();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.lettersHolderGrid);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
        //gridView.setStretchMode(GridView.NO_STRETCH);

        float scalefactor = getResources().getDisplayMetrics().density * 100;
        int number = getWindowManager().getDefaultDisplay().getWidth();
        int columns = (int) ((float) number / (float) scalefactor);
        gridView.setNumColumns(columns);

        gridView.setAdapter(customGridAdapter);

        retrieveImages();

        gridView.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                int postion = position;
                Intent i = new Intent(getApplicationContext(), ImageViewPager.class);
                i.putExtra("position", postion);

                /*DA BI POSLAO OVU LISTU U DRUGI ACTIVITY, MORAMO U DRUGOM ACTIVITY LISTU PROGLASIT STATIC*/
                ImageViewPager.images = images;

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.exitApplication)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList getData()
    {
        final ArrayList imageItems = new ArrayList();
        // retrieve String drawable array
        TypedArray imgs = getResources().obtainTypedArray(R.array.cloudImage_ids);
        for (int i = 0; i < imgs.length(); i++)
        {
            Bitmap bitmap = BitmapFactory.decodeResource
                    (this.getResources(),imgs.getResourceId(i, -1));

            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }

        return imageItems;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void retrieveImages()
    {
        // Retrieve all the images
        for (int i = 0; i < audioAndImagePlaceholder.mThumbIds.length; i++)
        {
            imageView = new ImageView(this);
            imageView.setImageResource(audioAndImagePlaceholder.mThumbIds[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            images.add(imageView);
        }
    }
}
