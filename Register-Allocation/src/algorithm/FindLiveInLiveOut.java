package algorithm;

import java.util.ArrayList;

import visitor.ConstructCFG;

import dataStructures.BasicBlock;
import dataStructures.ProcedureClass;
import dataStructures.TempClass;

public class FindLiveInLiveOut {
	
	public void CalcLiveRange(){
		boolean flag = false;

		for( ProcedureClass proc : ConstructCFG.procList ){
			while(true){
				for( BasicBlock blk : proc.stmtLst ){
					blk.liveInp = getCopy(blk.liveIn);
					blk.liveOutp = getCopy(blk.liveOut);
					blk.liveIn = findUnion(blk.use, doSubtract(blk.liveOut, blk.def));
					blk.liveOut = blk.successors.size() == 0 ? new ArrayList<TempClass>() : 
						(blk.successors.size() == 1 ? getCopy(blk.successors.get(0).liveIn) : 
							(findUnion(blk.successors.get(0).liveIn, blk.successors.get(1).liveIn))); 
				}
				flag = false;
				for( BasicBlock blk : proc.stmtLst ){
					if(!compareLst(blk.liveIn, blk.liveInp) || !compareLst(blk.liveOut, blk.liveOutp)){
						flag = true;
						break;
					}
				}
				if(!flag)
					break;
			}
		}
	}
	
	private boolean compareLst(ArrayList<TempClass> lst1, ArrayList<TempClass> lst2){
		if(lst1.size() != lst2.size())
			return false;
		
		boolean found = false;
		
		for(TempClass i : lst1){
			found = false;
			for(TempClass j : lst2){
				if(j.equals(i)){
					found = true;
					break;
				}
			}
			if(!found)
				return false;
		}
		return true;
	}
	
	private <T> ArrayList<T> getCopy(ArrayList<T> lst1 ){
		return new ArrayList<T>(lst1);
	}
	
	private <T> ArrayList<T> findUnion(ArrayList<T> lst1, ArrayList<T> lst2 ){
		ArrayList<T> union = new ArrayList<T>(lst1);
		union.removeAll(lst2);
		union.addAll(lst2);
		return union;
	}
	
	private <T> ArrayList<T> doSubtract(ArrayList<T> lst1, ArrayList<T> lst2){
		ArrayList<T> sub = new ArrayList<T>(lst1);
		sub.removeAll(lst2);
		return sub;
	}
}
