package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import ksoap2.api.ProductService;
import ksoap2.soap.SoapClient;
import ksoap2.api.Product;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductServiceTest {

	private static SoapClient soapClient;
	private static ProductService productService;
	private static String product_id;
	private static Product product;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		soapClient = new SoapClient();
		productService = new ProductService(soapClient);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		soapClient.endSession();
	}

	@Test
	public final void testCreateProduct() {
		Product p1 = new Product();
		p1.setName("Product Name");
		String sku = UUID.randomUUID().toString();
		p1.setSku(sku);
		try {
			product_id = productService.CreateProduct(p1);
			System.out.println(product_id);
			assertNotNull(product_id);
			product = productService.getProductDetailsByID(product_id);
			System.out.println(product);
			assertEquals(product.getSku(), sku);
			assertEquals(product.getProduct_id(), product_id);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public final void testGetProductDetailsByID() {
		try {
			System.out.println("product_id=" + product_id);
			Product p1 = productService.getProductDetailsByID(product_id);
			assertNotNull(p1);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public final void testGetAllProductFull() {
		ArrayList<Product> products = productService.getAllProductFull();
		assertTrue(products.size() > 0);
	}

	@Test
	public final void testDeleteProduct() {
		assertNotNull(product);
		Boolean result = productService.deleteProduct(product_id);
		assertTrue(result);
		try {
			Product p1 = productService.getProductDetailsByID(product_id);
			assertNull(p1);
		} catch (Exception e) {
		}

	}

}
