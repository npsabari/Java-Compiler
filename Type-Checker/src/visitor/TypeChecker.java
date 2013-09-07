//
//Generated by JTB 1.3.2
//

package visitor;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Enumeration;
import java.util.Map;
import java.util.Stack;

import symbolTableClasses.ClassNode;
import symbolTableClasses.IdClass;
import symbolTableClasses.MethodNode;
import syntaxtree.AllocationExpression;
import syntaxtree.AndExpression;
import syntaxtree.ArrayAllocationExpression;
import syntaxtree.ArrayAssignmentStatement;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.ArrayType;
import syntaxtree.AssignmentStatement;
import syntaxtree.Block;
import syntaxtree.BooleanType;
import syntaxtree.BracketExpression;
import syntaxtree.ClassDeclaration;
import syntaxtree.ClassExtendsDeclaration;
import syntaxtree.CompareExpression;
import syntaxtree.Expression;
import syntaxtree.ExpressionList;
import syntaxtree.ExpressionRest;
import syntaxtree.FalseLiteral;
import syntaxtree.FormalParameter;
import syntaxtree.FormalParameterList;
import syntaxtree.FormalParameterRest;
import syntaxtree.Goal;
import syntaxtree.Identifier;
import syntaxtree.IfStatement;
import syntaxtree.IntegerLiteral;
import syntaxtree.IntegerType;
import syntaxtree.MainClass;
import syntaxtree.MessageSend;
import syntaxtree.MethodDeclaration;
import syntaxtree.MinusExpression;
import syntaxtree.Node;
import syntaxtree.NodeList;
import syntaxtree.NodeListOptional;
import syntaxtree.NodeOptional;
import syntaxtree.NodeSequence;
import syntaxtree.NodeToken;
import syntaxtree.NotExpression;
import syntaxtree.PlusExpression;
import syntaxtree.PrimaryExpression;
import syntaxtree.PrintStatement;
import syntaxtree.Statement;
import syntaxtree.ThisExpression;
import syntaxtree.TimesExpression;
import syntaxtree.TrueLiteral;
import syntaxtree.Type;
import syntaxtree.TypeDeclaration;
import syntaxtree.VarDeclaration;
import syntaxtree.WhileStatement;

/**
* Provides default methods which visit each node in the tree in depth-first
* order.  Your visitors may extend this class.
*/
public class TypeChecker<R> implements GJNoArguVisitor<R> {
//
// Auto class visitors--probably don't need to be overridden.
//
	public static ArrayList<ClassNode> SymbolTableBackup;
	public static Map<String, ClassNode> ClassMapBackup;
	public ClassNode currentClass = null;
	public MethodNode currentMethod = null;
	public IdClass expType = null , pexpType = null;
	public Stack<IdClass> explist = new Stack<IdClass>();
	
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
	   if ( n.present() )
	      return n.node.accept(this);
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
	 * f0 -> MainClass()
	 * f1 -> ( TypeDeclaration() )*
	 * f2 -> <EOF>
	 */
	public R visit(Goal n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> "public"
	 * f4 -> "static"
	 * f5 -> "void"
	 * f6 -> "main"
	 * f7 -> "("
	 * f8 -> "String"
	 * f9 -> "["
	 * f10 -> "]"
	 * f11 -> Identifier()
	 * f12 -> ")"
	 * f13 -> "{"
	 * f14 -> PrintStatement()
	 * f15 -> "}"
	 * f16 -> "}"
	 */
	public R visit(MainClass n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   currentClass = ClassMapBackup.get(n.f1.f0.tokenImage);
	   n.f2.accept(this);
	   currentMethod = currentClass.memMap.get("main");
	   n.f3.accept(this);
	   n.f4.accept(this);
	   n.f5.accept(this);
	   n.f6.accept(this);
	   n.f7.accept(this);
	   n.f8.accept(this);
	   n.f9.accept(this);
	   n.f10.accept(this);
	   n.f11.accept(this);
	   n.f12.accept(this);
	   n.f13.accept(this);
	   n.f14.accept(this);
	   n.f15.accept(this);
	   currentMethod = null;
	   n.f16.accept(this);
	   currentClass = null;
	   return _ret;
	}
	
	/**
	 * f0 -> ClassDeclaration()
	 *       | ClassExtendsDeclaration()
	 */
	public R visit(TypeDeclaration n) {
	   R _ret=null;
	   n.f0.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> ( VarDeclaration() )*
	 * f4 -> ( MethodDeclaration() )*
	 * f5 -> "}"
	 */
	public R visit(ClassDeclaration n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   currentClass = ClassMapBackup.get(n.f1.f0.tokenImage);	   
	   n.f2.accept(this);
	   n.f3.accept(this);
	   n.f4.accept(this);
	   n.f5.accept(this);
	   currentClass = null;
	   return _ret;
	}
	
	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "extends"
	 * f3 -> Identifier()
	 * f4 -> "{"
	 * f5 -> ( VarDeclaration() )*
	 * f6 -> ( MethodDeclaration() )*
	 * f7 -> "}"
	 */
	public R visit(ClassExtendsDeclaration n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   currentClass = ClassMapBackup.get(n.f1.f0.tokenImage);
	   n.f2.accept(this);
	   n.f3.accept(this);
	   if(!ClassMapBackup.containsKey(currentClass.parentClass)){
//		   System.out.println("The class "+currentClass.className+" is extending unknown class "+ currentClass.parentClass);
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f4.accept(this);
	   n.f5.accept(this);
	   n.f6.accept(this);
	   n.f7.accept(this);
	   currentClass = null;
	   return _ret;
	}
	
	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 * f2 -> ";"
	 */
	public R visit(VarDeclaration n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   String varname = n.f1.f0.tokenImage;
	   if(currentMethod != null){
		   if(currentMethod.varMap.containsKey(varname)){
			   if( currentMethod.varMap.get(varname).refornot == true && !ClassMapBackup.containsKey(currentMethod.varMap.get(varname).type) ){
//				   System.out.println("Undefined Variable Type "+currentMethod.varMap.get(varname).type);
				   System.out.print("Type error");
				   System.exit(0);
			   }
		   }
		   else{
			   if( currentClass.varMap.get(varname).refornot == true && !ClassMapBackup.containsKey(currentClass.varMap.get(varname).type)){
//				   System.out.println("Undefined Variable Type "+currentClass.varMap.get(varname).type);
				   System.out.print("Type error");
				   System.exit(0);
			   }
		   }
	   }
	   else if(currentMethod == null && currentClass.varMap.get(varname).refornot == true && !ClassMapBackup.containsKey(currentClass.varMap.get(varname).type)){
//		   System.out.println("Undefined Variable Type "+currentClass.varMap.get(varname).type);
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   //Inheritance Check for overridden variable types
	   /*
	    if( currentMethod == null ){
		   ClassNode tmpClass = currentClass;
		   ClassNode tmpparen;
		   while( tmpClass.parentClass != null){
			   tmpparen = ClassMapBackup.get(tmpClass.parentClass);
			   if( tmpparen.varMap.containsKey(varname) && !tmpparen.varMap.get(varname).equals(currentClass.varMap.get(varname))){
//				   System.out.println("Overriding variable "+ varname +" are of, different types in "+ currentClass.className);
 					System.out.print("Type error");
				   System.exit(0);
			   }
			   tmpClass = tmpparen;
		   }
	   }*/
	   return _ret;
	}
	
	/**
	 * f0 -> "public"
	 * f1 -> Type()
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( FormalParameterList() )?
	 * f5 -> ")"
	 * f6 -> "{"
	 * f7 -> ( VarDeclaration() )*
	 * f8 -> ( Statement() )*
	 * f9 -> "return"
	 * f10 -> Expression()
	 * f11 -> ";"
	 * f12 -> "}"
	 */
	public R visit(MethodDeclaration n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   String funname = n.f2.f0.tokenImage;
	   currentMethod = currentClass.memMap.get(funname);
	   //Inheritance check for overridden methods
	   ClassNode tmpClass = currentClass;
	   ClassNode paren;
	   while(tmpClass.parentClass != null){
		   paren = ClassMapBackup.get(tmpClass.parentClass);
		   if( paren.memMap.containsKey(funname)){
			   MethodNode Methodparen = paren.memMap.get(funname);
			   if( Methodparen.noParams != currentMethod.noParams
					   || ( !currentMethod.returnType.refornot && !currentMethod.returnType.equals(Methodparen.returnType)) ){
//				   System.out.println("The method "+ funname + " is declared with wrong type or no of parameters in "+ currentClass.className);
				   System.out.print("Type error");
				   System.exit(0);
			   }
			   
			   for( int i = 0; i < currentMethod.noParams; ++i){
				   if(!currentMethod.paramStore.get(i).refornot){
					   if(!currentMethod.paramStore.get(i).equals(Methodparen.paramStore.get(i))){
//						   System.out.println("The Overridden method "+ funname + " is declared with wrong parameters in "+ currentClass.className);
						   System.out.print("Type error");
						   System.exit(0);
					   }
				   }
				   else if(!ChkStringReachesSec(ClassMapBackup.get(currentMethod.paramStore.get(i).type), Methodparen.paramStore.get(i).type)){
//					   System.out.println("The Overridden method "+ funname + " is declared with wrong parameters in "+ currentClass.className);
					   System.out.print("Type error");
					   System.exit(0);
				   }
			   }
			   if( currentMethod.returnType.refornot && 
					   !ChkStringReachesSec(ClassMapBackup.get(currentMethod.returnType.type), Methodparen.returnType.type)){
//				   System.out.println("The Overridden method "+ funname + " is declared with wrong return type in "+ currentClass.className);
				   System.out.print("Type error");
				   System.exit(0);
			   }
		   }
		   tmpClass = paren;
	   }
	   n.f3.accept(this);
	   n.f4.accept(this);
	   n.f5.accept(this);
	   n.f6.accept(this);
	   n.f7.accept(this);
	   n.f8.accept(this);
	   n.f9.accept(this);
	   n.f10.accept(this);
	   // Matching Return types
	   if( expType == null 
			   || (!currentMethod.returnType.refornot && !currentMethod.returnType.equals(expType)) 
			   || (currentMethod.returnType.refornot && !ChkStringReachesSec(ClassMapBackup.get(expType.type), currentMethod.returnType.type))
			   ){
//		   System.out.println("The return type of the function doesnt match");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f11.accept(this);
	   n.f12.accept(this);
	   currentMethod = null;
	   return _ret;
	}
	
	/**
	 * f0 -> FormalParameter()
	 * f1 -> ( FormalParameterRest() )*
	 */
	public R visit(FormalParameterList n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 */
	public R visit(FormalParameter n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   String varname = n.f1.f0.tokenImage;
	   if(currentMethod.varMap.containsKey(varname)){
		   if( currentMethod.varMap.get(varname).refornot == true && !ClassMapBackup.containsKey(currentMethod.varMap.get(varname).type) ){
//			   System.out.println("Undefined Variable Type "+currentMethod.varMap.get(varname).type);
			   System.out.print("Type error");
			   System.exit(0);
		   }
	   }
	   else if (currentMethod.paramMap.containsKey(varname)){
		   if( currentMethod.paramMap.get(varname).refornot && !ClassMapBackup.containsKey(currentMethod.paramMap.get(varname).type) ){
//			   System.out.println("Undefined Variable Type "+currentMethod.paramMap.get(varname).type);
			   System.out.print("Type error");
			   System.exit(0);
		   }
	   }
	   else{
		   if( currentClass.varMap.get(varname).refornot && !ClassMapBackup.containsKey(currentClass.varMap.get(varname).type)){
//			   System.out.println("Undefined Variable Type "+currentClass.varMap.get(varname).type);
			   System.out.print("Type error");
			   System.exit(0);
		   }
	   } 
	   return _ret;
	}
	
	/**
	 * f0 -> ","
	 * f1 -> FormalParameter()
	 */
	public R visit(FormalParameterRest n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> ArrayType()
	 *       | BooleanType()
	 *       | IntegerType()
	 *       | Identifier()
	 */
	public R visit(Type n) {
	   R _ret=null;
	   n.f0.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "int"
	 * f1 -> "["
	 * f2 -> "]"
	 */
	public R visit(ArrayType n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "boolean"
	 */
	public R visit(BooleanType n) {
	   R _ret=null;
	   n.f0.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "int"
	 */
	public R visit(IntegerType n) {
	   R _ret=null;
	   n.f0.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> Block()
	 *       | AssignmentStatement()
	 *       | ArrayAssignmentStatement()
	 *       | IfStatement()
	 *       | WhileStatement()
	 *       | PrintStatement()
	 */
	public R visit(Statement n) {
	   R _ret=null;
	   n.f0.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "{"
	 * f1 -> ( Statement() )*
	 * f2 -> "}"
	 */
	public R visit(Block n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> Identifier()
	 * f1 -> "="
	 * f2 -> Expression()
	 * f3 -> ";"
	 */
	public R visit(AssignmentStatement n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   IdClass Idtype = getIdType(n.f0.f0.tokenImage);
	   if( Idtype == null || expType == null || (!Idtype.refornot && !Idtype.equals(expType))
			   || (Idtype.refornot && !ChkStringReachesSec(ClassMapBackup.get(expType.type), Idtype.type))
			   ){
//		   System.out.println("Type Mismatch in Assignment Statement containing "+ n.f0.f0.tokenImage);
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f3.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> Identifier()
	 * f1 -> "["
	 * f2 -> Expression()
	 * f3 -> "]"
	 * f4 -> "="
	 * f5 -> Expression()
	 * f6 -> ";"
	 */
	public R visit(ArrayAssignmentStatement n) {
	   R _ret=null;
	   n.f0.accept(this);
	   IdClass Idtype = getIdType(n.f0.f0.tokenImage);
	   if( Idtype == null || expType == null || !Idtype.equals(new IdClass(false, true, "int", null))){
//		   System.out.println( "Type mismatch in Array Assignment Statement containing "+ Idtype.name);
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( expType == null || !expType.equals( new IdClass("int", null))){
//		   System.out.println("Integer expected here, but "+ expType.type +" is present");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f3.accept(this);
	   n.f4.accept(this);
	   n.f5.accept(this);
	   if( expType == null || !expType.equals( new IdClass("int", null))){
//		   System.out.println( "Type mismatch in Array Assignment Statement containing "+ Idtype.name);
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f6.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "if"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 * f5 -> "else"
	 * f6 -> Statement()
	 */
	public R visit(IfStatement n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( expType == null || !expType.equals( new IdClass("boolean", null))){
//		   System.out.println("Boolean is expected in this if statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f3.accept(this);
	   n.f4.accept(this);
	   n.f5.accept(this);
	   n.f6.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "while"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 */
	public R visit(WhileStatement n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( expType == null || !expType.equals( new IdClass("boolean", null))){
//		   System.out.println("Boolean is expected in this while loop");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f3.accept(this);
	   n.f4.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> "System.out.println"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> ";"
	 */
	public R visit(PrintStatement n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( expType == null || !expType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this System.out.println() statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f3.accept(this);
	   n.f4.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> AndExpression()
	 *       | CompareExpression()
	 *       | PlusExpression()
	 *       | MinusExpression()
	 *       | TimesExpression()
	 *       | ArrayLookup()
	 *       | ArrayLength()
	 *       | MessageSend()
	 *       | PrimaryExpression()
	 */
	public R visit(Expression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if(n.f0.which == 8)
		   expType = pexpType;
	   
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "&"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(AndExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("boolean", null))){
//		   System.out.println("Boolean is expected in this AND statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("boolean", null))){
//		   System.out.println("Boolean is expected in this AND statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   expType = new IdClass("boolean", null);
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "<"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(CompareExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this < statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this < statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   expType = new IdClass("boolean", null);
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "+"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(PlusExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this + statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if(pexpType == null ||  !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this + statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   expType = new IdClass("int", null);
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "-"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(MinusExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this - statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this - statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   expType = new IdClass("int", null);
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "*"
	 * f2 -> PrimaryExpression()
	 */
	public R visit(TimesExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this * statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in this * statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   expType = new IdClass("int", null);
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "["
	 * f2 -> PrimaryExpression()
	 * f3 -> "]"
	 */
	public R visit(ArrayLookup n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass(false, true,"int", null))){
//		   System.out.println("Only Integer array in valid");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass("int", null))){
//		   System.out.println("Integer is expected in Array Lookup statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f3.accept(this);
	   expType = new IdClass("int", null);
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> "length"
	 */
	public R visit(ArrayLength n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !pexpType.equals( new IdClass(false, true, "int", null))){
//		   System.out.println("Integer array is expected in Array Length statement");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f1.accept(this);
	   n.f2.accept(this);
	   expType = new IdClass("int", null);
	   return _ret;
	}
	
	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( ExpressionList() )?
	 * f5 -> ")"
	 */
	public R visit(MessageSend n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( pexpType == null || !ClassMapBackup.containsKey(pexpType.type)){
//		   System.out.println(pexpType.type+" is not a valid Class");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   ClassNode tmpClass = ClassMapBackup.get(pexpType.type);
	   ClassNode tmpClasssave = tmpClass;
	   n.f1.accept(this);
	   n.f2.accept(this);
	   String varname = n.f2.f0.tokenImage;
//	   System.out.println("TESt "+tmpClass.className+ " "+ varname);
	   boolean correct = false;
	   do{
		   if(tmpClass.memMap.containsKey(varname)){
			   correct = true;
			   break;
		   }
		   tmpClass = (tmpClass.parentClass != null) ? ClassMapBackup.get(tmpClass.parentClass) : null;
	   }while(tmpClass != null);
	   if(!correct){
//		   System.out.println(varname +" method doesnt belong to "+ tmpClasssave.className + " class or its parents");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   MethodNode tmpMethod = tmpClass.memMap.get(varname);
	   n.f3.accept(this);
	   n.f4.accept(this);
	   int i = 0;
	   boolean flag = true;
	   while(!explist.empty()){
		   if( i >= tmpMethod.noParams)
			   flag = false;
		   else if(explist.peek() == null 
				   || (!tmpMethod.paramStore.get(i).refornot && !tmpMethod.paramStore.get(i).equals(explist.peek()) )
				   || (tmpMethod.paramStore.get(i).refornot 
						   && !ChkStringReachesSec(ClassMapBackup.get(explist.peek().type), tmpMethod.paramStore.get(i).type) )
				)
			   flag = false;
		   explist.pop();
		   ++i;
	   }
	   if(!flag || i != tmpMethod.noParams){
//		   System.out.println("Number of parameters and Type doesnot match");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f5.accept(this);
	   expType = new IdClass(tmpMethod.returnType.refornot, tmpMethod.returnType.arrornot, tmpMethod.returnType.type, null);
	   return _ret;
	}
	
	/**
	 * f0 -> Expression()
	 * f1 -> ( ExpressionRest() )*
	 */
	public R visit(ExpressionList n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if( expType != null)
		   explist.push(new IdClass(expType.refornot, expType.arrornot, expType.type, expType.name));
	   n.f1.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> ","
	 * f1 -> Expression()
	 */
	public R visit(ExpressionRest n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   if(expType != null)
		   explist.push(new IdClass(expType.refornot, expType.arrornot, expType.type, expType.name));
	   return _ret;
	}
	
	/**
	 * f0 -> IntegerLiteral()
	 *       | TrueLiteral()
	 *       | FalseLiteral()
	 *       | Identifier()
	 *       | ThisExpression()
	 *       | ArrayAllocationExpression()
	 *       | AllocationExpression()
	 *       | NotExpression()
	 *       | BracketExpression()
	 */
	public R visit(PrimaryExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   return _ret;
	}
	
	/**
	 * f0 -> <INTEGER_LITERAL>
	 */
	public R visit(IntegerLiteral n) {
	   R _ret=null;
	   n.f0.accept(this);
	   pexpType = new IdClass("int", null);
	   return _ret;
	}
	
	/**
	 * f0 -> "true"
	 */
	public R visit(TrueLiteral n) {
	   R _ret=null;
	   n.f0.accept(this);
	   pexpType = new IdClass("boolean", null);
	   return _ret;
	}
	
	/**
	 * f0 -> "false"
	 */
	public R visit(FalseLiteral n) {
	   R _ret=null;
	   n.f0.accept(this);
	   pexpType = new IdClass("boolean", null);
	   return _ret;
	}
	
	/**
	 * f0 -> <IDENTIFIER>
	 */
	public R visit(Identifier n) {
	   R _ret=null;
	   n.f0.accept(this);
	   String varname = n.f0.tokenImage;
	   pexpType = getIdType(varname);
	   return _ret;
	}
	
	/**
	 * f0 -> "this"
	 */
	public R visit(ThisExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   if(currentClass == null){
//		   System.out.println("Invalid identifier 'this'");
		   System.out.print("Type error");
	   }
	   else
		   pexpType = new IdClass(true, false, currentClass.className, null);
	   return _ret;
	}
	
	/**
	 * f0 -> "new"
	 * f1 -> "int"
	 * f2 -> "["
	 * f3 -> Expression()
	 * f4 -> "]"
	 */
	public R visit(ArrayAllocationExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   n.f3.accept(this);
	   if( expType == null || !expType.equals( new IdClass("int", null))){
//		   System.out.println("Array Allocation requires integer size");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f4.accept(this);
	   pexpType = new IdClass(false, true, "int", null);
	   return _ret;
	}
	
	/**
	 * f0 -> "new"
	 * f1 -> Identifier()
	 * f2 -> "("
	 * f3 -> ")"
	 */
	public R visit(AllocationExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   String varname = n.f1.f0.tokenImage;
	   if( !ClassMapBackup.containsKey(varname)){
//		   System.out.println("Cant create object of this class ; No class declared as "+varname);
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   n.f2.accept(this);
	   n.f3.accept(this);
	   pexpType = new IdClass(true, false, varname, null);
	   return _ret;
	}
	
	/**
	 * f0 -> "!"
	 * f1 -> Expression()
	 */
	public R visit(NotExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   if( expType == null ||  !expType.equals( new IdClass("boolean", null))){
//		   System.out.println("Boolean expected for NOT operation");
		   System.out.print("Type error");
		   System.exit(0);
	   }
	   pexpType = new IdClass("boolean", null);
	   return _ret;
	}
	
	/**
	 * f0 -> "("
	 * f1 -> Expression()
	 * f2 -> ")"
	 */
	public R visit(BracketExpression n) {
	   R _ret=null;
	   n.f0.accept(this);
	   n.f1.accept(this);
	   n.f2.accept(this);
	   pexpType = new IdClass(expType.refornot, expType.arrornot, expType.type, null);
	   return _ret;
	}
	/*
	 * My Functions
	 */
	private IdClass getIdType(String varname){
		if(ClassMapBackup.containsKey(varname))
			return new IdClass(true, false, varname, null);
		else if(currentMethod != null && currentMethod.functionName.equals("main"))
			return new IdClass(false, true, "String", varname);
		else if( currentMethod != null ){
		   if(currentMethod.varMap.containsKey(varname))
			   return currentMethod.varMap.get(varname);
		   else if(currentMethod.paramMap.containsKey(varname))
			   return currentMethod.paramMap.get(varname);
		   else{
			   ClassNode tmpClass = currentClass;
			   boolean correct = false;
			   do{
				   if( tmpClass.varMap.containsKey(varname)){
					   correct = true;
					   break;
				   }
				   tmpClass = (tmpClass.parentClass != null) ? ClassMapBackup.get(tmpClass.parentClass) : null;
			   }while(tmpClass != null);
			   return ( correct ? tmpClass.varMap.get(varname) : null ); 
		   }
		}
		else{
			ClassNode tmpClass = currentClass;
		   boolean correct = false;
		   do{
			   if( tmpClass.varMap.containsKey(varname)){
				   correct = true;
				   break;
			   }
			   tmpClass = (tmpClass.parentClass != null) ? ClassMapBackup.get(tmpClass.parentClass) : null;
		   }while(tmpClass != null);
		   return ( correct ? tmpClass.varMap.get(varname) : null ); 
		}
	}
	
	private boolean ChkStringReachesSec(ClassNode currentretClassNode, String parentretClassname){
	   boolean correct = false;
	   do{
		   if(currentretClassNode.className.equals(parentretClassname)){
			   correct = true;
			   break;
		   }
		   currentretClassNode = (currentretClassNode.parentClass != null) ? ClassMapBackup.get(currentretClassNode.parentClass) : null;
	   }while( currentretClassNode != null);
	   
	   return correct;
	}
}
