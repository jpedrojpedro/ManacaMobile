package br.com.tag.mobile.service;

import java.util.Calendar;

import android.R;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import br.com.tag.mobile.handlers.DateHandler;
import br.com.tag.mobile.persistence.DataBaseHandler;

public class UpdateInformation extends Service implements Runnable
{
	@Override
	public void onCreate()
	{
		Log.i("ServiceManaca", "Service was created");
		super.onCreate();
		this.run();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.i("ServiceManaca", "Service is alive");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy()
	{
		Log.i("ServiceManaca", "Service was destroyed");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	@Override
	public void run()
	{
		try
		{
			Log.e("ServiceManaca", "Rodando!");
			this.verifyProducts();
			Thread.sleep(900000); // 1000 milliseconds = 1 second | update in 15 minutes
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void verifyProducts ()
	{
		Calendar date = Calendar.getInstance();
		DateHandler dh = new DateHandler(date);
		if ( ( dh.getDayWeek().equals("Quarta") || 
			   dh.getDayWeek().equals("Quinta") || 
			   dh.getDayWeek().equals("Sexta")
			 ) 
			 && 
			 date.get(Calendar.AM_PM) == Calendar.PM 
		   )
		{
			if ( !hasBeenNotified(date) )
			{
				// notify user and update database
				DataBaseHandler db = new DataBaseHandler(this);
				db.insertDate(date);
				NewProductsNotification n = 
				new NewProductsNotification(this, 
											"Novos produtos disponíveis", 
											R.drawable.bottom_bar);
				n.send();
				db.close();
			}
		}
	}
	
	private boolean hasBeenNotified ( Calendar date )
	{
		// verify on database the last notify sent
		DataBaseHandler db = new DataBaseHandler(this);
		if ( db.lastNotifySent() != null )
		{
			String d[] = db.lastNotifySent().split("-");
			Log.e("LastNotifySent", "Date: " + d[0] + "-" + d[1] + "-" + d[2]);
			Calendar lastDate = Calendar.getInstance();
			lastDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d[0]));
			lastDate.set(Calendar.MONTH, (Integer.parseInt(d[1]) - 1));
			lastDate.set(Calendar.YEAR, Integer.parseInt(d[2]));
			db.close();
			if ( lastDate.before(date) )
				return false;
			else
				return true;
		}
		else
		{
			db.close();
			return false;
		}
	}
}