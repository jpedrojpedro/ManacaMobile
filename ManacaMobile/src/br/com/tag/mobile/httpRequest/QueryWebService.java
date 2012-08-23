package br.com.tag.mobile.httpRequest;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.util.Log;
import br.com.tag.mobile.handlers.ProductsHandler;
import br.com.tag.mobile.model.Product;

public class QueryWebService
{
	// singleton class
	private static final QueryWebService instance = new QueryWebService();
	private final String url = 	"http://www.manacasabordaserra.com.br/manaca" + 
								"/component/xmlproduto.cfm?id_empresa=2&id_tipo=" +
								"&id=&ordem=nome_produto&lista_logico=item_number," +
								"disponivel,apresentacao,amount,estoque,oferta," +
								"novo,image";
	
	private QueryWebService () {}
	
	public static ArrayList<Product> getUpdatedProducts ()
	{
		ArrayList<Product> result = null;
		
		try
		{
			
			/* defaultHttpClient */
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(instance.url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream stream = httpEntity.getContent();

            /* Get a SAXParser from the SAXPArserFactory. */
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            /* Get the XMLReader of the SAXParser we created. */
            XMLReader xr = sp.getXMLReader();
            
            /* Create a new ContentHandler and apply it to the XML-Reader*/
            ProductsHandler myHandler = new ProductsHandler();
            xr.setContentHandler(myHandler);
           
            /* Parse the xml-data from our URL. */
            xr.parse(new InputSource(stream));
            /* Parsing has finished. */

            /* Our ExampleHandler now provides the parsed data to us. */
            ParsedDataSet myDataSet = myHandler.getParsedData();
            
            result = myDataSet.getProducts();
	    }
		catch (Exception e)
	    {
			Log.e("QueryWebService", "Error!", e);
	    }
		
		if ( result != null )
		{
			Log.e("QueryWebService", "Gerou ArrayList! =)");
			Log.e("QueryWebService", "Qtd: " + result.size());
		}
		else
			Log.e("QueryWebService", "Não gerou ArrayList! =(");
		
		return result;
	}
}