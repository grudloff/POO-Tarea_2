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
import javax.swing.JMenu;

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
   public static void main(String[] args) throws IOException{
      //RECETA PARA LA UTILIZACI�N DE SWING
      SwingUtilities.invokeLater(new Runnable() {   // implementacion Swing recomendada: Creacion de hebra
            public void run() {// Codigo de la hebra
               MainFrame frame = new MainFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setLocation(350,50);
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

      MyTime time = new MyTime();
      Container contentPane = getContentPane();//Acceder al panel de contenidos del JFrame
      contentPane.add(time.getView(),BorderLayout.SOUTH);
      // add Menu bar to frame
      setJMenuBar(new MainMenuBar(contentPane));
      
      MainPanel Mapa = new MainPanel();
      contentPane.add(Mapa);

   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 600;  
}


class MainMenuBar extends JMenuBar implements ActionListener{
   public MainMenuBar (Container p){
      parent = p;
     // menuBar = new JMenuBar();//Barra del menu
      menu = new JMenu("File");
      menu.setMnemonic(KeyEvent.VK_F);
      this.add(menu);// Se agrega a la barra de menu del padre
      item = new JMenuItem("Open");//Primera opcion del menu
      item.setMnemonic(KeyEvent.VK_O);
      menu.add(item);
      item.addActionListener(this);
      item = new JMenuItem("Compile");//Primera opcion del menu
      item.setMnemonic(KeyEvent.VK_C);
      menu.add(item);
      
      menu = new JMenu("World");
      menu.setMnemonic(KeyEvent.VK_W);
      this.add(menu);// Se agrega a la barra de menu del padre
      item = new JMenuItem("Create robot");//Primera opcion del menu
      item.setMnemonic(KeyEvent.VK_R);
      menu.add(item);
      item = new JMenuItem("Set Delta t");//Primera opcion del menu
      item.setMnemonic(KeyEvent.VK_S);
      menu.add(item);
      
      fc = new JFileChooser();
      fc.setCurrentDirectory(new File("C:"));
      fc.setFileFilter(new FileNameExtensionFilter("PBM file", "pbm"));//filtro solo se debe poner extension
      
      
      
   }
   
   public void actionPerformed(ActionEvent event){
    String archivo;
    File file;
    int returnVal = fc.showOpenDialog(parent);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
       System.out.println("You chose to open this file: " +
            fc.getSelectedFile().getName());
       file = fc.getSelectedFile();  
       archivo=(String)fc.getSelectedFile().getName();
       System.out.println(archivo);
       try {
          in = new Scanner(new File(archivo));
       }
       catch ( FileNotFoundException t ){  
         
       }  
       
       //Maze laberinto= new Maze(in);
    }
    //in = new Scanner (new File(archivo));
   }
   
   
   
   private Container parent;
   private JFileChooser fc;
   JMenuBar menuBar;
   JMenu menu;
   JMenuItem item;
   Scanner in;
}

