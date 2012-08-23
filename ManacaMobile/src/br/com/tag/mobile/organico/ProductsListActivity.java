package br.com.tag.mobile.organico;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import br.com.tag.mobile.httpRequest.GetProductsTask;
import br.com.tag.mobile.httpRequest.GetProductsThumbTask;
import br.com.tag.mobile.model.Product;
import br.com.tag.mobile.organico.ProductArrayAdapter.ProductsViewHolder;

import com.quietlycoding.android.picker.Picker;

public class ProductsListActivity extends Activity implements OnScrollListener
{
	private ListView showProducts;
    private ArrayList<Product> products;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_product);
		this.showProducts = (ListView) findViewById(R.id.listView);
		if ( isNetworkAvaiable(this) )
		{
			// call AsyncTask
			new GetProductsTask(this).execute();
			registerForContextMenu(this.showProducts);
		}
		else
		{
			Toast.makeText(this, "Não há conexão de rede disponível", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.shop_cart_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch ( item.getItemId() )
		{
	        case R.id.goto_shop_cart:
	            // intent to cart shop
	        	Intent i = new Intent(	ProductsListActivity.this, 
	        							ShopCartListActivity.class);
	        	startActivity(i);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Adicionar ao Carrinho...");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		if ( item.getTitle().equals("Adicionar ao Carrinho...") )
		{
			Intent i = new Intent(ProductsListActivity.this, Picker.class);
			i.putExtra("imageName", this.products.get(info.position).getImageName());
			i.putExtra("productId", this.products.get(info.position).get_id());
			i.putExtra("productPrice", this.products.get(info.position).getAmount());
			i.putExtra("productName", this.products.get(info.position).getProductName());
			i.putExtra("productType", this.products.get(info.position).getProductTypeName());
        	startActivity(i);
		}
		else
			return false;
		
		return true;
	}
	
	public void populateListView ( ArrayList<Product> products )
	{
		if ( products != null )
		{
			this.products = products;
			ProductArrayAdapter messageAdapter = new ProductArrayAdapter(this, products);
			this.showProducts.setAdapter(messageAdapter);
		}
	}
	
	public static boolean isNetworkAvaiable ( Context context )
	{
		ConnectivityManager cm = (ConnectivityManager) 
								 context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if ( netInfo != null && netInfo.isConnected() )
			return true;
		else
			return false;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		int lastPosition, firstPosition;
		if ( scrollState == SCROLL_STATE_IDLE )
		{
			firstPosition = view.getFirstVisiblePosition();
			lastPosition = view.getLastVisiblePosition();
			
			for ( int i = firstPosition; i <= lastPosition; i++ )
			{
				if ( ProductsListActivity.isNetworkAvaiable(this) )
				{
					ProductsViewHolder p;
					p = (ProductsViewHolder) this.showProducts.getTag(i);
					new GetProductsThumbTask(this, p.imgName, i);
				}
			}
		}
	}
	
	public void setThumbImage ( int position, Bitmap img )
	{
		Drawable d = new BitmapDrawable(img);
		this.showProducts.getChildAt(position).setBackgroundDrawable(d);
	}
}