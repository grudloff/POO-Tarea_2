import java.awt.*;
import javax.swing.*;

public class MainPanel extends JPanel {
   public MainPanel(){
      maze = null;
   }

   public void setMaze(Maze m) {
      this.maze=m;
      this.repaint();
   }
   
      
   public void paintComponent(Graphics g) { //Metodo de la clase 
      super.paintComponent(g);//llamar al metodo de la clase padre
      Graphics2D g2 = (Graphics2D)g;
      
      g2.setStroke(new BasicStroke(5.0f)); // grosor de 5 pixels
      g2.drawLine (10, 10, 100, 100);
     
      //this.maze.draw(g2);
   //   g.drawRect(50,50,200,200);
   // to be coded
   }
   
   private Maze maze;
   
}