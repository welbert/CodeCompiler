package welbert.codecompiler.staticsvalues;

public class Config {
	public static final String version = "0.8";
	public static final boolean log = true;
	public static boolean WINDOWS = System.getProperty("os.name").contains("Windows");
	
	public static final String templateC = "#include <stdio.h>\n\nint main(void) {\n	// your code goes here\n"+
	"	return 0;\n}";
	public static final String templateCpp = "#include <iostream>\nusing namespace std;\n\nint main(void) {\n"+
	"	// your code goes here\n	return 0;\n}";
	public static final String templateJava = "import java.util.*;\nimport java.lang.*;\nimport java.io.*;\n\n"
			+ "class CodeCompiler{\n	public static void main (String[] args){\n		"
			+ "// your code goes here\n	}\n}";
	
	public static final int MAXTIMELIMIT = 30;
}
