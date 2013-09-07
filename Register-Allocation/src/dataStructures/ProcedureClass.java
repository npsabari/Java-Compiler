package dataStructures;

import java.util.ArrayList;

public class ProcedureClass {
	public int numArgs, numExtraMem, maxCallArgs;
	public String name;
	public int lineStrt, lineEnd;
	public ArrayList<BasicBlock> stmtLst;
	public int noSregs, noTregs;
	public int totSpilled;
	public boolean hasCalls;
	
	public ProcedureClass(String name, int a, int line){
		this.name = name;
		this.numArgs = a;
		this.lineStrt = line;
		this.maxCallArgs = 0;
		stmtLst = new ArrayList<BasicBlock>();
		noSregs = noTregs = totSpilled = 0;
		hasCalls = false;
	}
	
	public boolean equals(ProcedureClass b){
		return (this.name.equals(b.name));
	}
}
