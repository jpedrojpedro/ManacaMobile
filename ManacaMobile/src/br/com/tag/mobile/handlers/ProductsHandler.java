package br.com.tag.mobile.handlers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import br.com.tag.mobile.httpRequest.ParsedDataSet;
import android.util.Log;

public class ProductsHandler extends DefaultHandler
{
	private boolean inCatalog = false;
    private boolean inProduto = false;
	private boolean inIdTipo = false;
	private boolean inNomeTipo = false;
	private boolean inItemName = false;
	private boolean inNovo = false;
	private boolean inItemNumber = false;
	private boolean inDisponivel = false;
	private boolean inApresentacao = false;
	private boolean inImage = false;
	private boolean inAmount = false;
	private boolean inEstoque = false;
	private boolean inOferta = false;
	
	private ParsedDataSet myDataSet;
	
	@Override
	public void startElement(String uri, String localName, 
							 String qName, Attributes attributes) 
							 throws SAXException
	{
		super.startElement(uri, localName, qName, attributes);
		
		if ( localName.equals ( "catalog" ) )
            this.inCatalog = true;
		else if ( localName.equals ( "produto" ) )
		{
            this.inProduto = true;
            String attrValue = attributes.getValue("item_id");
            int i = Integer.parseInt(attrValue);
            myDataSet.setNewProduct();
            myDataSet.getProducts().get(myDataSet.getNumElem() - 1).set_id(i);
		}
		else if ( localName.equals ( "id_tipo" ) )
            this.inIdTipo = true;
		else if ( localName.equals ( "nome_tipo_produto" ) )
            this.inNomeTipo = true;
		else if ( localName.equals ( "item_name" ) )
            this.inItemName = true;
		else if ( localName.equals ( "novo" ) )
            this.inNovo = true;
		else if ( localName.equals ( "item_number" ) )
            this.inItemNumber = true;
		else if ( localName.equals ( "disponivel" ) )
            this.inDisponivel = true;
		else if ( localName.equals ( "apresentacao" ) )
            this.inApresentacao = true;
		else if ( localName.equals ( "image" ) )
            this.inImage = true;
		else if ( localName.equals ( "amount" ) )
            this.inAmount = true;
		else if ( localName.equals ( "estoque" ) )
            this.inEstoque = true;
		else if ( localName.equals ( "oferta" ) )
            this.inOferta = true;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
						   throws SAXException
	{
		super.endElement(uri, localName, qName);
		
		if ( localName.equals ( "catalog" ) )
            this.inCatalog = false;
		else if ( localName.equals ( "produto" ) )
            this.inProduto = false;
		else if ( localName.equals ( "id_tipo" ) )
            this.inIdTipo = false;
		else if ( localName.equals ( "nome_tipo_produto" ) )
            this.inNomeTipo = false;
		else if ( localName.equals ( "item_name" ) )
            this.inItemName = false;
		else if ( localName.equals ( "novo" ) )
            this.inNovo = false;
		else if ( localName.equals ( "item_number" ) )
            this.inItemNumber = false;
		else if ( localName.equals ( "disponivel" ) )
            this.inDisponivel = false;
		else if ( localName.equals ( "apresentacao" ) )
            this.inApresentacao = false;
		else if ( localName.equals ( "image" ) )
            this.inImage = false;
		else if ( localName.equals ( "amount" ) )
            this.inAmount = false;
		else if ( localName.equals ( "estoque" ) )
            this.inEstoque = false;
		else if ( localName.equals ( "oferta" ) )
            this.inOferta = false;
	}
	
    @Override
	public void characters(char ch[], int start, int length)
    {
    	String str = new String (ch, start, length);
    	 
		if ( this.inNomeTipo )
			 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setProductTypeName(str);
		else if ( this.inItemName )
		     myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setProductName(str);
		else if ( this.inNovo )
		{
			 if ( str.equals("s") )
				 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setNew(true);
			 else
				 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setNew(false);
		}
		else if ( this.inDisponivel )
		{
			 if ( str.equals("s") )
				 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setAvaiable(true);
			 else
				 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setAvaiable(false);
		}
		else if ( this.inApresentacao )
			 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setUnit(str);
		else if ( this.inImage )
			 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setImageName(str);
		else if ( this.inAmount )
			 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setAmount(Float.parseFloat(str));
		else if ( this.inEstoque )
			 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setStock(Float.parseFloat(str));
		else if ( this.inOferta )
		{
			 if ( str.equals("s") )
				 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setSale(true);
			 else
				 myDataSet.getProducts().get(myDataSet.getNumElem() - 1).setSale(false);
		}
	}
    
    @Override
    public void startDocument() throws SAXException
    {
    	Log.e("ProductsHandler", "Start Document");
    	this.myDataSet = new ParsedDataSet();
    }

    @Override
    public void endDocument() throws SAXException
    {
    	Log.e("ProductsHandler", "End Document");
    }
    
    public ParsedDataSet getParsedData()
    {
		return this.myDataSet;
	}
}