package dataStructures;

public class TempClass implements Comparable<TempClass>{
	public int tmpNumber, start, end;
	public int liveRangeSize;
	public String parentProc;
	public char RegType;
	public int RegNum;
	
	public TempClass(int num, String proc){
		this.tmpNumber = num;
		this.parentProc = proc;
		this.start = Integer.MAX_VALUE;
		this.end = Integer.MIN_VALUE;
		this.liveRangeSize = -1;
	}
	
	public boolean equals(TempClass a){
		return (this.tmpNumber == a.tmpNumber && this.parentProc.equals(a.parentProc));
	}

	@Override
	public int compareTo(TempClass a) {
		if(this.start == a.start)
			return this.tmpNumber - a.tmpNumber;
		return this.start - a.start;
	}
}