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
   // to be coded
	   this.maze=m;
	   repaint();
   }
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
      g2.transform(SCALE_TRANSFORM);
   // to be coded
      if (maze!=null)
         maze.draw(g2); 
      
   }
   
   private Maze maze;
   private static AffineTransform SCALE_TRANSFORM;
   private static int SCALE=5;
}
