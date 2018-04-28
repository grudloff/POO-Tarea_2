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
import javax.swing.event.*;
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

public class Stage1 {
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
      setJMenuBar(new MainMenuBar(M_panel, this));
      MyTime time= new MyTime();
      Container contentPane = getContentPane();
      contentPane.add(time.getView(), BorderLayout.SOUTH);
      

      contentPane.add(M_panel);
      
   }

   public static final int DEFAULT_WIDTH = 600;
   public static final int DEFAULT_HEIGHT = 600;
}


class MainMenuBar extends JMenuBar implements ActionListener{
   public MainMenuBar (MyWorld parent, MainFrame f){
	   this.parent=parent;
	   MyFrame=f;
      JMenu menu1 = new JMenu("File");
      JMenu menu2 = new JMenu("World");
      add(menu1);
      add(menu2);
      JMenuItem item_open = new JMenuItem("Open");
      menu1.add(item_open);
      JMenuItem item_compile = new JMenuItem("Compile");
      menu1.add(item_compile);
      JMenuItem item_robot = new JMenuItem("Create Robot");//Create robot
      JMenuItem item_delta_t = new JMenuItem("Set delta_t");
      menu2.add(item_robot);
      menu2.add(item_delta_t);
      item_open.addActionListener(this);
      item_robot.addActionListener(new RobotCreationListener());//Create robot
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
	       parent.setMaze(maze, MyFrame);
	             
	    }
   }
   
   
   class RobotCreationListener implements ActionListener{
		public Vector2D pos_M;
      
      
      public RobotCreationListener(){ 
      }
		
      class Ubicacion extends MouseInputAdapter{
         public Robot rr;
         public MyWorld ww;
         public Vector2D p;
         
         public Ubicacion (Robot r,MyWorld w){
            rr=r;
            ww=w;
			}
         
         public void mouseMoved(MouseEvent e){
            System.out.println("x="+e.getX());
            System.out.println("y="+e.getY());  
         }
         public void mouseDragged(MouseEvent e) {
         }
         
         public void mouseClicked(MouseEvent mm) {
            System.out.println("Click");
            p =new Vector2D(mm.getX()/3,mm.getY()/3);
            rr.setPosition(p);
				ww.setRobot(rr);
			}

           
      }  
             
		public void actionPerformed(ActionEvent e) {
			
         
         String x=JOptionPane.showInputDialog("Velocidad X: ");
			String y=JOptionPane.showInputDialog("Velocidad Y: ");
			boolean turn;
			int selection= JOptionPane.showOptionDialog(null, "\t Elegir el piloto para el robot", "Configuracion piloto",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Apegado a la pared Izquierda","Apegado a la pared Derecha"}, "Apegado a la pared Derecha");
			if (selection==0)
				turn=true;
			else
				turn=false;
			Vector2D pos=new Vector2D(10,10);
         Vector2D vel=new Vector2D(Integer.parseInt(x),Integer.parseInt(y));
			Robot robot=new Robot(pos, vel, 3.0, parent, turn);
			  
			//addMouseListener(new MouseMovement(robot));// Se activan los eventos de click del mouse estaran activos con MouseMovement
			
         parent.setRobot(robot);
         Ubicacion ubicro= new Ubicacion(robot,parent);
         parent.addMouseMotionListener(ubicro);//
         parent.addMouseListener(ubicro);// 
         //addMouseListener(ubicro);// Se capturan los eventos del mouse
         
		}
   
       
   public class MouseMovement extends JPanel implements MouseInputListener{
			public int mX, mY;
			public Robot rr;
         
			public MouseMovement(Robot r) {
				addMouseMotionListener(this);//Recepcion del movimiento del mouse
				addMouseListener(this);
				setVisible(true);
				rr=r;
			}
			
         
			public void mouseMoved(MouseEvent mm) {
				mX=(int) mm.getPoint().getX();
				mY=(int) mm.getPoint().getY();
				Vector2D mousePos=new Vector2D(mX,mY);//posicion mouse
				rr.setPosition(mousePos);//fija la posicion del robot
				parent.paintRobot(rr);// pinta el robot
			}
			
			public void mouseClicked(MouseEvent mm) {
            System.out.println("Click");
				parent.setRobot(rr);
			}
			
			public void mouseDragged(MouseEvent mm) {}
			public void mouseEntered(MouseEvent mm) {}
			public void mouseExited(MouseEvent mm) {}
			public void mousePressed(MouseEvent mm) {}
			public void mouseReleased(MouseEvent mm) {}
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
	               final JTextField ventanaTexto=new JTextField("new delta_t value", 20);	               
	               tframe.getContentPane().add(ventanaTexto);
	               JButton boton=new JButton("ok!");
	               tframe.getContentPane().add(boton, BorderLayout.PAGE_END);
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
	
	

   private double delta_t;
   private JFileChooser chooser;
   private MyWorld parent;
   private Scanner in;
   private Maze maze;
   private MainFrame MyFrame;
   
}


