package symbolTableClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MethodNode {
	public int noFunctionVars;
	public int noParams;
	public String functionName;
	public IdClass returnType;
	
	public String enclClass;
	
	public ArrayList<IdClass> paramStore;
	public ArrayList<IdClass> varStore;
	
	public Map<String, IdClass> paramMap;
	public Map<String, IdClass> varMap;
	
	public MethodNode(){
		this.noFunctionVars = this.noParams = 0;
		this.enclClass = null;
		paramStore = new ArrayList<IdClass>();
		varStore = new ArrayList<IdClass>();
		paramMap = new HashMap<String, IdClass>();
		varMap = new HashMap<String, IdClass>();
	}

	public boolean equals(MethodNode tmp){
		return this.functionName.equals(tmp.functionName);
	}
	
	public String toString() {
		return functionName + " " +returnType +" "+ noParams + " " + noFunctionVars + " " + enclClass +" " + paramStore + " " + varStore;
	}
}
