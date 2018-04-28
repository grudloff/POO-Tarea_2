import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.JComponent.*;
import java.util.*;

public class MyWorld extends JPanel {
   public MyWorld(){
      maze = null;
      mPos=null;
   }
   
   {
	   SCALE_TRANSFORM= AffineTransform.getScaleInstance(SCALE,SCALE);
   }
   public boolean isThere_a_wall(int x, int y) {
	   return maze.isThere_a_wall(x, y);
   }

   public void setMaze(Maze m, MainFrame f) {
   // to be coded
	   this.maze=m;
	   setPreferredSize(new Dimension(m.getWidth()*SCALE, m.getHeight()*SCALE));
	   f.pack();
	   repaint();
	}
   
   public void paintRobot(Robot p) { //Va pintando el robot con el movimiento del mouse
	   this.mPos=p;
	   repaint();
   }
   
   public void setRobot(Robot r) {
	   this.robots.add(r);
      
	   repaint();
   }
   
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.transform(SCALE_TRANSFORM);
      if (maze!=null) {
         maze.draw(g2);
      }
      
      
      
      if (robots!=null){
    	  for (int i=0;i<robots.size();i++) 
    		  robots.get(i).draw(g2);
      }
           
      if (mPos!=null){
    	  System.out.println("posicion mouse");
        mPos.draw(g2);
      }
          	  
      
   }
   
   private Maze maze;
   private Robot mPos;
   private ArrayList<Robot> robots= new ArrayList<Robot> ();
   private static AffineTransform SCALE_TRANSFORM;
   private static int SCALE=3;
}


