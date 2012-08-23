package br.com.tag.mobile.httpRequest;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.quietlycoding.android.picker.Picker;

public class GetProductsImageTask extends AsyncTask<Void, Void, Bitmap>
{
	private ProgressDialog loading;
	private Picker instance;
	private String imgName;
	
	public GetProductsImageTask(Picker obj, String imgName)
	{
		this.instance = obj;
		this.imgName = imgName;
	}
	
	@Override
	protected Bitmap doInBackground(Void... params)
	{
		return QueryImage.getImage(this.imgName);
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		this.loading = new ProgressDialog(this.instance);
		this.loading.setMessage("Carregando...");
		this.loading.show();
	}
	
	@Override
	protected void onPostExecute(Bitmap result)
	{
		super.onPostExecute(result);
		this.loading.dismiss();
		this.instance.setProductsImage(result);
	}
}