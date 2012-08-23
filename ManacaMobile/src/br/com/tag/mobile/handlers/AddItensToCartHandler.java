package br.com.tag.mobile.handlers;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import br.com.tag.mobile.model.Itens_Compra;
import br.com.tag.mobile.persistence.DataBaseHandler;

import com.quietlycoding.android.picker.Picker;

public class AddItensToCartHandler implements OnClickListener
{
	private Picker instance;
	
	public AddItensToCartHandler(Picker obj)
	{
		this.instance = obj;
	}

	@Override
	public void onClick(View v)
	{
		DataBaseHandler db = new DataBaseHandler(this.instance);
		int id_compra = db.getIncompleteCartId();
		if ( id_compra == 0 )
			id_compra = db.addCart();
		Itens_Compra item = new Itens_Compra( id_compra, 
											  this.instance.getSelectedProd(), 
											  this.instance.getQtd(), 
											  this.instance.getPriceProd()
											);
		db.addProduct(	this.instance.getSelectedProd(), 
						this.instance.getProdName(), 
						this.instance.getImgName(), 
						this.instance.getTypeProduct()
					 );
		db.addItem(item);
		db.close();
		Toast.makeText(	this.instance, "Produto inserido com sucesso!", 
						Toast.LENGTH_SHORT).show();
		this.instance.finish();
	}
}