/* ARCHIVO.PBM-->MAZE-->MAINPANEL-->STAGE1*/
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;

public class Maze {

   protected Maze(){}
   
   public Maze(Scanner sc) {
      read(sc);
   }
   
   public void read(Scanner sc){//Recoge sc para leerlo
      String s;
      while(!sc.hasNextInt())
          sc.nextLine();
      int width = sc.nextInt();//Toma el ancho
      int hight = sc.nextInt();//Toma el largo
      sc.nextLine();
      array = new boolean [hight][];
      for (int h=0; h<hight; h++)
         array[h] = new boolean[width];//va generando nuevos espacios ara booleanos
      for (int h=0; h<hight; h++) 
         for (int w=0; w<width; ){
            s = sc.findInLine(".");
            if (s==null) sc.nextLine();
            else array[h][w++] = s.charAt(0)=='1';//Genera un arreglo con booleanos igual al mapa  
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
   
   public boolean isThere_a_wall(int x, int y) {// 1: Hay muro; 0:No hay muro
      if ((x < array.length) && (y < array[0].length) )
         return array[x][y];
      else return false;
   }
   
   public void markPoint(Vector2D p){
      int x=(int)p.getX(), y=(int)p.getY();
      if ((x < array.length) && (y < array[0].length))
           array[x][y]=true;// Marca los lugares en donde existe muralla
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
      MainPanel dibujo= new MainPanel();//llamo al dibujante
      dibujo.setMaze(this);// le doy este laberinto
      
      //to be coded
   }
   
   private boolean [][] array;
}