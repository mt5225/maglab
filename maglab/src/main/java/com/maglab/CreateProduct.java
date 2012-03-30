package com.maglab;

import java.util.UUID;

import com.google.code.magja.model.product.Product;
import com.google.code.magja.model.product.ProductAttributeSet;
import com.google.code.magja.model.product.ProductTypeEnum;
import com.google.code.magja.service.product.ProductRemoteService;
import com.google.code.magja.service.product.ProductRemoteServiceImpl;
import com.google.code.magja.soap.MagentoSoapClient;
import com.google.code.magja.soap.SoapConfig;

public class CreateProduct {

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
			
			
			ProductRemoteService productRemoteService = new ProductRemoteServiceImpl();
			productRemoteService.setSoapClient(magentoSoapClient);
			
			
			//Create a new product
			Product product = new Product();
			product.setSku(UUID.randomUUID().toString()); //treat sku as primary key for product
			product.setName("Product Name");
			product.setShortDescription("This is a short description");
			product.setDescription("This is a description for Product");
			product.setPrice(new Double(250.99));
			product.setCost(new Double(100.22));
			product.setEnabled(true);
			product.setWeight(new Double(0.500));
			product.setType(ProductTypeEnum.SIMPLE.getProductType());
			product.setAttributeSet(new ProductAttributeSet(4, "Default"));
			product.setMetaDescription("one two tree");
			product.set("meta_title", "kiss my ass");
			Integer[] websites = { 1 };
			product.setWebsites(websites);

			// inventory - set the inventory for product
			product.setQty(new Double(20));
			product.setInStock(true);
			productRemoteService.save(product);
			//productRemoteService.updateInventory(product);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
