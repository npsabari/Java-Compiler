package symbolTableClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MethodNode{
	
	public String functionName;
	public String returnType;
	public HashMap<String , IdClass> localVarMap;
	public HashMap<String, IdClass> paramMap;
	public List<String> varStore;
	public String enclClass;
	
	public MethodNode(String name, String rtype, String parent) {
		this.functionName = name;
		this.returnType = rtype;
		this.enclClass = parent;
		this.localVarMap = new HashMap<String, IdClass>();
		this.paramMap = new HashMap<String, IdClass>();
		this.varStore = new ArrayList<String>();
	}
	
	public void addParameter( IdClass var ) {
		this.paramMap.put(var.varName, var);
		this.varStore.add(var.varType);
	}
	
	public void addVariable(IdClass var) {
		this.localVarMap.put(var.varName, var);
	}
	
	/*public String toString() {
		return "\nName: " + this.functionName + "\nReturn type: " + this.returnType + "\nLocal vars: " + this.localVarMap.toString() + "\nparamMap:" + this.paramMap.toString() + "\n";
	}*/

}
