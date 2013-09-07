import syntaxtree.*;
import visitor.*;

public class Simplify2 {
   public static void main(String [] args) {
      try {
         Node root = new MiniIRParser(System.in).Goal();
         root.accept(new getMaxIndex());
         root.accept(new GJNoArguDepthFirst()); // Your assignment part is invoked here.
      }
      catch (ParseException e) {
         System.out.println(e.toString());
      }
   }
} 