package com.wargog.abeceda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    private GridView gridView;
    private GridViewAdapter customGridAdapter;

    Context context;

    static public ArrayList images = new ArrayList();

    AudioAndImagePlaceholder audioAndImagePlaceholder = new AudioAndImagePlaceholder();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        gridView = (GridView) findViewById(R.id.lettersHolderGrid);
        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());

        float scalefactor = getResources().getDisplayMetrics().density * 100;
        int number = getWindowManager().getDefaultDisplay().getWidth();
        int columns = (int) ((float) number / (float) scalefactor);
        gridView.setNumColumns(columns);

        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                int postion = position;
                Intent i = new Intent(getApplicationContext(), ImageViewPager.class);
                i.putExtra("position", postion);

                startActivity(i);

                finish();
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
        return images;
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
}
