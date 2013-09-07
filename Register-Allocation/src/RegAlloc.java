import algorithm.FindLiveInLiveOut;
import algorithm.FindLiveRange;
import algorithm.LinearScan;
import syntaxtree.*;
import visitor.*;

public class RegAlloc{
   public static void main(String [] args) {
      try {
         Node root = new microIRParser(System.in).Goal();
//         root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
         root.accept( new ConstructCFG());
         new FindLiveInLiveOut().CalcLiveRange();
//         for(ProcedureClass i : ConstructCFG.procList){
//        	 for( BasicBlock blk : i.stmtLst){
//        		 System.out.println(blk.toString());
//        	 }
//         }
         new FindLiveRange().FindTempLiveRange();
//         for(TempClass i : ConstructCFG.tempSeen){
//        	 System.out.println(i.tmpNumber+ " " + i.start + " " + i.end);
//         }
         new LinearScan().AllocateRegister();
         root.accept(new PrintMiniRA());
//         PrintMiniRA.print();
//         for(TempClass i : ConstructCFG.tempSeen){
//        	 System.out.println(i.tmpNumber+ " " + i.RegNum + " " + i.RegType + " "+i.parentProc);
//         }
//         System.out.println(PrintMiniRA.strng);
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}



