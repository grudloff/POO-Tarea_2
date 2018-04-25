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
    Es posible utilizar la version comenatda para iniciar la interfaz usuario; sin emabrgo,
    las componentes de Swing aumentaron su complejidad y hoy no se logra garantizar la seguridad
    de la inicialización antigua. La probabilida de problemas es baja, pero no querrás ser 
    de aquellos sin suerte que enfrentan problemas intermitentes. Es mejor usar este
    mecanismo aún cuando el código luzca extraño. 
*/

public class Stage1 {
   public static void main(String[] args) throws IOException{///
   ////////////////////////////////////////RECETA PARA LA UTILIZACI�N DE SWING
      SwingUtilities.invokeLater(new Runnable() {   // implementacion Swing recomendada: Creacion de hebra
            public void run() {// Codigo de la hebra
               MainFrame frame = new MainFrame();// FRAME GENERADO
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
   public MainFrame() { /////////// CREACION DEL FRAME: VENTANA CON TITULO
      setTitle("ELO329: Robots en Laberinto");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      MyTime time = new MyTime();
      Container contentPane = getContentPane();//Acceder al panel de contenidos del JFrame
      contentPane.add(time.getView(),BorderLayout.SOUTH);//FORMA DE UBICAR LA IMAGEN EN EL BOTON PLAY
      
      MainPanel Mapa = new MainPanel();// INSTANCIA PARA DIBUJAR EL LABERINTO
      //contentPane.add(Mapa);//SE A�ADE EL CONTENIDO EN LA PANTALLA
      
      setJMenuBar(new MainMenuBar(contentPane,Mapa));//INSTANCIA DEL OBJETO MainMenuBar EL CUAL AUN NO SE DEFINE Y ES MOSTRADA COMO CONTENIDO PRINCIPAL 
      
      
     
   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 600;
     
}



class MainMenuBar extends JMenuBar implements ActionListener{
   public MainMenuBar (Container p,MainPanel m){
      parent = p;
      Mapa = m;
      menuBar = new JMenuBar();//Barra del menu
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
      
      //parent.add(Mapa);      
   }
   
   public void actionPerformed(ActionEvent event){
    String archivo;
    Scanner in;
    //this Mapa = new MainPanel();
    int returnVal = fc.showOpenDialog(parent);//DIALOGO MOSTRADO COMO CONTENIDO PRINCIPAL
    if(returnVal == JFileChooser.APPROVE_OPTION) {
       archivo=(String)fc.getSelectedFile().getName();
       try {
          in = new Scanner(new File(archivo));
          maze= new Maze(in);
          this.Mapa.setMaze(maze);
          parent.add(this.Mapa);
          this.Mapa.repaint();
          System.out.println(archivo);

       }       
       catch ( FileNotFoundException t ){}
       
           } 
    
    
    
   }
   
   private Container parent;
   private JFileChooser fc;
   private JMenuBar menuBar;
   private JMenu menu;
   private JMenuItem item;
   private Scanner in;
   private MainPanel Mapa;
   private Maze maze;
}

