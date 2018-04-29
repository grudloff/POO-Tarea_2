import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.Graphics2D.*;
import javax.swing.*;
import javax.swing.JComponent.*;

public class Maze {
   protected Maze(){}
   public Maze(Scanner sc) {
      read(sc);
   }
   public void read(Scanner sc){
      String s;
      while(!sc.hasNextInt())
          sc.nextLine();
      int width = sc.nextInt();
      int hight = sc.nextInt();
      sc.nextLine();
      array = new boolean [hight][];
      for (int h=0; h<hight; h++)
         array[h] = new boolean[width];
      for (int h=0; h<hight; h++) 
         for (int w=0; w<width; ){
            s = sc.findInLine(".");
            if (s==null) sc.nextLine();
            else array[h][w++] = s.charAt(0)=='1';  
         }      
   }
   protected Maze (Maze m){  // This is called copy constructor
      array = new boolean [m.array.length][];
      for (int h=0; h<array.length; h++)
         array[h] = new boolean[m.array[0].length];
      for (int h=0; h<array.length; h++) 
         for (int w=0; w<array[0].length; w++)
            array[h][w]=m.array[h][w];   
   }
   public boolean isThere_a_wall(int x, int y) {
	   // x,y pueden ser negativos por lo que se agrego la condición
      if ((x>=0)&&(x < array.length) && (y>=0)&&(y < array[0].length) ) {
         return array[x][y];
      }
      else return true;
   }
   public void markPoint(Vector2D p){
      int x=(int)p.getX(), y=(int)p.getY();
      if ((x < array.length) && (y < array[0].length))
           array[x][y]=true;
   }
   public void write(PrintStream out){
      out.println("P1");
      out.println("#Created by "+getClass().getName()+"UTFSM ELO329");
      out.println(array[0].length + " " +array.length);
      for (int h=0; h<array.length; h++) {
         for (int w=0; w<array[0].length; w++)
            out.print(array[h][w]?"1":"0");
         out.println();
      }   
   }
   
   public void draw(Graphics2D g) {
	      for (int h=0; h<array.length; h++) 
	          for (int w=0; w<array[0].length; w++)
	        	  if  (array[h][w]) 
	        			  g.fillRect(w,h,1,1);
	        	  
   }
   
   public int getLength() {
	   return array.length;
   }
   public int getHight() {
	   return array[0].length;
   }
   private boolean [][] array;
}