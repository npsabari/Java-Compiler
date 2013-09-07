package symbolTableClasses;

public class IdClass {
	public boolean refornot;
	public boolean arrornot;
	public String type;
	public String name;
	
	public IdClass(String ty, String nam){
		this.refornot = false;
		this.arrornot = false;
		this.type = ty;
		this.name = nam;
	}
	public IdClass(boolean ref, boolean arr, String ty, String nam){
		this.refornot = ref;
		this.arrornot = arr;
		this.type = ty;
		this.name = nam;
	}
	
	public boolean equals(IdClass sec){
		return (this.refornot == sec.refornot && this.arrornot == sec.arrornot && this.type.equals(sec.type) ) ? true : false;
	}
	
	public String toString(){
		return refornot + " " + arrornot + " " + type + " " + name;
	}
}
