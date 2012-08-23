package br.com.tag.mobile.handlers;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.tag.mobile.organico.AboutActivity;
import br.com.tag.mobile.organico.MainActivity;

public class ShowAboutHandler implements OnClickListener
{
	private MainActivity instance;
	
	public ShowAboutHandler(MainActivity obj)
	{
		this.instance = obj;
	}

	@Override
	public void onClick(View v)
	{
		Intent i = new Intent(this.instance, AboutActivity.class);
		this.instance.startActivity(i);
	}
}