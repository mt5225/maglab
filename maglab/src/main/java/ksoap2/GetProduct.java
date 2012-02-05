package ksoap2;

import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.google.code.magja.model.product.Product;

public class GetProduct {

	private static final String METHOD_LOGIN = "login";
	private static final String METHOD_CALL = "call";
	private static final String NAMESPACE = "urn:Magento";
	private static final String URL = "http://127.0.0.1/index.php/api/index/index/";
	private static final String USER = "wsuser";
	private static final String PASS = "mt8rwGPu";
	
	public static void main( String[] args )
    {
		try {
	        SoapObject request = new SoapObject(NAMESPACE, METHOD_LOGIN);
	        request.addProperty("username", USER);
	        request.addProperty("apiKey", PASS);
	 
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	 
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	        androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        androidHttpTransport.call(METHOD_LOGIN, envelope);
	        String result = (String)envelope.getResponse();
	        //System.out.println("sessionID =" + result.getPropertyAsString(0));
	        
	        String sessionID = result;
	        
	        request = new SoapObject(NAMESPACE, METHOD_CALL);
	        request.addProperty("sessionId", sessionID);
	        request.addProperty("resourcePath", "catalog_product.list");
	        envelope.setOutputSoapObject(request);
	        envelope.addMapping(NAMESPACE, METHOD_CALL, Product.class);
	        androidHttpTransport = new HttpTransportSE(URL);
	        androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        androidHttpTransport.call(METHOD_CALL, envelope);
	        List products = (List) envelope.getResponse();
	         
	        for(Object p: products){
	           System.out.println(p.toString());
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
}
