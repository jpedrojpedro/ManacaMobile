package br.com.tag.mobile.organico;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.tag.mobile.model.ShopCartItem;

public class ShopCartArrayAdapter extends ArrayAdapter<ShopCartItem> 
{
	private final Context context;
	private final List<ShopCartItem> itens;
	
	public ShopCartArrayAdapter ( Context context, List<ShopCartItem> itens )
	{
		super ( context, R.layout.shop_cart_row, itens);
		this.context = context;
		this.itens = itens;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = 	(LayoutInflater) context.
									getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.shop_cart_row, parent, false);
		
		ShopCartItem i = itens.get(position);
		
		TextView tvProdId = (TextView) rowView.findViewById(R.id.productId);
		TextView tvProdName = (TextView) rowView.findViewById(R.id.productName);
		TextView tvPriceItem = (TextView) rowView.findViewById(R.id.amount);
		TextView tvQtdItem = (TextView) rowView.findViewById(R.id.quantity);
		
		tvProdId.setText(String.valueOf(i.getIdProduct()));
		tvProdName.setText(i.getNameProduct());
		tvPriceItem.setText(String.valueOf(i.getPriceItem()));
		tvQtdItem.setText(String.valueOf(i.getQtdItem()));
		
		return rowView;
	}
}