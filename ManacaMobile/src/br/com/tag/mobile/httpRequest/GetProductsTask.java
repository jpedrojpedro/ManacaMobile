package br.com.tag.mobile.httpRequest;

import java.util.ArrayList;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import br.com.tag.mobile.model.Product;
import br.com.tag.mobile.organico.ProductsListActivity;

public class GetProductsTask extends AsyncTask<Void, Void, ArrayList<Product>>
{
	private ProgressDialog loading;
	private ProductsListActivity instance;
	
	public GetProductsTask(ProductsListActivity obj)
	{
		this.instance = obj;
	}
	
	@Override
	protected ArrayList<Product> doInBackground(Void... params)
	{
		ArrayList<Product> onlyAvaiable = QueryWebService.getUpdatedProducts();
		for ( int i = 0; i < onlyAvaiable.size(); i++ )
		{
			Product p = onlyAvaiable.get(i);
			if ( p.getStock() == 0 || p.isAvaiable() == false )
			{
				onlyAvaiable.remove(i);
				i--;
				Log.d("Removido Produto", "Produto: " + p.get_id());
			}
		}
		return onlyAvaiable;
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
	protected void onPostExecute(ArrayList<Product> result)
	{
		super.onPostExecute(result);
		this.loading.dismiss();
		this.instance.populateListView(result);
	}
}