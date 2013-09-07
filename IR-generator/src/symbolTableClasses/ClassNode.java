package symbolTableClasses;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClassNode {

	public List<String> memlst;
	public List<String> varlst;
	
	public String className;
	public String parentClass;
	
	public HashMap<String, MethodNode> memMap;
	public HashMap<String, IdClass> varMap;
	
	public List<ClassNode> ancesters;
	
	public ClassNode( String name ) {
		this.memlst = new ArrayList<String>();
		this.varlst = new ArrayList<String>();
		this.ancesters = new ArrayList<ClassNode>();
		this.parentClass = null;
		this.className = name;
		this.memMap = new HashMap<String, MethodNode>();
		this.varMap = new HashMap<String, IdClass>();
	}
	
	public ClassNode ( String name, String extendThis ) {
		this.className = name;
		this.memMap = new HashMap<String, MethodNode>();
		this.varMap = new HashMap<String, IdClass>();
		this.ancesters = new ArrayList<ClassNode>();
		this.varlst = new ArrayList<String>();
		this.parentClass = extendThis;
		this.memlst = new ArrayList<String>();
	}
	
	public void addMethod( String name, MethodNode memObj ) {
		this.memlst.add(name);
		this.memMap.put(name, memObj);
	}
	
	public void addVariable(IdClass var) {
		this.varlst.add(var.varName);
		this.varMap.put(var.varName, var);
	}
	
	/*public String toString() {
		String s = "[";
		for( ClassNode c : ancesters ) s += c.className + ",";
		s += "]";
		return "\nName: " + this.className + "\nMethods: " + this.memMap.toString() + "\nglobal vars: " + this.varMap.toString() + "\nextends: " + s + "\ndirectly extends: " + this.parentClass + "\n";
	}*/
	
}
