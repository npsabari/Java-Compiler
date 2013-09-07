import syntaxtree.*;
import visitor.*;

public class Simplify {
   public static void main(String [] args) {
      try {
         Node root = new MiniJavaParser(System.in).Goal();
         root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
         root.accept(new IRgenerator());
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 
