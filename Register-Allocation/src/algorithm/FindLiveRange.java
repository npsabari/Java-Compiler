package algorithm;

import visitor.ConstructCFG;
import dataStructures.BasicBlock;
import dataStructures.TempClass;

public class FindLiveRange {
	
	public void FindTempLiveRange(){		
		int line = 0;
		
		for( TempClass i : ConstructCFG.tempSeen){
			for( BasicBlock blk : ConstructCFG.procMap.get(i.parentProc).stmtLst){
				line = blk.LineNum;
				if(blk.liveIn.contains(i) && i.end < line)
					i.end = line;
				if(blk.liveOut.contains(i) && i.start > line)
					i.start = line;
			}
			if(i.start == Integer.MAX_VALUE && i.end == Integer.MIN_VALUE){
				i.start = ConstructCFG.procMap.get(i.parentProc).lineStrt;
				i.end = ConstructCFG.procMap.get(i.parentProc).lineEnd;
			}
		}
		
		FixMultipleDefs();
	}
	
	private void FixMultipleDefs(){
		int line = 0;
		for( TempClass i : ConstructCFG.tempSeen){
			for(BasicBlock blk : ConstructCFG.procMap.get(i.parentProc).stmtLst){
				line = blk.LineNum;
				if(blk.def.contains(i) && line < i.start)
					i.start = line;
				if(blk.use.contains(i) && line > i.end)
					i.end = line;
			}
			i.liveRangeSize = i.end - i.start + 1;
		}
	}
}
