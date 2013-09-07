import java.io.FileInputStream;
import java.io.InputStream;

import syntaxtree.*;
import visitor.*;

public class Codegen {
	public static void main(String[] args) {
		try {
			// use the following line only in IDE
			// Node root = new MiniJavaParser(new
			// FileInputStream("/home/sharmistha/jtb_javacc_demo/Factorial.java")).Goal();

			// Final submission file should take input from command line
			Node root = new MiniRAParser(System.in).Goal();

			root.accept(new GenerateMIPS()); // Your assignment part is
													// invoked here.
			GenerateMIPS.mandatoryPrint();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}