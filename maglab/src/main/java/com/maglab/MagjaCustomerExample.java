package com.maglab;

import java.util.UUID;

import com.google.code.magja.model.customer.Customer;
import com.google.code.magja.model.product.Product;
import com.google.code.magja.model.product.ProductAttributeSet;
import com.google.code.magja.model.product.ProductTypeEnum;
import com.google.code.magja.service.RemoteServiceFactory;
import com.google.code.magja.service.customer.CustomerRemoteService;
import com.google.code.magja.service.customer.CustomerRemoteServiceImpl;
import com.google.code.magja.service.product.ProductRemoteService;
import com.google.code.magja.service.product.ProductRemoteServiceImpl;
import com.google.code.magja.soap.MagentoSoapClient;
import com.google.code.magja.soap.SoapConfig;

public class MagjaCustomerExample {

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
			
			System.out.println("list of customers");
			CustomerRemoteService customerRemoteService = new CustomerRemoteServiceImpl();
			customerRemoteService.setSoapClient(magentoSoapClient);
			
			for (Customer customer : customerRemoteService.list()) {
				System.out.println(customer.toString());
			}
			
			System.out.println("list of products");
			ProductRemoteService productRemoteService = new ProductRemoteServiceImpl();
			productRemoteService.setSoapClient(magentoSoapClient);
			for (Product product : productRemoteService.listAllNoDep()) {
				System.out.println(product.toString());
			}
			
			//search for a product by sku
			Product p1 = productRemoteService.getBySku("P001", false);
			System.out.println(p1.toString());
			
			//search for a product by id : does no work
			//Product p2 = productRemoteService.getById(2);
			//System.out.println(p2.toString());
			
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
			
			// websites - set the website for product
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
