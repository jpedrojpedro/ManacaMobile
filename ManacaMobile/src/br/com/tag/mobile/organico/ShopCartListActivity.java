package br.com.tag.mobile.organico;

import java.text.DecimalFormat;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.tag.mobile.handlers.SendPurchaseHandler;
import br.com.tag.mobile.model.ShopCartItem;
import br.com.tag.mobile.persistence.DataBaseHandler;

public class ShopCartListActivity extends Activity
{
	private ListView shopCartList;
	private Button btnSend;
	private ArrayList<ShopCartItem> itens;
	private TextView idCompra;
	private TextView total;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_cart_list);
		this.shopCartList = (ListView) findViewById(R.id.listView);
		this.btnSend = (Button) findViewById(R.id.btn_envio);
		this.idCompra = (TextView) findViewById(R.id.id_compra);
		this.total = (TextView) findViewById(R.id.valor_total);
		this.btnSend.setOnClickListener(new SendPurchaseHandler(this));
		this.updateListView();
		registerForContextMenu(this.shopCartList);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Deletar");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		
		if ( item.getTitle().equals("Deletar") )
		{
			DataBaseHandler dbHandler = new DataBaseHandler(this);
			dbHandler.removeItem(this.itens.get(info.position).getIdProduct(), 
								 this.getIdCompra());
			dbHandler.close();
			this.updateListView();
			Toast.makeText(this, "Produto retirado da lista", Toast.LENGTH_SHORT).show();
		}
		/*
		else if ( item.getTitle().equals("Alterar") )
		{
			DataBaseHandler dbHandler = new DataBaseHandler(this);
			dbHandler.updateQtdItem(this.itens.get(info.position).getIdProduct(), 
									this.getIdCompra(), 
									this.itens.get(info.position).getQtdItem());
			dbHandler.close();
			this.updateListView();
			Toast.makeText(this, "Produto deve ser atualizado", Toast.LENGTH_SHORT).show();
		}
		*/
		else
			return false;
		
		return true;
	}
	
	public void updateListView ()
	{
		DataBaseHandler dbHandler = new DataBaseHandler(this);
		int id_compra = dbHandler.getIncompleteCartId();
		this.idCompra.setText(String.valueOf(id_compra));
		this.populateListView(dbHandler.getItens(id_compra));
		dbHandler.close();
	}
	
	public void populateListView ( ArrayList<ShopCartItem> itens )
	{
		if ( itens != null )
		{
			float soma = 0;
			DecimalFormat df = new DecimalFormat("###.00");
			for (ShopCartItem item : itens )
			{
				soma += (item.getPriceItem() * (float) (item.getQtdItem()));
			}
			this.itens = itens;
			this.total.setText(String.valueOf(df.format(soma)));
			ShopCartArrayAdapter shopCartAdapter = new ShopCartArrayAdapter(this, itens);
			this.shopCartList.setAdapter(shopCartAdapter);
		}
	}
	
	public int getIdCompra ()
	{
		return Integer.parseInt(this.idCompra.getText().toString());
	}
}