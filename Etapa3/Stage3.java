
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
import java.util.Timer;
import java.util.TimerTask;
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

public class Stage3 {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // implementación Swing recomendada
			public void run() {
				frame = new MainFrame();
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
	
	public static MainFrame frame;
}

/**
 * A frame containing the application main GUI
 */
class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("ELO329: Robots en Laberinto");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		world = new MyWorld();
		Vector2D exit=new Vector2D(190,225);
		double exitRadius=10;
		world.setExit(exit,exitRadius);

		// add Menu bar to frame
		MyMainMenu=new MainMenuBar(world, this);
		setJMenuBar(MyMainMenu);
		time = new MyTime();
		Container contentPane = getContentPane();
		contentPane.add(time.getView(), BorderLayout.SOUTH);

		contentPane.add(world);
		
		startTimer();

	}
	
	private void startTimer() {
	    final Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	        public void run() {
	        	if(MyMainMenu.getFlagDelta_t()){
	    			timer.cancel(); // cancel time
	    			MyMainMenu.setFlagDelta_t();
		            startTimer();   // start the time again with a new delay time
	    			}
		    	if(time.isPlaying()) {
		    		world.setCourse(MyMainMenu.getDelta_t());
		    		}
	        }
	    //Por alguna razon hacer el casteo a long tira a cero los valores
	    },0,(long)Math.ceil(MyMainMenu.getDelta_t()*1000));
	    System.out.println((long)Math.ceil(MyMainMenu.getDelta_t()*1000));
	}

	Timer timer;
	MainMenuBar MyMainMenu;
	MyTime time;
	MyWorld world;
	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 600;
}

class MainMenuBar extends JMenuBar implements ActionListener {
	public MainMenuBar(MyWorld parent, JFrame jf) {
		this.parent = parent;
		this.jf = jf;
		JMenu menu1 = new JMenu("File");
		JMenu menu2 = new JMenu("World");
		add(menu1);
		add(menu2);
		JMenuItem item_open = new JMenuItem("Open");
		menu1.add(item_open);
		JMenuItem item_compile = new JMenuItem("Compile");
		menu1.add(item_compile);
		JMenuItem item_robot = new JMenuItem("Create Robot");
		JMenuItem item_delta_t = new JMenuItem("Set delta_t");
		menu2.add(item_robot);
		menu2.add(item_delta_t);
		item_open.addActionListener(this);
		item_robot.addActionListener(new RobotCreationListener());
		item_delta_t.addActionListener(new EntradaTexto());
		MouseListener mouse = new MouseListener();
		parent.addMouseMotionListener(mouse);//
		parent.addMouseListener(mouse);//

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		chooser.setFileFilter(new FileNameExtensionFilter("PBM file", "pbm"));
	}
	
	public double getDelta_t() {
		return delta_t;
	}

	//Menu open
	public void actionPerformed(ActionEvent event) {
		// actionPerformed asociada al menu Open.
		int returnVal = chooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			// Crear el Maze desde el archivo entregado en JFileChooser
			try {
				in = new Scanner(new File((String) chooser.getSelectedFile().getName()));
				maze = new Maze(in);
			} catch (FileNotFoundException t) {

			}

			// Pasarle el maze a MainPanel
			parent.setMaze(maze);

			// Hace un resize para ajustar al contenido
			jf.pack();

		}
	}

	class MouseListener extends MouseInputAdapter {
		// public Vector2D p;
		int mX, mY;
		Vector2D mousePos;

		public MouseListener() {
		}

		public void mouseMoved(MouseEvent e) {
			if (flag_mouse) {
				mX = (int) e.getPoint().getX();
				mY = (int) e.getPoint().getY();
				Vector2D mousePos = new Vector2D(mX, mY);
				// Esto evita que este fuera del maze y además que no este en una pared.
				if (!parent.isDeployable(mX, mY))
					parent.setRobotPos(mousePos);
			}
		}

		public void mouseClicked(MouseEvent mm) {
			flag_mouse = false;
		}

	}

	class RobotCreationListener implements ActionListener {
		public RobotCreationListener() {
		}

		public void actionPerformed(ActionEvent e) {
			String x = JOptionPane.showInputDialog("Velocidad X: ");
			String y = JOptionPane.showInputDialog("Velocidad Y: ");
			boolean turn;
			int selection = JOptionPane.showOptionDialog(null, "\t Elegir el piloto para el robot",
					"Configuracion piloto", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					new Object[] { "Apegado a la pared Izquierda", "Apegado a la pared Derecha" },
					"Apegado a la pared Derecha");
			if (selection == 0)
				turn = true;
			else
				turn = false;
			Vector2D pos = new Vector2D(0, 0);
			Vector2D vel = new Vector2D(Double.parseDouble(x), Double.parseDouble(y));
			Robot robot = new Robot(pos, vel, 7.0, parent, turn);
			parent.setRobot(robot);
			flag_mouse = true;

		}
	}

	class EntradaTexto implements ActionListener {
		public EntradaTexto() {
		}

		public void actionPerformed(ActionEvent e) {
			String dt = JOptionPane.showInputDialog("Diferencial de tiempo: ");
			delta_t = Double.parseDouble(dt);
			flag_deltat=true;
		}
	}
	
	public boolean getFlagDelta_t() {
		return flag_deltat;
	}
	
	public void setFlagDelta_t() {
		flag_deltat=false;
	}
	
	private boolean flag_deltat=false;
	private boolean flag_mouse = false;
	private JFrame jf;
	private double delta_t=0.001;
	private JFileChooser chooser;
	private MyWorld parent;
	private Scanner in;
	private Maze maze;

}
