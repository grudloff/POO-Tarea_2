import java.awt.*;
import javax.swing.*;

public class MainPanel extends JPanel {
   public MainPanel(){
      maze = null;
   }

   public void setMaze(Maze m) {
      this.maze=m;
   }
   
   public void paintComponent(Graphics g) { //Metodo de la clase 
      super.paintComponent(g);//llamar al metodo de la clase padre
      Graphics2D g2 = (Graphics2D)g;
      
      g.drawRect(50,50,200,200);
   // to be coded
   }
   
   private Maze maze;
}