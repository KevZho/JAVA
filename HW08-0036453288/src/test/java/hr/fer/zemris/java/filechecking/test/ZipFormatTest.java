package hr.fer.zemris.java.filechecking.test;

import hr.fer.zemris.java.filechecking.syntax.node.ZIPFormat;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class ZipFormatTest {

	@Test
	public void acceptFalseTest() {
		File f = new File(Paths.get("./provjere.txt").toString());	
		Assert.assertNotNull(f);
		ZIPFormat node = new ZIPFormat(null, null, false);
		Assert.assertFalse(node.acceptType(f));
	}

	@Test
	public void getZipContentTest() {
		File f = new File(Paths.get("./provjere.txt").toString());	
		Assert.assertNotNull(f);
		ZIPFormat node = new ZIPFormat(null, null, false);
		Assert.assertNull(node.getFileContent(f));
	}
}
