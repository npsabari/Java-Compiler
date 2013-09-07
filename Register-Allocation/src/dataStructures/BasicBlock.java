package dataStructures;

import java.util.ArrayList;

public class BasicBlock {
	public int LineNum;
	public ArrayList<BasicBlock> successors, predecessors;
	public ArrayList<TempClass> liveIn, liveOut, def, use, liveInp, liveOutp;
	
	public BasicBlock(int num){
		this.LineNum = num;
		successors = new ArrayList<BasicBlock>();
		predecessors = new ArrayList<BasicBlock>();
		liveIn = new ArrayList<TempClass>();
		liveOut = new ArrayList<TempClass>();
		liveInp = new ArrayList<TempClass>();
		liveOutp = new ArrayList<TempClass>();
		def = new ArrayList<TempClass>();
		use = new ArrayList<TempClass>();
	}
	public String tostr(){
		return "lineNum : " + LineNum + " successors : " + successors.size() + " predecessors : " + predecessors.size() + " def : " + def.size() + " use : " + use.size();
	}
	
	public String toString(){
		String str = "";
		for( TempClass i : liveIn )
			str += " " + i.tmpNumber;
		String str1 = "";
		for(TempClass i : liveOut)
			str1 += " " + i.tmpNumber; 
		return "lineNum : " + LineNum + " liveIn " + str + " liveOut " + str1;
	}
}
