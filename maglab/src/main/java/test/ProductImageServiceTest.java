package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

import ksoap2.api.Product;
import ksoap2.api.ProductImage;
import ksoap2.api.ProductImageService;
import ksoap2.api.ProductService;
import ksoap2.soap.SoapClient;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductImageServiceTest {
	private static SoapClient soapClient;
	private static ProductService productService;
	private static ProductImageService productImageService;
	private static String product_id;
	private static Product product;
	private static ProductImage product_image;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		soapClient = new SoapClient();
		productService = new ProductService(soapClient);
		productImageService = new ProductImageService(soapClient);
		Product p1 = new Product();
		p1.setName("Product Name");
		String sku = UUID.randomUUID().toString();
		p1.setSku(sku);
		try {
			product_id = productService.CreateProduct(p1);
			System.out.println(product_id);
			assertNotNull(product_id);
			product = productService.getProductDetailsByID(product_id);
			assertEquals(product.getSku(), sku);
			assertEquals(product.getProduct_id(), product_id);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		productService.deleteProduct(product_id);
		soapClient.endSession();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testUploadImageToProduct() {
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("C:/tmp/v2.jpg");
			product_image = productImageService.uploadImageToProduct(
					product_id, fileInputStream);
			System.out.println(product_image);
			assertNotNull(product_image);
		} catch (FileNotFoundException e) {
			fail(e.toString());
			e.printStackTrace();
		}
	}

	@Test
	public final void testGetProductImageByPosition() {
		try {
			ProductImage pimg = productImageService.getProductImageByPosition(
					product_id, product_image.getPosition());
			assertEquals(pimg.getFile(), product_image.getFile());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public final void testListProductImage() {
		ArrayList<ProductImage> pimgs = productImageService
				.listProductImage(product_id);
		assertTrue(pimgs.size() == 1);
		for (ProductImage pimg : pimgs) {
			assertTrue(pimg.getFile().indexOf("v2") > 0);
		}
	}

	@Test
	public final void testDeleteProductImage() {
		Boolean result = productImageService.deleteProductImage(product_id,
				product_image.getPosition());
		assertTrue(result);
		ArrayList<ProductImage> pimgs = productImageService
				.listProductImage(product_id);
		assertTrue(pimgs.size() == 0);
	}
}
