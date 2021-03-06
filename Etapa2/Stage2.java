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
    El Método main tiene dos implementaciones, una de ellas comentada.
    Las componentes de Swing deben ser configuradas desde la hebra despachadora de eventos
    ésta es la hebra que pasa los eventos tales como clicks del mouse, teclas, etc 
    a las componentes de la interfaz usuario. 
    Es posible utilizar la versión comenatda para iniciar la interfaz usuario; sin emabrgo,
    las componetes de Swing aumentaron su complejidad y hoy no se logra garantizar la seguridad
    de la inicialización antigua. La probabilida de problemas es baja, pero no querrás ser 
    de aquellos sin suerte que enfrentan problemas intermitentes. Es mejor usar este
    mecanismo aún cuando el código luzca extraño. 
*/

public class Stage2 {
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {   // implementación Swing recomendada
            public void run() {
               MainFrame frame = new MainFrame();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setVisible(true);
            } // run ends
         });
    }
  // esta implementación no es recomendada cuando usamos Swing
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
      MyWorld M_panel= new MyWorld();

      // add Menu bar to frame
      setJMenuBar(new MainMenuBar(M_panel,this));
      MyTime time= new MyTime();
      Container contentPane = getContentPane();
      contentPane.add(time.getView(), BorderLayout.SOUTH);
      

      contentPane.add(M_panel);
      
   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 600;
}


class MainMenuBar extends JMenuBar implements ActionListener{
   public MainMenuBar (MyWorld parent,JFrame jf){
	  this.parent=parent;
	  this.jf=jf;
      JMenu menu1 = new JMenu("File");
      JMenu menu2 = new JMenu("World");
      add(menu1);
      add(menu2);
      JMenuItem item_open = new JMenuItem("Open");
      menu1.add(item_open);
      JMenuItem item_robot = new JMenuItem("Create Robot");
      JMenuItem item_delta_t = new JMenuItem("Set delta_t");
      menu2.add(item_robot);
      menu2.add(item_delta_t);
      item_open.addActionListener(this);
      item_robot.addActionListener(new RobotCreationListener());
      item_delta_t.addActionListener(new EntradaTexto());
      
      chooser=new JFileChooser();
      chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
      chooser.setFileFilter(new FileNameExtensionFilter("PBM file","pbm"));
      }
   public void actionPerformed(ActionEvent event) {
	   //actionPerformed asociada al menu Open.
	    int returnVal = chooser.showOpenDialog(parent);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       //Crear el Maze desde el archivo entregado en JFileChooser
	       try {
	       in=new Scanner(new File((String)chooser.getSelectedFile().getName()));
	       maze=new Maze(in);
	       } 
	       catch (FileNotFoundException t){
	    	   
	       }
	       
	       //Pasarle el maze a MainPanel
	       parent.setMaze(maze);
	       
	       //Hace un resize para ajustar al contenido
	       jf.pack();
	       
	       	       
	       
	    }
   }
   
   class RobotCreationListener implements ActionListener{
		public RobotCreationListener() {
		}
		public void actionPerformed(ActionEvent e) {
			Vector2D pos=new Vector2D(15,50);
			Vector2D vel=new Vector2D(1,0);
			Robot robot=new Robot(pos, vel, 3.0, parent, true);
			parent.setRobot(robot);
			
		}
	}

	class EntradaTexto implements ActionListener{
		public EntradaTexto() {
		}
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {   // implementación Swing recomendada
	            public void run() {
	               final JDialog tframe = new JDialog();
	               tframe.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	               tframe.setSize(150, 50);
	               tframe.setVisible(true);
	               tframe.getContentPane().add(new JLabel("New Delta time value:"), BorderLayout.PAGE_START);
	               final JTextField ventanaTexto=new JTextField(String.valueOf(delta_t), 20);	               
	               tframe.getContentPane().add(ventanaTexto);
	               JButton boton=new JButton("ok!");
	               tframe.getContentPane().add(boton, BorderLayout.PAGE_END);
	               tframe.pack();
	               boton.addActionListener(new ActionListener() {
	       			public void actionPerformed(ActionEvent e) {
	       				delta_t=Double.parseDouble(ventanaTexto.getText());
	       				tframe.dispose();
	       				System.out.println(delta_t);
	       				
	       			}	       			      		
	       		});

	            } // run ends
	         });
			
		}
	}
	
	
	private JFrame jf;
   private double delta_t;
   private JFileChooser chooser;
   private MyWorld parent;
   private Scanner in;
   private Maze maze;
   
}


