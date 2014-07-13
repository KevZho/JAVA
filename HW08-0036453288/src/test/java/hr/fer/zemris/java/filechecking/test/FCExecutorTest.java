package hr.fer.zemris.java.filechecking.test;


import hr.fer.zemris.java.filechecking.FCFileVerifier;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class FCExecutorTest {

	@Test
	public void executeSimpleTest() {
		String program = InputPrograms.getSimpleProgram();
		File f = new File(Paths.get("./test/datoteka.zip").toString());
		FCFileVerifier verifier = new FCFileVerifier(f, "datoteka.zip", program, new HashMap<String, Object>());
		Assert.assertFalse(verifier.hasErrors());
	}
	
	@Test
	public void executeSimpleFileNotZipTest() {
		String program = InputPrograms.getSimpleProgram();
		File f = new File(Paths.get("./test/provjere.txt").toString());
		FCFileVerifier verifier = new FCFileVerifier(f, "datoteka.zip", program, new HashMap<String, Object>());
		Assert.assertTrue(verifier.hasErrors());
	}
	
	@Test
	public void executeComplexTest() {
		String program = InputPrograms.getComplexProgram();
		File f = new File(Paths.get("./test/0036453288-dz2.zip").toString());
		Map<String, Object> initData = new HashMap<>();
		initData.put("jmbag", "0036453288");
		
		FCFileVerifier verifier = new FCFileVerifier(f, "0036453288-dz2.zip", program, initData);
		Assert.assertFalse(verifier.hasErrors());
	}

	@Test
	public void executeTest() {
		String program = InputPrograms.getProgram();
		File f = new File(Paths.get("./test/test.zip").toString());
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, new HashMap<String, Object>());
		Assert.assertTrue(verifier.hasErrors());
	}

	@Test
	public void visitFailNode() {
		String program = "fail @\"Poruka\"";
		File f = new File(Paths.get("./test/test.zip").toString());
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, new HashMap<String, Object>());
		Assert.assertTrue(verifier.hasErrors());
	} 
	
	@Test
	public void visitFileName() {
		String program = InputPrograms.getTestFileName();
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		map.put("ime", "Igor");
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertTrue(verifier.hasErrors());
	}
	
	@Test
	public void undifiniedVariableTest() {
		String program = InputPrograms.getUndifiniedVariableProgram();
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertFalse(verifier.errors().isEmpty());
	}
	
	@Test
	public void visitFilenameDefaultErrorMsg() {
		String program = "filename i\"\test1.zip\" { terminate } terminate";
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertFalse(verifier.errors().isEmpty());
	}
	
	@Test
	public void visitFilenameErrorMsg() {
		String program = "filename i\"\test1.zip\" @\"Error msg\"{ terminate } terminate";
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertFalse(verifier.errors().isEmpty());
	}
	
	@Test
	public void visitFailDefaultErrorMsg() {
		String program = "fail";
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertFalse(verifier.errors().isEmpty());
	}
	
	@Test
	public void visitFailErrorMsg() {
		String program = "fail @\"Error msg\" {terminate}";
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertFalse(verifier.errors().isEmpty());
	}
	
	@Test
	public void visitExistsDefaultErrorMsg() {
		String program = "format zip { exists f \"build.xml\"  terminate } terminate";
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertFalse(verifier.errors().isEmpty());
	}
	
	@Test
	public void visitFormatNode() {
		String program = "!format zip @\"Datoteka mora biti zip\"{ exists f \"build.xml\"  terminate } terminate";
		File f = new File(Paths.get("./test/test.zip").toString());
		Map<String, Object> map = new HashMap<>();
		FCFileVerifier verifier = new FCFileVerifier(f, "test.zip", program, map);
		Assert.assertFalse(verifier.errors().isEmpty());
	}
}
