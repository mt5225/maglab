package com.maglab;

import java.util.List;

import org.apache.axis2.AxisFault;

import com.google.code.magja.model.category.Category;
import com.google.code.magja.model.product.Product;
import com.google.code.magja.service.RemoteServiceFactory;
import com.google.code.magja.service.category.CategoryRemoteService;
import com.google.code.magja.service.category.CategoryRemoteServiceImpl;
import com.google.code.magja.soap.MagentoSoapClient;
import com.google.code.magja.soap.SoapConfig;

/**
 * test magento API
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        App myapp = new App();
        try {
        	System.out.println( "call api" );
			myapp.callAPI();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
           }
    
    public void callAPI() throws Exception {
    	String user = "wsuser";
    	String pass = "mt8rwGPu";
    	String host = "http://192.168.1.8/index.php/api/soap";
    	
    	// get default connection
    	MagentoSoapClient magentoSoapClient = MagentoSoapClient.getInstance();
    	
    	// configure connection
    	SoapConfig soapConfig = new SoapConfig(user, pass, host);
    	magentoSoapClient.setConfig(soapConfig);
    	
    	// get category object
    	CategoryRemoteService categoryRemoteService = new CategoryRemoteServiceImpl();
    	categoryRemoteService.setSoapClient(magentoSoapClient);

    	// get details for category with id 2
    	Category category = categoryRemoteService.getByIdClean(2);
    	System.out.println(category.toString());
    }
}
