package hr.fer.zemris.java.tecaj.hw3;


import org.junit.Assert;
import org.junit.Test;

public class CStringTest {
		
	@Test
	public void fromString() {
		CString a = new CString("test1");
		Assert.assertEquals(a.toString().compareTo("test1"), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void illegalOffsetTest() {
		new CString(new char[] {'T', 'e', 's', 't'}, 5, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeOffsetTest() {
		new CString(new char[] {'T', 'e', 's', 't'}, -5, 1);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeLengthTest() {
		new CString(new char[] {'T', 'e', 's', 't'}, 0, -1);
	}
	
	@Test
	public void fromArrayAndOffsetTest() {
		CString a = new CString(new char[] {'T', 'e', 's', 't'}, 0, 4);
		Assert.assertEquals(a.toString().compareTo("Test"), 0);
	}
	
	@Test
	public void fromArrayTest() {
		CString a = new CString(new char[] {'T', 'e', 's', 't'});
		Assert.assertEquals(a.toString().compareTo("Test"), 0);
	}
	
	/**
	 * Provjera djeljenog polja.
	 */
	@Test
	public void stringCopyTest() {
		CString a = new CString(new char[] {'T', 'e', 's', 't'});
		CString b = new CString(a);
		
		Assert.assertTrue(a.equals(b));
	}
	
	/**
	 * Provjera djeljenog polja.
	 */
	@Test
	public void stringCopyAllocationTest() {
		CString a = new CString(new char[] {'T', 'e', 's', 't'});
		CString b = a.substring(1, 4);
		CString c = new CString(b);
		
		Assert.assertFalse(b.equals(c));
	}
	
	@Test
	public void charAtTest() {
		Assert.assertEquals('e', new CString("Test").charAt(1));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void charAtFailTest() {
		new CString("test").charAt(10);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void charAtNegativeTest() {
		new CString("test").charAt(-1);
	}
	
	@Test
	public void lengthTest() {
		CString a = new CString("Test duljine stringa");
		Assert.assertEquals(new String("Test duljine stringa").length(), a.length());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullPointerTest() {
		new CString((CString)null);
	}
	
	@Test
	public void toCharArrayTest() {
		CString a = new CString("Testiranje polja.");
		char[] array = a.toCharArray();
		
		Assert.assertArrayEquals(new String("Testiranje polja.").toCharArray(), array);
	}
	
	@Test
	public void indexOfNegativeTest() {
		CString a = new CString("Test");
		
		Assert.assertEquals(-1, a.indexOf('z'));
	}
	
	@Test
	public void indexOfTest() {
		CString a = new CString("Test");
		
		Assert.assertEquals(1, a.indexOf('e'));
	}
	
	@Test
	public void startWithTooLongTest() {
		CString a = new CString("Test");
		Assert.assertEquals(false, a.startsWith(new CString("Test1")));
	}
	
	@Test
	public void startWithFalseTest() {
		CString a = new CString("Test");
		Assert.assertEquals(false, a.startsWith(new CString("ATes")));
	}
	
	@Test
	public void startWithTrueTest() {
		CString a = new CString("Test");
		Assert.assertEquals(true, a.startsWith(new CString("Tes")));
	}
	
	@Test
	public void endWithTooLongTest() {
		CString a = new CString("Test");
		Assert.assertEquals(false, a.endsWith(new CString("Test1")));
	}
	
	@Test
	public void endWithFalseTest() {
		CString a = new CString("Test");
		Assert.assertEquals(false, a.endsWith(new CString("a")));
	}
	
	@Test
	public void endWithTrueTest() {
		CString a = new CString("Test");
		Assert.assertEquals(true, a.endsWith(new CString("est")));
	}
	
	@Test
	public void containsTooLongTest() {
		CString a = new CString("programski jezik.");
		CString b = new CString("Programski jezik Java");
		Assert.assertEquals(false, a.contains(b));
	}
	
	@Test
	public void containsFalseTest() {
		CString a = new CString("Java programski jezik.");
		Assert.assertEquals(false, a.contains(new CString("C++")));
	}
	
	@Test
	public void constainTrueTest() {
		CString a = new CString("Java programski jezik.");
		Assert.assertEquals(true, a.contains(new CString("programski")));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void substringFailTest() {
		new CString("Programski jezik").substring(0, 100);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void negativeStartIndexSubstring() {
		new CString("Programski jezik").substring(-10, 100);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void wrongEndIndexTest() {
		new CString("Programski jezik").substring(10, 9);
	}
	
	@Test
	public void substringTest() {
		CString a = new CString("Programski jezik");
		String b = a.substring(11, 16).toString();
		
		Assert.assertEquals(b.compareTo("jezik"), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void leftNegativeTest() {
		new CString(new char[] {'M', 'a', 'r', 'k', 'o'}).left(-6);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void leftFailTest() {
		new CString(new char[] {'M', 'a', 'r', 'k', 'o'}).left(6);
	}
	
	@Test
	public void leftTest() {
		CString a = new CString(new char[] {'M', 'a', 'r', 'k', 'o'});
		String b = a.left(3).toString();
		
		Assert.assertEquals(b.compareTo("Mar"), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rightNegativeTest() {
		new CString(new char[] {'M', 'a', 'r', 'k', 'o'}).right(-10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rightFailTest() {
		new CString(new char[] {'M', 'a', 'r', 'k', 'o'}).right(10);
	}
	
	@Test
	public void rightTest() {
		CString a = new CString(new char[] {'M', 'a', 'r', 'k', 'o'});
		String b = a.right(3).toString();
		
		Assert.assertEquals(b.compareTo("rko"), 0);
	}
	
	@Test
	public void addEmptyTest() {
		CString a = new CString("bazni");
		CString b = a.add(new CString(""));
		
		Assert.assertTrue(a.equals(b));
	}
	
	@Test
	public void addTest() {
		CString a = new CString("bazni");
		CString b = a.add(new CString(" razred"));
		
		Assert.assertEquals(b.toString().compareTo("bazni razred"), 0);
	}
	
	@Test
	public void replaceCharTest() {
		CString a = new CString("Popocatepetl");
		String b = a.replaceAll('e','a').toString();
		
		Assert.assertEquals(b.compareTo("Popocatapatl"), 0);
	}
	
	@Test
	public void replaceSameCharTest() {
		CString a = new CString("Popocatepetl");
		CString b = a.replaceAll('e','e');
		
		//moraju dijeliti isto polje
		Assert.assertTrue(a.equals(b));
	}
	
	@Test
	public void replaceStringTest() {
		CString a = new CString("ababab").replaceAll(new CString("ab"), new CString("abab"));
		String b = new String("ababab").replaceAll(new String("ab"), new String("abab"));
	
		Assert.assertEquals(a.toString().compareTo(b), 0);
	}
	
	@Test
	public void replaceStringTest2() {
		CString a = new CString("java tecaj");
		a = a.replaceAll(new CString("java"), new CString("Java"));
		
		String b = new String("java tecaj");
		b = b.replaceAll("java", "Java");
		
		Assert.assertEquals(a.toString().compareTo(b), 0);
	}
	
	@Test
	public void replaceStringTest3() {
		CString a = new CString("knjiga");
		CString b = a.replaceAll(new CString("fer"), new CString("FER"));
				
		Assert.assertEquals(b.toString().compareTo("knjiga"), 0);
	}
	
	@Test
	public void startsWithEmptyStringTest() {
		CString a = new CString("Test");
		
		Assert.assertTrue(a.startsWith(new CString("")));
	}
	
	@Test
	public void endsWithEmptyStringTest() {
		CString a = new CString("Test");
		
		Assert.assertTrue(a.endsWith(new CString("")));
	}
	
	@Test
	public void containsEmptyStringTest() {
		CString a = new CString("Test");
		
		Assert.assertTrue(a.contains(new CString("")));
	}
	
	@Test
	public void notCStringObject() {
		Assert.assertFalse(new CString("ab").equals(null));
	}
}
