package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import ksoap2.api.Customer;
import ksoap2.api.CustomerService;
import ksoap2.api.ProductImage;
import ksoap2.api.ProductImageService;
import ksoap2.soap.MessageParser;
import ksoap2.soap.SoapClient;

public class DataInit {
	private static SoapClient soapClient;
	private static CustomerService customerService;
	private static String customer_id;
	private static String sku;
	private static ProductImageService productImageService;
	private static ProductImage product_image;

	private void init() throws Exception {
		soapClient = new SoapClient();
		customerService = new CustomerService(soapClient);
		productImageService = new ProductImageService(soapClient);
		Customer customer = new Customer();
		customer.setFirstName("Lv");
		customer.setLastName("Xingang");
		customer.setPasswordHash(MessageParser.md5sum("password"));
		customer.setEmail(UUID.randomUUID() + "@gmail.com");
		customer_id = customerService.createCustomerWithProduct(customer);
		customer = customerService.getCustomerDetail(customer_id);
		sku = customer.getPrefix();
		FileInputStream fileInputStream;
		for (int i = 0; i < 1; i++) {
			fileInputStream = new FileInputStream("C:/tmp/v2.jpg");
			product_image = productImageService.uploadImageToProduct(sku,
					fileInputStream, UUID.randomUUID().toString() + "_v2");
		}
		soapClient.endSession();
	}

	public static void main(String[] args) {
		DataInit di = new DataInit();
		try {
			di.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
