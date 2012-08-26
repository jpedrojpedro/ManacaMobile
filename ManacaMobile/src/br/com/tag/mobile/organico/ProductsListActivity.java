package br.com.tag.mobile.organico;

import java.util.ArrayList;
import java.util.Comparator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import br.com.tag.mobile.httpRequest.GetProductsTask;
import br.com.tag.mobile.httpRequest.GetProductsThumbTask;
import br.com.tag.mobile.imgConvert.ImageToBase64;
import br.com.tag.mobile.model.Product;
import br.com.tag.mobile.organico.ProductArrayAdapter.ProductsViewHolder;
import com.quietlycoding.android.picker.Picker;

public class ProductsListActivity extends Activity implements OnScrollListener, 
															  OnItemClickListener
{
	private ListView showProducts;
    private ArrayList<Product> products;
    private SharedPreferences sharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_product);
		this.showProducts = (ListView) findViewById(R.id.listView);
		this.showProducts.setOnScrollListener(this);
		this.showProducts.setOnItemClickListener(this);
		this.sharedPreferences = getSharedPreferences("ImageConversion", MODE_PRIVATE);
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
	
	public void populateListView ( ArrayList<Product> products )
	{
		if ( products != null )
		{
			this.products = products;
			ProductArrayAdapter productAdapter = new ProductArrayAdapter(this, products);
			productAdapter.sort(new Comparator<Product>() {
				@Override
				public int compare(Product lhs, Product rhs)
				{
					return lhs.getProductName().compareTo(rhs.getProductName());
				}
			});
			this.showProducts.setAdapter(productAdapter);
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
			
			Log.d("ScrollPosition", "First: " + firstPosition + 
									" Last: " + lastPosition);
			
			for ( int i = firstPosition; i <= lastPosition; i++ )
			{
				if ( ProductsListActivity.isNetworkAvaiable(this) )
				{
					Product p;
					p = (Product) this.showProducts.getItemAtPosition(i);
					String imgEncoded = 
							this.sharedPreferences.getString(p.getImageName(), 
															 "");
					if ( imgEncoded.equals("") )
					{
						Log.d("ImageNameScroll", "Image Name: " + p.getImageName());
						new GetProductsThumbTask(this, p.getImageName(), i).execute();
					}
					else
					{
						Bitmap b = ImageToBase64.decodeBase64(imgEncoded);
						this.setThumbImage(i, b);
					}
				}
			}
		}
	}
	
	public void setThumbImage ( int position, Bitmap img )
	{
		Drawable d = new BitmapDrawable(img);
		try
		{
			ProductsViewHolder p = 
					(ProductsViewHolder) this.showProducts.getChildAt(position).getTag();
			p.img.setBackgroundDrawable(d);
			Log.d("ThumbTaskInsert", "Posição: " + position);
			SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
			String imgEncoded = ImageToBase64.encodeTobase64(img);
			spEditor.putString(p.imgName, imgEncoded).commit();
		}
		catch (NullPointerException e)
		{
			Log.d("ThumbTaskInsert", "NULL POINTER: " + e.toString());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		ProductsViewHolder p = (ProductsViewHolder) arg1.getTag();
		Intent i = new Intent(ProductsListActivity.this, Picker.class);
		i.putExtra("imageName", p.imgName);
		i.putExtra("productId", p.id);
		i.putExtra("productPrice", p.amount.getText().toString());
		i.putExtra("productName", p.name.getText().toString());
		i.putExtra("productType", p.type);
    	startActivity(i);
	}
}