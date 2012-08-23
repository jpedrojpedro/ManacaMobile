package br.com.tag.mobile.handlers;

import java.util.Calendar;

public class DateHandler
{
	private String dayWeek;
	private String month;
	private int year;
	
	public DateHandler ( Calendar date )
	{
		this.dayWeek = getDayWeek(date.get(Calendar.DAY_OF_WEEK));
		this.month = getMonth(date.get(Calendar.MONTH));
		this.year = date.get(Calendar.YEAR);
	}
	
	private String getMonth ( int month )
	{		
		String monthNames[] =  {"Janeiro", "Fevereiro", "Março", 
								"Abril", "Maio", "Junho", "Julho", 
								"Agosto", "Setembro", "Outubro", 
								"Novembro", "Dezembro"};
		return monthNames[month];
	}
	
	private String getDayWeek ( int dayWeek )
	{
		String weekDayNames[] = {"Domingo", "Segunda", "Terça", 
								 "Quarta", "Quinta", "Sexta", 
								 "Sábado"};
		return weekDayNames[(dayWeek-1)];
	}

	public String getDayWeek()
	{
		return dayWeek;
	}

	public void setDayWeek(String dayWeek)
	{
		this.dayWeek = dayWeek;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth(String month)
	{
		this.month = month;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}
}