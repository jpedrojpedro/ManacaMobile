package br.com.tag.mobile.organico;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		LayoutInflater inflater = 	(LayoutInflater) context.
									getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.product_row, parent, false);
		
		Product p = products.get(position);
		
		TextView tvId = (TextView) rowView.findViewById(R.id.productId);
		TextView tvName = (TextView) rowView.findViewById(R.id.productName);
		TextView tvAmount = (TextView) rowView.findViewById(R.id.amount);
		
		tvId.setText(String.valueOf(p.get_id()));
		tvName.setText(p.getProductName());
		tvAmount.setText(String.valueOf(p.getAmount()));
		
		return rowView;
	}
}