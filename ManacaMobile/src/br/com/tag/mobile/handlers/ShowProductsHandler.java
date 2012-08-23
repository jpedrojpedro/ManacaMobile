package br.com.tag.mobile.handlers;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.tag.mobile.organico.MainActivity;
import br.com.tag.mobile.organico.ProductsListActivity;

public class ShowProductsHandler implements OnClickListener
{
	private MainActivity instance;
	
	public ShowProductsHandler(MainActivity obj)
	{
		this.instance = obj;
	}

	@Override
	public void onClick(View v)
	{
		Intent i = new Intent(this.instance, ProductsListActivity.class);
		this.instance.startActivity(i);
	}
}