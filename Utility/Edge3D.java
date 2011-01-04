/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utility;

/**
 *
 * @author Abhijit Mukherjee
 */
public class Edge3D {
   public int a, b;
   public Edge3D( int A, int B ) {
      a = A;  b = B;
   }

   public Edge3D(Edge3D edge){
       this(edge.a,edge.b);
   }
}
