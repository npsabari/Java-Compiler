package symbolTableClasses;

public class IdClass {

	public String varName;
	public String varType;
	public int index;

	public IdClass( String name, String type, int index ) {
		this.varName = name;
		this.varType = type;
		this.index = index;
	}
	
	public IdClass( String name, String type ) {
		this.varName = name;
		this.varType = type;
	}
	
	/*public String toString() {
		return "Name: " + this.varName + "\nType: " + this.varType + "\nIndex: " + this.index;
	}*/
	
}
