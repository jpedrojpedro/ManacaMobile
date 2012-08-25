package br.com.tag.mobile.httpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class QueryImage
{
	// singleton class
	private static final QueryImage instance = new QueryImage();
	private final String url = 	"http://www.manacasabordaserra.com.br/" +
								"manaca/imagens/clientes/CLI_0002/";
	
	private QueryImage () {}
	
	public static Bitmap getImage ( String image )
	{	
		Bitmap bMap = null;
		BitmapFactory.Options bmOptions;
	    bmOptions = new BitmapFactory.Options();
	    bmOptions.inSampleSize = 1;
	    bMap = LoadImage(instance.url + image, bmOptions);
		
		return bMap;
	}
	
	private static Bitmap LoadImage(String URL, BitmapFactory.Options options)
	{       
		Bitmap bitmap = null;
		InputStream in = null;       
	try
	{
		in = OpenHttpConnection(URL);
		bitmap = BitmapFactory.decodeStream(in, null, options);
		in.close();
	}
	catch (IOException e1)
	{
		Log.e("thumbImage", "Error parsing image | LoadImage");
	}
	catch (Exception e)
	{
		Log.e("thumbImage", "ERROR: " + e.toString());
	}
		return bitmap;               
	}

	private static InputStream OpenHttpConnection(String strURL) throws IOException
	{
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();
		
		try
		{
			HttpURLConnection httpConn = (HttpURLConnection)conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			if ( httpConn.getResponseCode() == HttpURLConnection.HTTP_OK)
				inputStream = httpConn.getInputStream();
		}
		catch (Exception ex)
		{
			Log.e("thumbImage", "Error parsing image | OpenHttpConnection");
		}
		return inputStream;
	}
}