
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

public class Stage3 {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // implementación Swing recomendada
			public void run() {
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			} // run ends
		});
	
	}
	
}

/**
 * A frame containing the application main GUI
 */
class MainFrame extends JFrame {
	public MainFrame() {
		setTitle("ELO329: Robots en Laberinto");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		world = new MyWorld();
		Vector2D exit=new Vector2D(206,176);
		double exitRadius=10;
		world.setExit(exit,exitRadius);

		// add Menu bar to frame
		MyMainMenu=new MainMenuBar(world, this);
		setJMenuBar(MyMainMenu);
		time = new MyTime();
		Container contentPane = getContentPane();
		contentPane.add(time.getView(), BorderLayout.SOUTH);

		contentPane.add(world);
		
		startTimer(1);

	}
	
	private void startTimer(long a) {
	    final Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	        public void run() {
	        	if(MyMainMenu.getFlagDelta_t()){
	    			timer.cancel(); // cancel time
	    			MyMainMenu.setFlagDelta_t();
	    			long a=Math.round(MyMainMenu.getDelta_t()*1000);
		            startTimer(a);   // start the time again with a new delay time
	    			}
		    	if(time.isPlaying()) {
		    		world.setCourse(MyMainMenu.getDelta_t());
		    		}
	        }
	    //Por alguna razon hacer el casteo a long tira a cero los valores
	    },0,a);
	    System.out.println(a);
	}
	
	public MyTime getTime() {
		return time;
	}

	Timer timer;
	MainMenuBar MyMainMenu;
	MyTime time;
	MyWorld world;
	public static final int DEFAULT_WIDTH = 600;
	public static final int DEFAULT_HEIGHT = 600;
}

class MainMenuBar extends JMenuBar implements ActionListener {
	public MainMenuBar(MyWorld parent, MainFrame mf) {
		this.parent = parent;
		this.mf = mf;
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
			mf.pack();

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
			if(!mf.getTime().isPlaying()) {
				String x = JOptionPane.showInputDialog("Velocidad X: ");
				String y = JOptionPane.showInputDialog("Velocidad Y: ");
				boolean turn;
				int selection = JOptionPane.showOptionDialog(null, "\t Elegir el piloto para el robot",
						"Configuracion piloto", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new Object[] { "Apegado a la pared Izquierda", "Apegado a la pared Derecha" },
						"Apegado a la pared Derecha");
				if (selection == 0)
					turn = false;
				else
					turn = true;
				Vector2D pos = new Vector2D(0, 0);
				Vector2D vel = new Vector2D(Double.parseDouble(x), Double.parseDouble(y));
				Robot robot = new Robot(pos, vel, 7.0, parent, turn);
				parent.setRobot(robot);
				flag_mouse = true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Debes pausar para crear un nuevo robot", "Error", JOptionPane.ERROR_MESSAGE);
			}
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
	private MainFrame mf;
	private double delta_t=0.001;
	private JFileChooser chooser;
	private MyWorld parent;
	private Scanner in;
	private Maze maze;

}
