package br.com.tag.mobile.model;

public class Product
{
	private int _id;
	private String productTypeName;
	private String productName;
	private boolean isNew;
	private boolean isAvaiable;
	private String unit;
	private String imageName;
	private float amount;
	private float stock;
	private boolean isSale;
	
	public Product () {}
	
	public Product ( int id, String typeName, String prodName, boolean isNew, 
					 boolean isAvaiable, String unit, String imageName, 
					 float amount, float stock, boolean isSale
					)
	{
		this._id = id;
		this.productTypeName = typeName;
		this.productName = prodName;
		this.isNew = isNew;
		this.isAvaiable = isAvaiable;
		this.unit = unit;
		this.imageName = imageName;
		this.amount = amount;
		this.stock = stock;
		this.isSale = isSale;
	}
	
	public int get_id()
	{
		return _id;
	}
	
	public void set_id(int _id)
	{
		this._id = _id;
	}
	
	public String getProductTypeName()
	{
		return productTypeName;
	}
	
	public void setProductTypeName(String productTypeName)
	{
		this.productTypeName = productTypeName;
	}
	
	public String getProductName()
	{
		return productName;
	}
	
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	public boolean isNew()
	{
		return isNew;
	}
	
	public void setNew(boolean isNew)
	{
		this.isNew = isNew;
	}
	
	public boolean isAvaiable()
	{
		return isAvaiable;
	}
	
	public void setAvaiable(boolean isAvaiable)
	{
		this.isAvaiable = isAvaiable;
	}
	
	public String getUnit()
	{
		return unit;
	}
	
	public void setUnit(String unit)
	{
		this.unit = unit;
	}
	
	public String getImageName()
	{
		return imageName;
	}
	
	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}
	
	public float getAmount()
	{
		return amount;
	}
	
	public void setAmount(float amount)
	{
		this.amount = amount;
	}
	
	public float getStock()
	{
		return stock;
	}
	
	public void setStock(float stock)
	{
		this.stock = stock;
	}
	
	public boolean isSale()
	{
		return isSale;
	}
	
	public void setSale(boolean isSale)
	{
		this.isSale = isSale;
	}
}