import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.*;
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
   public void setRobot(Robot r,PrintStream out) {
	   r.setMaze(maze);
	   this.robots.add(r);
	   this.printRoute.add(true);
	   out.println("t,x,y");
	   this.RouteFiles.add(out);	   
	   repaint();
   }
   public void setRobotPos(Vector2D pos) {
	   robots.get(robots.size()-1).setPosition(pos.times(1.0/SCALE));
	   repaint();
   }
   
   
   public void setCourse(double delta_t) {
	   Robot robot;
	   PrintStream RouteFile;
	   
	   for (int i=0;i<robots.size();i++) {
		   robot=robots.get(i);
		   RouteFile=RouteFiles.get(i);
		   if(!((robot.getPosition().minus(exit).getModule()<exitRadius))) {
			   robot.moveDelta_t(delta_t);
			   robot.markRoute();
			   RouteFile.println(t+","+robot.getPosition().getX()+","+robot.getPosition().getY());
			   }
		   else if(printRoute.get(i)){
			   //imprimir maze del robot
			   printRoute.set(i, false);
			   try {
			   PrintStream out=new PrintStream(new File("maze_out_"+i+".pbm"));
			   robot.getMaze().write(out);
			   }catch (FileNotFoundException t) {

			   }
		   }
	   }
	   t+=delta_t;
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
   
   double t=0;
   private double exitRadius;
   private Vector2D exit;
   private Maze maze;
   private ArrayList<Boolean> printRoute = new ArrayList<Boolean> ();
   private ArrayList<Robot> robots= new ArrayList<Robot> ();
   private ArrayList<PrintStream> RouteFiles= new ArrayList<PrintStream> ();
   private static AffineTransform SCALE_TRANSFORM;
   private static double SCALE=3;
}



