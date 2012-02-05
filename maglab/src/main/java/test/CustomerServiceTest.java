package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import ksoap2.api.Customer;
import ksoap2.api.CustomerService;
import ksoap2.api.Product;
import ksoap2.api.ProductService;
import ksoap2.soap.MessageParser;
import ksoap2.soap.SoapClient;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerServiceTest {
	private static SoapClient soapClient;
	private static CustomerService customerService;
	private static ProductService productService;
	private static String customer_id;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		soapClient = new SoapClient();
		customerService = new CustomerService(soapClient);
		productService = new ProductService(soapClient);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		soapClient.endSession();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setFirstName("jerry");
		customer.setLastName("jiang");
		customer.setPasswordHash(MessageParser.md5sum("password"));
		customer.setEmail(UUID.randomUUID() + "@gmail.com");
		customer_id = customerService.createCustomerWithProduct(customer);
		assertNotNull(customer_id);
	}

	@Test
	public final void testGetCustomerDetail() {
		try {
			Customer customer = customerService.getCustomerDetail(customer_id);
			System.out.println(customer);
			assertEquals(customer.getCustomer_id(), customer_id);
			Product product = productService.getProductDetailsByID(customer
					.getPrefix());
			assertNotNull(product);
			assertEquals(product.getSku(), customer.getPrefix());
			String product_name = product.getName();
			String p1 = product_name.split(":")[0];
			assertEquals(p1, customer_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public final void testGetAllCustomer() {
		ArrayList<Customer> customer = customerService.getAllCustomer();
		assertTrue(customer.size() > 0);
	}

	@Test
	public final void testDeleteCustomer() {
		try {
			Customer customer = customerService.getCustomerDetail(customer_id);

			Boolean result = customerService
					.deleteCustomerWithAssoiatedProduct(customer);
			assertTrue(result);

			Product product = productService.getProductDetailsByID(customer
					.getPrefix());
			assertNull(product);

			assertNull(customerService.getCustomerDetail(customer_id));
		} catch (Exception e) {
		}
	}
}
