package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import visitor.ConstructCFG;
import dataStructures.ProcedureClass;
import dataStructures.TempClass;

public class LinearScan {
	private final int totalRegs = 18;
	private final int totalSRegs = 8;
	private final int totalTRegs = 10;
	private ArrayList<TempClass> allTemps = ConstructCFG.tempSeen;
	
	private PriorityQueue<Integer> sRegFree = new PriorityQueue<Integer>();
	private PriorityQueue<Integer> tRegFree = new PriorityQueue<Integer>();
	
	private PriorityQueue<TempClass> active = new PriorityQueue<TempClass>(totalRegs, new Comparator<TempClass>() {
		@Override
		public int compare(TempClass a, TempClass b) {
			if(a.end == b.end)
				return a.tmpNumber - b.tmpNumber;
			return a.end - b.end;
		}
	});

	private PriorityQueue<TempClass> activeRev = new PriorityQueue<TempClass>(totalRegs, Collections.reverseOrder(active.comparator()));
	
	public void AllocateRegister(){
		
		active.clear();
		activeRev.clear();
		
		for(int i = 0; i < totalSRegs; ++i)
			sRegFree.add(i);
		for(int i = 0; i < totalTRegs; ++i)
			tRegFree.add(i);
		
		Collections.sort(allTemps);
		
		for( TempClass i : allTemps){
			checkExpire(i);
			if(active.size() == totalRegs){
				spillReg(i);
			}
			else{
				if(!sRegFree.isEmpty()){
					i.RegNum = sRegFree.poll();
					i.RegType = 's';
				}
				else{
					i.RegNum = tRegFree.poll();
					i.RegType = 't';
				}
				active.add(i);
				activeRev.add(i);
			}
		}
		FindMaxForProc();
	}
	
	private void checkExpire(TempClass i){
		while(!active.isEmpty()){
			if(active.peek().end >= i.start)
				return;
			TempClass j =  active.poll();
			activeRev.remove(j);
			if(j.RegType == 's')
				sRegFree.add(j.RegNum);
			else
				tRegFree.add(j.RegNum);
		}
	}
	
	private void spillReg(TempClass i){
		TempClass j = activeRev.peek();
		if(j.end > i.end){
			j = activeRev.poll();
			active.remove(j);
			i.RegType = j.RegType;
			i.RegNum = j.RegNum;
			j.RegType = 'a';
			j.RegNum = 0;
			active.add(i);
			activeRev.add(i);
		}
		else{
			i.RegType = 'a';
			i.RegNum = 0;
		}
	}
	
	private void FindMaxForProc(){
		String curProc = "MAIN";
		int sreg = -1, treg = -1, spilled = 0;
		ProcedureClass tmpClass;
		for(TempClass i : allTemps){
			if(!curProc.equals(i.parentProc)){
				tmpClass =  ConstructCFG.procMap.get(curProc);
				sreg++;
				treg++;
				tmpClass.noSregs = sreg;
				tmpClass.noTregs = treg;
				tmpClass.totSpilled = spilled;
				tmpClass.numExtraMem = (tmpClass.numArgs > 4 ? tmpClass.numArgs - 4 : 0) 
						+ spilled + (!curProc.equals("MAIN") ?  tmpClass.noSregs : 0) + (tmpClass.hasCalls ? treg : 0);
				sreg = treg = -1;
				spilled = 0;
				curProc = i.parentProc;
			}
			if(i.RegType == 's' && i.RegNum > sreg)
				sreg = i.RegNum;
			if(i.RegType == 't' && i.RegNum > treg)
				treg = i.RegNum;
			if(i.RegType =='a')
				++spilled;
		}
		tmpClass =  ConstructCFG.procMap.get(curProc);
		++sreg;
		++treg;
		tmpClass.noSregs = sreg;
		tmpClass.noTregs = treg;
		tmpClass.totSpilled = spilled;
		tmpClass.numExtraMem = (tmpClass.numArgs > 4 ? tmpClass.numArgs - 4: 0) + spilled 
				+ (!curProc.equals("MAIN") ?  tmpClass.noSregs : 0) + treg;
		
		curProc = "MAIN";
		int j = 0;
		for(TempClass i : allTemps){
			if(!curProc.equals(i.parentProc)){
				j = 0;
				curProc = i.parentProc;
			}
			if(i.RegType == 'a'){
				tmpClass = ConstructCFG.procMap.get(curProc);
				i.RegNum = (!curProc.equals("MAIN") ?  tmpClass.noSregs : 0) + (tmpClass.numArgs > 4 ? tmpClass.numArgs - 4: 0) 
						+(tmpClass.hasCalls ? tmpClass.noTregs : 0) + (j++);
			}
		}
	}
}