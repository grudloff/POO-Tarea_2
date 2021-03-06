import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.JComponent.*;
import java.util.*;

public class MyWorld extends JPanel {
   public MyWorld(){
      maze = null;
   }
   
   {
	   SCALE_TRANSFORM= AffineTransform.getScaleInstance(SCALE,SCALE);
   }
   public boolean isThere_a_wall(int x, int y) {
	   return maze.isThere_a_wall(y,x);
   }
   
   public boolean isDeployable(int x, int y) {
	   return maze.isThere_a_wall((int)(y/SCALE),(int) (x/SCALE));
   }

   public void setMaze(Maze m) {
	   this.maze=m;
	   //Permite a .pack() del JFrame saber el tamaño
	   this.setPreferredSize(new Dimension((int)(SCALE*maze.getHight()),(int)(SCALE*maze.getLength())));
	   repaint();
	   
   }
   public void setRobot(Robot r) {
	   this.robots.add(r);
	   repaint();
   }
   public void setRobotPos(Vector2D pos) {
	   robots.get(robots.size()-1).setPosition(pos.times(1.0/SCALE));
	   repaint();
   }
   
   
   public void setCourse(double delta_t) {
	   Robot robot;
	   
	   for (int i=0;i<robots.size();i++) {
		   robot=robots.get(i);
		   if(!((robot.getPosition().minus(exit).getModule()<exitRadius))) {
			   robot.moveDelta_t(delta_t);
			   }
	   }
	   repaint();
   }
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.transform(SCALE_TRANSFORM);
      if (maze!=null) {
         maze.draw(g2);
         
         }
      if (robots!=null)
    	  for (int i=0;i<robots.size();i++)
    		  robots.get(i).draw(g2);
      
   }
   
   public void setExit(Vector2D exit,double exitRadius) {
	   this.exit=exit;
	   this.exitRadius=exitRadius;
   }
   
   private double exitRadius;
   private Vector2D exit;
   private Maze maze;
   private ArrayList<Robot> robots= new ArrayList<Robot> ();
   private static AffineTransform SCALE_TRANSFORM;
   private static double SCALE=3;
}



