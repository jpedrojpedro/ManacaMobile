package br.com.tag.mobile.imgConvert;

import java.io.File;
import java.io.FileOutputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageToSDCard
{
	public static void storeImage ( String imgName, Bitmap img )
	{
	    String root = Environment.getExternalStorageDirectory().toString();
	    File myDir = new File(root + "/manaca_images");
	    if ( !myDir.exists() )
	    	myDir.mkdir();
	    File file = new File (myDir, imgName);
	    if (file.exists ()) file.delete(); 
	    try
	    {
	        FileOutputStream out = new FileOutputStream(file);
	        img.compress(Bitmap.CompressFormat.JPEG, 90, out);
	        out.flush();
	        out.close();
	    }
	    catch (Exception e)
	    {
	        Log.e("sdCardError", e.getMessage());
	    }
	}
	
	public static Bitmap getImage ( String imgName )
	{
		Bitmap b = null;
		String root = Environment.getExternalStorageDirectory().toString();
	    File myDir = new File(root + "/manaca_images");
	    File file = new File (myDir, imgName);
	    String absPath = root + "/manaca_images/" + imgName;
	    if (file.exists ())
	    	b = BitmapFactory.decodeFile(absPath);
	    return b;
	}
}