package ksoap2.soap;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class ImageHelper {
	public static final String imageToBase64(String filePath) throws IOException{
		FileInputStream fileInputStream = new FileInputStream(filePath);
		String  base64Format = new String(Base64.encodeBase64(IOUtils.toByteArray(fileInputStream)));
		return base64Format;
	}
	
	public static final String imageToBase64(InputStream io) throws IOException{
		String  base64Format = new String(Base64.encodeBase64(IOUtils.toByteArray(io)));
		return base64Format;
	}
}
