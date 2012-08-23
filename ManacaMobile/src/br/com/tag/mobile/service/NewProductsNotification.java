package br.com.tag.mobile.service;

import br.com.tag.mobile.organico.ProductsListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NewProductsNotification
{
	private String message;
	private int icon;
	private UpdateInformation instance;
	
	public NewProductsNotification ( UpdateInformation obj, String message, int drawableIcon )
	{
		this.message = message;
		this.icon = drawableIcon;
		this.instance = obj;
	}
	
	public void send ()
	{
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager myManager = (NotificationManager) 
										this.instance.getSystemService(ns);
		long when = System.currentTimeMillis();
		Notification myNotification = new Notification(	this.icon, 
														this.message, 
														when );
		Intent notifyIntent = new Intent(this.instance, ProductsListActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this.instance, 0, 
																notifyIntent, 0);
		myNotification.setLatestEventInfo(this.instance, 
										  "Manacá Sabor da Serra", 
										  "Novos Produtos Disponíveis", 
										  contentIntent);
		myManager.notify(1, myNotification);
	}
}