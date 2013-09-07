//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class GJNoArguDepthFirst<R> implements GJNoArguVisitor<R> {
	
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	private Stack<Integer> tempIndex = new Stack<Integer>();
	private Stack<String> LabelStack = new Stack<String>();
	private int integral = 0;
	private int index = getMaxIndex.max+1;
	private boolean functionFlag = false, expLabel = false, expInt = false;
   public R visit(NodeList n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n) {
      if ( n.present() ){
    	  R _ret = n.node.accept(this);
    	  System.out.println(LabelStack.pop() + " NOOP");
    	  return _ret;
      }
      else
         return null;
   }

   public R visit(NodeSequence n) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n) { return null; }

   //
   // User-generated visitor methods below
   //

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n) {
      R _ret=null;
      n.f0.accept(this);
      System.out.println("MAIN");
      n.f1.accept(this);
      n.f2.accept(this);
      System.out.println("END");
      n.f3.accept(this);
      n.f4.accept(this);
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n) {
      R _ret=null;
      n.f0.accept(this);
      System.out.print(LabelStack.pop()+" ");
      n.f1.accept(this);
      System.out.print("[");
      n.f2.accept(this);
      System.out.print(integral);
      n.f3.accept(this);
      System.out.println("]");
      functionFlag = true;
      n.f4.accept(this);
      functionFlag = false;
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public R visit(Stmt n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      System.out.println(" NOOP");
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n) {
      R _ret=null;
      n.f0.accept(this);
      System.out.println(" ERROR");
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Exp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      System.out.println("CJUMP TEMP " + tempIndex.pop() + " " + LabelStack.pop());
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      System.out.println("JUMP " + LabelStack.pop());
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Exp()
    * f2 -> IntegerLiteral()
    * f3 -> Exp()
    */
   public R visit(HStoreStmt n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      Integer i = tempIndex.pop();
      System.out.println("HSTORE TEMP "+ tempIndex.pop()  + " " + n.f2.f0.tokenImage + " TEMP " + i);
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Exp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      // Assuming Temp() wont print anything, but will push the temp Number into the Queue
      n.f2.accept(this);
      n.f3.accept(this);
      int i = tempIndex.pop();
      System.out.println("HLOAD TEMP " + tempIndex.pop() + " TEMP " + i + " " + integral);
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      int i = tempIndex.pop();
      //Assuming Exp() and als Temp() will push the temp number irrespective of its type (  unlike as specified in the site )
      System.out.println("MOVE TEMP " + tempIndex.pop() + " TEMP " + i);
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> Exp()
    */
   public R visit(PrintStmt n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      System.out.println("PRINT TEMP " + tempIndex.pop());
      return _ret;
   }

   /**
    * f0 -> StmtExp()
    *       | Call()
    *       | HAllocate()
    *       | BinOp()
    *       | Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(Exp n) {
      R _ret=null;
      if(n.f0.which == 5)
    	  expInt = true;
      else if(n.f0.which == 6)
    	  expLabel = true;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> Exp()
    * f4 -> "END"
    */
   public R visit(StmtExp n) {
      R _ret=null;
      n.f0.accept(this);
      boolean tmpFlag = functionFlag;
      functionFlag = false;
      if(tmpFlag)
    	  System.out.println("BEGIN");
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      if(tmpFlag){
    	  System.out.println("RETURN");
    	  System.out.println("TEMP " + tempIndex.pop());
      }
      n.f4.accept(this);
      if(tmpFlag)
    	  System.out.println("END");
      functionFlag = tmpFlag;
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> Exp()
    * f2 -> "("
    * f3 -> ( Exp() )*
    * f4 -> ")"
    */
   public R visit(Call n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      n.f3.accept(this);
      n.f4.accept(this);
      Stack<Integer> tmpInd = new Stack<Integer>();
      //System.out.println(n.f3.nodes.size());
      for( int i = 0; i < n.f3.nodes.size(); ++i)
    	 tmpInd.push(tempIndex.pop());
      String str = " CALL TEMP " + tempIndex.pop() + "( ";
      for( int i = 0; i < n.f3.nodes.size(); ++i)
    	  str += "TEMP " + tmpInd.pop() + " "; 
      str += ")";
      System.out.println("MOVE TEMP " + index +  str);
      tempIndex.push(index);
      ++index;
      return _ret;
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> Exp()
    */
   public R visit(HAllocate n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      String str = " HALLOCATE TEMP " + tempIndex.pop();
      System.out.println("MOVE TEMP " + index + str );
      tempIndex.push(index);
      ++index;
      return _ret;
   }

   /**
    * f0 -> Operator()
    * f1 -> Exp()
    * f2 -> Exp()
    */
   public R visit(BinOp n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      n.f2.accept(this);
      String str = "";
      switch(n.f0.f0.which){
    	case 0:	str = " LT"; break;
    	case 1: str = " PLUS"; break;
    	case 2: str = " MINUS"; break;
    	case 3: str = " TIMES"; break;
      }
      int i = tempIndex.pop();
      System.out.println("MOVE TEMP " + index + str + " TEMP " + tempIndex.pop() + " TEMP " + i );
      tempIndex.push(index);
      ++index;
      return _ret;
   }

   /**
    * f0 -> "LT"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    */ 
   public R visit(Operator n) {
      R _ret=null;
      n.f0.accept(this);
      return _ret;
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n) {
      R _ret=null;
      n.f0.accept(this);
      n.f1.accept(this);
      tempIndex.push(integral);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */ 
   public R visit(IntegerLiteral n) {
      R _ret=null;
      n.f0.accept(this);
      integral = Integer.parseInt(n.f0.tokenImage);
      if(expInt){
    	  System.out.println("MOVE TEMP "+ index + " " + integral);
    	  tempIndex.push(index);
    	  ++index;
    	  expInt = false;
      }
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n) {
      R _ret=null;
      n.f0.accept(this);
      LabelStack.push(n.f0.tokenImage);
      if(expLabel){
    	  System.out.println("MOVE TEMP "+ index + " " + LabelStack.pop());
    	  tempIndex.push(index);
    	  ++index;
    	  expLabel = false;
      }
      return _ret;
   }

}
