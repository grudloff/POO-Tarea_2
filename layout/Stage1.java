/**
   @version 1.0 2018-04-17
   @author Agustín J. González
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
/* 
    El Metodo main tiene dos implementaciones, una de ellas comentada.
    Las componentes de Swing deben ser configuradas desde la hebra despachadora de eventos
    Esta es la hebra que pasa los eventos tales como clicks del mouse, teclas, etc 
    a las componentes de la interfaz usuario. 
    Es posible utilizar la versión comenatda para iniciar la interfaz usuario; sin emabrgo,
    las componetes de Swing aumentaron su complejidad y hoy no se logra garantizar la seguridad
    de la inicialización antigua. La probabilida de problemas es baja, pero no querrás ser 
    de aquellos sin suerte que enfrentan problemas intermitentes. Es mejor usar este
    mecanismo aún cuando el código luzca extraño. 
*/

public class Stage1 {
   public static void main(String[] args) {
      //RECETA PARA LA UTILIZACI�N DE SWING
      SwingUtilities.invokeLater(new Runnable() {   // implementacion Swing recomendada: Creacion de hebra
            public void run() {// Codigo de la hebra
               MainFrame frame = new MainFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            } // run ends
         });
    }
  // esta implementacion no es recomendada cuando usamos Swing
   public static void oldmain(String[] args) {  
       MainFrame frame = new MainFrame();
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
   }
}

/**
   A frame containing the application main GUI
*/
class MainFrame extends JFrame {
   public MainFrame() {
      setTitle("ELO329: Robots en Laberinto");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // add Menu bar to frame
      setJMenuBar(new MainMenuBar());
   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 600;  
}

class MainMenuBar extends JMenuBar {
   public MainMenuBar (){
     // menuBar = new JMenuBar();//Barra del menu
      menu = new JMenu("File");
      this.add(menu);// Se agrega a la barra de menu del padre
      //JMenuItem item = ...;
      item = new JMenuItem("Open");//Primera opcion del menu
      menu.add(item);
      item = new JMenuItem("Compile");//Primera opcion del menu
      menu.add(item);
      // to be coded
   }
   JMenuBar menuBar;
   JMenu menu;
   JMenuItem item;
}
