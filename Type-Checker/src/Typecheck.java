import syntaxtree.*;
import visitor.*;

public class Typecheck {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
//         System.out.println("Program parsed successfully");
         root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
         root.accept(new TypeChecker());
         System.out.print("Program type checked successfully");
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
}