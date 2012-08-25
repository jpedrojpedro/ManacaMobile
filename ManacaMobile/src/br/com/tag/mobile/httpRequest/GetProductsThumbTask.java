package br.com.tag.mobile.httpRequest;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import br.com.tag.mobile.organico.ProductsListActivity;

public class GetProductsThumbTask extends AsyncTask<Void, Void, Bitmap>
{
	private ProductsListActivity instance;
	private String imgName;
	private int position;
	
	public GetProductsThumbTask(ProductsListActivity obj, String imgName, 
								int position)
	{
		this.instance = obj;
		this.imgName = imgName;
		this.position = position;
	}
	
	@Override
	protected Bitmap doInBackground(Void... params)
	{
		return QueryImage.getImage(this.imgName);
	}
	
	@Override
	protected void onPostExecute(Bitmap result)
	{
		super.onPostExecute(result);
		this.instance.setThumbImage(this.position, result);
	}
}