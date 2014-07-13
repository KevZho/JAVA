package hr.fer.zemris.java.webserver.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import org.junit.Assert;
import org.junit.Test;

/**
 * Razred za testiranje RequestContexta.
 * @author Igor Smolkovič
 *
 */
public class RequestContextTest {

	private RequestContext requestContext;
	
	private ByteArrayOutputStream output;
	
	@Test(expected=IllegalArgumentException.class)
	public void constructorTest() {
		new RequestContext(null, null, null, null);
	}
	
	@Test
	public void constructorTest1() {
		new RequestContext(System.out, null, null, null);
	}

	@Test
	public void constructorTest2() {
		new RequestContext(System.out, new HashMap<String, String>(), null, null);
	}
	
	@Test
	public void constructorTest3() {
		new RequestContext(System.out, null, new HashMap<String, String>(), null);
	}
	
	@Test
	public void constructorTest4() {
		new RequestContext(System.out, null, null, new ArrayList<RCCookie>());
	}
	
	@Test
	public void RCCookieTest() {
		RequestContext.RCCookie cookie = new RCCookie("user", "igor", 3600, "server.fer.hr", "/");
		Assert.assertEquals("Name nije user", "user", cookie.getName());
		Assert.assertEquals("Value nije igor", "igor", cookie.getValue());
		Assert.assertEquals("Max-Age nije 3600", Integer.valueOf(3600), cookie.getMaxAge());
		Assert.assertEquals("Path nije /", "/", cookie.getPath());
		Assert.assertEquals("Domain nije server.fer.hr", "server.fer.hr", cookie.getDomain());
	}

	private void init() {
		Map<String, String> parameters = new HashMap<>();
		parameters.put("param1", "value1");
		parameters.put("param2", "value2");
		output = new ByteArrayOutputStream();
		requestContext = new RequestContext(output, parameters, null, null);
		requestContext.setPersistentParameter("user", "igor");
		requestContext.setPersistentParameter("password", "igor");
		requestContext.setTemporaryParameter("a", "1");
		requestContext.setTemporaryParameter("b", "2");
	}
	
	private void closeStream(OutputStream stream) {
		try {
			stream.close();
		} catch (Exception ignorable) { }
	}
	
	@Test
	public void getPersistentParametersTest() {
		init();
		Set<String> names = requestContext.getPersistentParameters();
		Set<String> set = new HashSet<>();
		set.add("user");
		set.add("password");
		Assert.assertEquals("Setovi nisu isti", set, names);
		closeStream(output);
	}
	
	@Test
	public void getPersistentParameterTest() {
		init();
		Assert.assertEquals("Vrijednost nije igor", "igor", requestContext.getPersistentParameter("user"));
		closeStream(output);
	}	
	
	@Test
	public void removePersistentParameterTest() {
		init();
		requestContext.removePersistentParameter("user");
		Assert.assertEquals("Vrijednost nije null", null, requestContext.getPersistentParameter("user"));
		closeStream(output);
	}
	
	@Test
	public void getTemporaryParametersTest() {
		init();
		Set<String> names = requestContext.getTemporaryParameters();
		Set<String> set = new HashSet<>();
		set.add("a");
		set.add("b");
		Assert.assertEquals("Setovi nisu isti", set, names);
		closeStream(output);
	}
	
	@Test
	public void getTemporaryParameterTest() {
		init();
		Assert.assertEquals("Vrijednost nije 1", "1", requestContext.getTemporaryParameter("a"));
		closeStream(output);
	}
	
	@Test
	public void removeTemporaryParameterTest() {
		init();
		requestContext.removeTemporaryParameter("a");
		Assert.assertEquals("Vrijednost nije null", null, requestContext.getTemporaryParameter("a"));
		closeStream(output);
	}
	
	@Test
	public void getParameterNamesTest() {
		init();
		Set<String> names = requestContext.getParameterNames();
		Set<String> set = new HashSet<>();
		set.add("param1");
		set.add("param2");
		Assert.assertEquals("Setovi nisu isti", set, names);
		closeStream(output);
	}
	
	@Test
	public void getParameterTest() {
		init();
		Assert.assertEquals("Vrijednost nije value1", "value1", requestContext.getParameter("param1"));
		closeStream(output);
	}
	
	@Test
	public void writeDefaultHeaderTest() {
		init();
		requestContext.write("Test".getBytes(StandardCharsets.UTF_8));
		requestContext.write("šž.");
		requestContext.write("Super.".getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n"
				+ "\r\n";
		try {
			expected.write(header.getBytes(StandardCharsets.ISO_8859_1));
			expected.write("Testšž.Super.".getBytes(StandardCharsets.UTF_8));
		} catch (Exception ignorable) { }
		Assert.assertEquals("Sadržaji stremova nisu isti", expected.toString(), output.toString());
		closeStream(output);
		closeStream(expected);
	}
	
	@Test
	public void changeHeaderTest() {
		init();
		requestContext.setEncoding("UTF-8");
		requestContext.setMimeType("image/jpg");
		requestContext.setStatusCode(204);
		requestContext.setStatusText("No Content");
		requestContext.write("Test".getBytes(StandardCharsets.UTF_8));
		requestContext.write("šž.");
		requestContext.write("Super.".getBytes(StandardCharsets.UTF_8));
		
		
		requestContext.setEncoding("UTF-8");
		requestContext.setMimeType("text/html");
		requestContext.setStatusCode(200);
		requestContext.setStatusText("OK");
		
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		String header = "HTTP/1.1 204 No Content\r\n"
				+ "Content-Type: image/jpg\r\n"
				+ "\r\n";
		try {
			expected.write(header.getBytes(StandardCharsets.ISO_8859_1));
			expected.write("Testšž.Super.".getBytes(StandardCharsets.UTF_8));
		} catch (Exception ignorable) { }
		Assert.assertEquals("Sadržaji stremova nisu isti", expected.toString(), output.toString());
		
		closeStream(output);
		closeStream(expected);
	}

	@Test
	public void cookieTest() {
		output = new ByteArrayOutputStream();
		requestContext = new RequestContext(output, null, null, null);
		requestContext.addRCCookie(new RCCookie("user", "igor", null, null, null));
		requestContext.addRCCookie(new RCCookie("user", "igor", 3600, null, null));
		requestContext.addRCCookie(new RCCookie("user", "igor", 3600, "server.fer.hr", null));
		requestContext.addRCCookie(new RCCookie("user", "igor", 3600, "server.fer.hr", "/"));
		requestContext.addRCCookie(new RCCookie("sid", "abcd", null, "server.fer.hr", "/", true));
		
		requestContext.write("Test.");
		requestContext.write("Super.".getBytes(StandardCharsets.UTF_8));
		ByteArrayOutputStream expected = new ByteArrayOutputStream();
		String header = "HTTP/1.1 200 OK\r\n"
				+ "Content-Type: text/html; charset=UTF-8\r\n"
				+ "Set-Cookie: user=\"igor\"\r\n"
				+ "Set-Cookie: user=\"igor\"; Max-Age=3600\r\n"
				+ "Set-Cookie: user=\"igor\"; Domain=server.fer.hr; Max-Age=3600\r\n"
				+ "Set-Cookie: user=\"igor\"; Domain=server.fer.hr; Path=/; Max-Age=3600\r\n"
				+ "Set-Cookie: sid=\"abcd\"; Domain=server.fer.hr; Path=/; HttpOnly\r\n"
				+ "\r\n";
		try {
			expected.write(header.getBytes(StandardCharsets.ISO_8859_1));
			expected.write("Test.Super.".getBytes(StandardCharsets.UTF_8));
		} catch (Exception ignorable) { }

		Assert.assertEquals("Sadržaji stremova nisu isti", expected.toString(), output.toString());
		closeStream(output);
		closeStream(expected);
	}
}
