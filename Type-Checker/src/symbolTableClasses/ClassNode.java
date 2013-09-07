package symbolTableClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassNode {
	public int noMemberVariables;
	public int noMemberFunctions;
	public String className;
	
	public String parentClass;
	
	public ArrayList<IdClass> varStore;
	public ArrayList<MethodNode> memFunctions;
	public Map<String, IdClass> varMap;
	public Map<String, MethodNode> memMap;
	
	public ClassNode(){
		this.noMemberFunctions = this.noMemberVariables = 0;
		this.parentClass = null;
		varStore = new ArrayList<IdClass>();
		memFunctions = new ArrayList<MethodNode>();
		varMap = new HashMap<String, IdClass>();
		memMap = new HashMap<String, MethodNode>();
	}
	
	public boolean equals(ClassNode tmp){
		return this.className.equals(tmp.className);
	}
	
	public String toString() {
		return className + " " + noMemberVariables + " " + noMemberFunctions + " " + varStore + " " +  memFunctions;
	}
}
