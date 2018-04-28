import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.JComponent.*;
import java.util.*;

public class MainPanel extends JPanel {
   public MainPanel(){
      maze = null;
   }
   
   {
	   SCALE_TRANSFORM= AffineTransform.getScaleInstance(SCALE,SCALE);
   }
   public boolean isThere_a_wall(int x, int y) {
	   return maze.isThere_a_wall(x, y);
   }

   public void setMaze(Maze m) {
	   this.maze=m;
	   //Permite a .pack() del JFrame saber el tamaño
	   this.setPreferredSize(new Dimension((int)(SCALE*maze.getHight()),(int)(SCALE*maze.getLength())));
	   //Repinta todo para actualizar la vista
	   repaint();
	   
   }
   
   
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.transform(SCALE_TRANSFORM);
      //Previo a setear un maze, maze es null
      if (maze!=null)
         maze.draw(g2);
      
   }
   
   private Maze maze;
   private ArrayList<Robot> robots= new ArrayList<Robot> ();
   private static AffineTransform SCALE_TRANSFORM;
   private static double SCALE=3;
}


