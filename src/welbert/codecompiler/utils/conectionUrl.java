package welbert.codecompiler.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class conectionUrl {
	URL url = new URL("http://www.google.com");

	public conectionUrl(String url) throws MalformedURLException {
		super();
		this.url= new URL(url);
	}
	
	@SuppressWarnings("resource")
	public String getTitle() throws IOException{
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		String result = new Scanner(is).useDelimiter("\\A").next();
		is.close();
		return result.substring(result.indexOf("<title>")+7, result.indexOf("</title>"));
	}
	
}
