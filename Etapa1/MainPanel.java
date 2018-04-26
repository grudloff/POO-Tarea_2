import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.JComponent.*;

public class MainPanel extends JPanel {
   public MainPanel(){
      maze = null;
   }
   
   {
	   SCALE_TRANSFORM= AffineTransform.getScaleInstance(SCALE,SCALE);
   }

   public void setMaze(Maze m) {
	   this.maze=m;
	   // Para pintar al momento que se setea el maze, de lo contrario no se actualiza.
	   repaint();
   }
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.transform(SCALE_TRANSFORM);
      
      //Para evitar problemas previo a setear el maze.
      if (maze!=null)
         maze.draw(g2); 
      
   }
   
   private Maze maze;
   private static AffineTransform SCALE_TRANSFORM;
   private static double SCALE=2.6;
}
