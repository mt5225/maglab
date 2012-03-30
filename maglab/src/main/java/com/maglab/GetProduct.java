package com.maglab;

import com.google.code.magja.model.product.Product;
import com.google.code.magja.service.product.ProductRemoteService;
import com.google.code.magja.service.product.ProductRemoteServiceImpl;
import com.google.code.magja.soap.MagentoSoapClient;
import com.google.code.magja.soap.SoapConfig;

public class GetProduct {

	public static void main(String[] args) {
		try {
			String user = "wsuser";
			String pass = "mt8rwGPu";
			String host = "http://127.0.0.1/index.php/api/index/index/";

			// get default connection
			MagentoSoapClient magentoSoapClient = MagentoSoapClient
					.getInstance();
			System.out.println("after get instance");
			// configure connection
			SoapConfig soapConfig = new SoapConfig(user, pass, host);
			System.out.println("after get soapconfig");
			//magentoSoapClient.setConfig(soapConfig);
			
			System.out.println("list of products");
			ProductRemoteService productRemoteService = new ProductRemoteServiceImpl();
			productRemoteService.setSoapClient(magentoSoapClient);
			for (Product product : productRemoteService.listAllNoDep()) {
				System.out.println(product.toString());
			}
			
			//search for a product by sku
			//Product p1 = productRemoteService.getBySku("P001", false);
			//System.out.println(p1.toString());
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
