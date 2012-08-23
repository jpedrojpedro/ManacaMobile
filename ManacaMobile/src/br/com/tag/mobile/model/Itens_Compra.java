package br.com.tag.mobile.model;

public class Itens_Compra
{
	private int _id;
	private int idCompra;
	private int idProduto;
	private int quantity;
	private float itemAmount;
	
	public Itens_Compra ( int idCompra, int idProduto, int quantity, 
						  float itemAmount )
	{
		this.idCompra = idCompra;
		this.idProduto = idProduto;
		this.quantity = quantity;
		this.itemAmount = itemAmount;
	}
	
	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public int getIdCompra()
	{
		return idCompra;
	}
	
	public void setIdCompra(int idCompra)
	{
		this.idCompra = idCompra;
	}
	
	public int getIdProduto()
	{
		return idProduto;
	}
	
	public void setIdProduto(int idProduto)
	{
		this.idProduto = idProduto;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

	public float getItemAmount()
	{
		return itemAmount;
	}

	public void setItemAmount(float itemAmount)
	{
		this.itemAmount = itemAmount;
	}
}