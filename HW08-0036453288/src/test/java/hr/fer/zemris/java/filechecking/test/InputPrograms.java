package hr.fer.zemris.java.filechecking.test;

/**
 * Razred koji sadrži programe koji služe za testiranje.
 * @author Igor Smolkovič
 *
 */
public class InputPrograms {

	public static String getSimpleProgram() {
		StringBuilder builder = new StringBuilder();
		builder.append("filename i\"datoteka.zip\"");
		builder.append("format zip {").append("exists f \"build.xml\"\r\n").append("exists d \"bin\"");
		builder.append("terminate } terminate");
		
		return builder.toString();
	}
	
	public static String getComplexProgram() {
		StringBuilder builder = new StringBuilder();
		builder.append("filename i\"${jmbag}-dz2.zip\"");
		builder.append("# execute next block only if the format of the given file is ZIP \r\n format zip {");
		builder.append("def dir \"src\"");
		builder.append("def dir1 \"${dir}/main/java\"");
		builder.append("exists f \"${dir1}/IntegerStorage.java\" @\"error msg\"");
		builder.append("exists f \"${dir1}/ObserverExample.java\" { !exists f \"${dir1}/ObserverExample1.java\" } ");
		builder.append("terminate }");
		builder.append("terminate");
		
		return builder.toString();
	}
	
	public static String getProgram() {
		StringBuilder builder = new StringBuilder();
		builder.append("filename i\"test.zip\"").append(" format zip {")
				.append("exists f \"build.xml\" @\"Ne postoji build.xml\"")
				.append("!exists d \"test\" @\"Ne postoji bin\" { fail @\"Projekt ne bi trebao sadržavati test\" } }terminate");
		
		return builder.toString();
	}
	
	public static String getTestFileName() {
		StringBuilder builder = new StringBuilder();
		builder.append("!filename \"${ime}-dz4.zip\" {")
				.append("fail @\"Naziv je morao biti jmbag-dz4.zip\"")
				.append("!exists d \"super/cool:hr.fer.zemris.java\"")
				.append( " terminate}  terminate");
		return builder.toString();
	}
	
	public static String getUndifiniedVariableProgram() {
		return "def src \"${src1}/test\"";
	}
}
