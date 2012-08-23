package br.com.tag.mobile.handlers;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import br.com.tag.mobile.organico.ShopCartListActivity;
import br.com.tag.mobile.persistence.DataBaseHandler;

public class SendPurchaseHandler implements OnClickListener
{
	private ShopCartListActivity instance;
	
	public SendPurchaseHandler(ShopCartListActivity obj)
	{
		this.instance = obj;
	}

	@Override
	public void onClick(View v)
	{
		if ( this.instance.getIdCompra() != 0 )
		{
			DataBaseHandler dbHandler = new DataBaseHandler(this.instance);
			dbHandler.markAsSentPurchase(this.instance.getIdCompra());
			dbHandler.close();
			this.instance.updateListView();
			Toast.makeText(this.instance, "Pedido Enviado", Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(this.instance, "Insira Algum Pedido", Toast.LENGTH_SHORT).show();
	}
}