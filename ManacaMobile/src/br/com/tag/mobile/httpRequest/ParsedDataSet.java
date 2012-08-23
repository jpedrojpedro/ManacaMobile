package br.com.tag.mobile.httpRequest;

import java.util.ArrayList;

import br.com.tag.mobile.model.Product;

public class ParsedDataSet
{
	private ArrayList<Product> products = new ArrayList<Product>();
	private int numElem = 0;
	
	public void setNewProduct()
	{
		Product p = new Product();
		products.add(p);
		numElem++;
	}
	
	public void setNumElem ( int numElem )
	{
		this.numElem = numElem;
	}
	
	public int getNumElem ()
	{
		return this.numElem;
	}
	
	public ArrayList<Product> getProducts ()
	{
		return this.products;
	}
}