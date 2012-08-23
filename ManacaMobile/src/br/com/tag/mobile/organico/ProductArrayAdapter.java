package br.com.tag.mobile.organico;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.tag.mobile.model.Product;

public class ProductArrayAdapter extends ArrayAdapter<Product>
{
	private final Context context;
	private final List<Product> products;
	
	public ProductArrayAdapter ( Context context, List<Product> products )
	{
		super ( context, R.layout.product_row, products);
		this.context = context;
		this.products = products;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
        ProductsViewHolder viewHolder;
        
        if(convertView == null)
        {
			LayoutInflater inflater = 	(LayoutInflater) context.
										getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.product_row, parent, false);
			
			viewHolder = new ProductsViewHolder();
			viewHolder.id = (TextView) convertView.findViewById(R.id.productId);
			viewHolder.name = (TextView) convertView.findViewById(R.id.productName);
			viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.productImage);
        }
        else
        	viewHolder = (ProductsViewHolder) convertView.getTag();
		
		Product p = products.get(position);
		
		if ( p != null )
		{
			viewHolder.id.setText(String.valueOf(p.get_id()));
			viewHolder.name.setText(p.getProductName());
			viewHolder.amount.setText(String.valueOf(p.getAmount()));
			viewHolder.img.setBackgroundResource(R.drawable.no_image);
			viewHolder.imgName = p.getImageName();
		}
		
		convertView.setTag(viewHolder);
		
		return convertView;
	}
	
	static class ProductsViewHolder
	{
	    TextView id;
	    TextView name;
	    TextView amount;
	    ImageView img;
	    String imgName;
	}
}