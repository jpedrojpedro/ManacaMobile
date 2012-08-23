package br.com.tag.mobile.model;

public class ShopCartItem
{
	private int idProduct;
	private String nameProduct;
	private int qtdItem;
	private float priceItem;
	
	public ShopCartItem ( int id, String name, int qtd, float price )
	{
		this.idProduct = id;
		this.nameProduct = name;
		this.qtdItem = qtd;
		this.priceItem = price;
	}

	public int getIdProduct()
	{
		return idProduct;
	}

	public void setIdProduct(int idProduct)
	{
		this.idProduct = idProduct;
	}

	public String getNameProduct()
	{
		return nameProduct;
	}

	public void setNameProduct(String nameProduct)
	{
		this.nameProduct = nameProduct;
	}

	public int getQtdItem()
	{
		return qtdItem;
	}

	public void setQtdItem(int qtdItem)
	{
		this.qtdItem = qtdItem;
	}

	public float getPriceItem()
	{
		return priceItem;
	}

	public void setPriceItem(float priceItem)
	{
		this.priceItem = priceItem;
	}
}