package br.com.tag.mobile.model;

public class Compra
{
	private int _id;
	private String date;
	private int foiEnviada;
	
	public Compra ( int id, String date )
	{
		this._id = id;
		this.date = date;
	}
	
	public Compra ( int id, String date, float total )
	{
		this._id = id;
		this.date = date;
	}
	
	public int get_id()
	{
		return _id;
	}
	
	public void set_id(int _id)
	{
		this._id = _id;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}

	public int getFoiEnviada()
	{
		return foiEnviada;
	}

	public void setFoiEnviada(int foiEnviada)
	{
		this.foiEnviada = foiEnviada;
	}
}