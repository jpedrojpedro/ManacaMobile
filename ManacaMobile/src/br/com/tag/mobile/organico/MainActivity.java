package br.com.tag.mobile.organico;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import br.com.tag.mobile.handlers.ShowAboutHandler;
import br.com.tag.mobile.handlers.ShowProductsHandler;

public class MainActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnProdutos = (Button) findViewById(R.id.btnProdutos);
        Button btnSobre = (Button) findViewById(R.id.btnSobre);
        btnProdutos.setOnClickListener(new ShowProductsHandler(this));
        btnSobre.setOnClickListener(new ShowAboutHandler(this));
    }
    
    @Override
	public void onBackPressed()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Tem certeza que deseja sair?")
	        .setCancelable(false)
	        .setPositiveButton("Sim", 
	        new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog,int id)
	            {
	            	Intent i = new Intent(Intent.ACTION_MAIN);
	            	i.addCategory(Intent.CATEGORY_HOME);
	            	startActivity(i);
	            }
	        })
	        .setNegativeButton("Não", 
	        new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog,int id)
	            {
	                dialog.cancel();
	            }
	        });
	        AlertDialog alert = builder.create();
	        alert.show();
	}
}