package com.wargog.abeceda;
import android.graphics.Bitmap;

/**
 * Created by Ivan on 29.8.2014..
 */
public class ImageItem
{
    private Bitmap image;
    private String title;

    public ImageItem(Bitmap image, String title)
    {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage()
    {
        return image;
    }

    public void setImage(Bitmap image)
    {
        this.image = image;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}