import java.awt.Color;
import java.awt.Graphics2D;

/** Clase Robot 
*  Esta clase alberga al robot que recorrera el laberinto y que puede ser posicionado de forma arbitraria
*  cuya forma de ser pilotado tambien puede ser establecida
*  @param position entrega las coordenadas en las que| robot dentro del laberinto
*  @param velocity es el vector velocidad que indica cuanto avanzara el robot por unidad de tiempo
*  @param sensorRange corresponde al rango del sensor que detecta las paredes cercanas al robot
*  @param w entrega una clase que hereda de JPanel atributos y metodos para modificar y mostrar en pantalla
*  @param u corresponde al flag que dice que piloto debe utilizarse
*/
public class Robot {
   public Robot(Vector2D position, Vector2D velocity, double sensorRange, MyWorld w,boolean u) {
	   pos = position;
	   v = velocity;
	   world = w;
	   Vector2D dir = v.getUnitary();
	   rightSensor = new DistanceSensor(new Vector2D(dir.getY(), -dir.getX()), sensorRange);
	   frontSensor = new DistanceSensor(dir, sensorRange);
	   leftSensor = new DistanceSensor(new Vector2D(-dir.getY(), dir.getX()), sensorRange);
   // similar to Robot in previous stage without the logic to set the course
   /// that ti is now asked to the pilot
	   if(u)
		   pilot = new PilotL();
	   else
		   pilot= new PilotR();
   }

/** setMaze 
*   Metodo para fijar el laberinto utilizado
*   @param maze laberinto utilizado para el robot  
*/

public void setMaze(Maze maze) {
	this.maze=new Maze(maze);
}

/** getMaze    
*   Metodo que retorna el laberinto utilizado
*   @return maze que es el laberinto utilizado
*/
public Maze getMaze() {
	return maze;
}

/** getPosition
*   Entrega la posicion actual del robot  
*   @return pos es la posicion del robot
*
*/
public Vector2D getPosition() {
   return pos;
}

/** setPosition
*   Fija una posicion nueva para el robot
*   @param posicion que se quiere fijar
*
*/
public void setPosition(Vector2D pos) {
	this.pos=pos;
}

/** getVelocity
*   Obtiene la velocidad actual del robot
*   @return velocidad del robot
*
*/
public Vector2D getVelocity() {
   return v;
}

/** turnLeft
*   Hace que el robot gire hacia la izquierda
*  
*
*/
public void turnLeft(){
   v.turnLeft();
   rightSensor.turnLeft();
   frontSensor.turnLeft();
   leftSensor.turnLeft();
   //System.out.println("turning left...");  // for debugging
   return; 
}

/** turnRight
*   Hace que el robot gire hacia la derecha
*   
*
*/
public void turnRight(){
   v.turnRight();
   rightSensor.turnRight();
   frontSensor.turnRight();
   leftSensor.turnRight();      
   //System.out.println("turning right...");
   return; 
}

/** getRightSensor
*   Obtener el sensor derecho
*   @return sensor derecho
*
*/
public DistanceSensor getRightSensor() {
	return rightSensor;
}

/** getLeftSensor
*   Obtener el sensor izquierdo
*   @return sensor izquierdo
*
*/
public DistanceSensor getLeftSensor() {
	return leftSensor;
}
/** getFrontSensor
*   Obtener el sensor frontal
*   @return sensor frontal
*
*/
public DistanceSensor getFrontSensor() {
	return frontSensor;
}

/** moveDelta_t
*   Fija el periodo de muestreo de la simulacion
*   @param valor del periodo de muestreo
*
*/
public void moveDelta_t(double delta_t) {
	   pilot.setCourse(delta_t);
}

/** getDescription()
*   Entrega la informacion correspondiente a los 3  sensores
*   @return Informacion en formato string
*
*/
public String getDescription() {
   return pos.getDescription() + ",  leftS frontS rightS";
}

/** toString()
*   Reune la informacion de los tres sensores
*   @return Informacion en formato string de los tres sensores juntos
*
*/
public String toString() {
   return pos.toString()+ "," +leftSensor.toString() + frontSensor.toString()+rightSensor.toString()+ ", " +v.toString();
}

/** markRoute()
*   Marca la ruta correspondiente al robot
*   
*
*/
public void markRoute(){
	//Cambiao
   maze.markPoint(pos);
}
   
   /** Pilot
   *   Interfaz que contiene metodos para pilotear el robot
   *   
   *
   */
	private interface Pilot{
      /** setCourse
      *   Metodo que funciona para fijar el periodo de muestreo
      *   dependiendo de la direccion del robot
      *   @param perido de muestreo
      *
      */
		public void setCourse(double delta_t);
	}
   
   /** PilotR   
   *   Clase que sirve para pilotear a la derecha el robot
   *     
   *
   */
	private class PilotR implements Pilot{

		   // set the lookingForRightWall depending on the left sensor state
		private boolean lookingFor=!rightSensor.senseWall();
	   
       
	   public void setCourse(double delta_t){
		   if(leftSensor.senseWall()) {
			   turnRight();
			  return;
			  }
		  if(frontSensor.senseWall()) {
			  turnLeft();
			  return;
		  }
		  if(rightSensor.senseWall()) {
			  lookingFor=false;
			  pos = pos.plus(v.times(delta_t));  
			  return;
		  }
		  if(lookingFor) {
			  pos = pos.plus(v.times(delta_t));  
			  return;
		  }
		  turnRight();
		  lookingFor=true;
		  return;   
	   }
	}
   
   /** PilotL   
   *   Clase que sirve para pilotear a la izquierda el robot
   *     
   *
   */
	private class PilotL implements Pilot{

		private boolean lookingFor=!leftSensor.senseWall();

	   public void setCourse(double delta_t){
		   if(rightSensor.senseWall()) {
			   turnLeft();
			  return;
			  }
		  if(frontSensor.senseWall()) {
			  turnRight();
			  return;
		  }
		  if(leftSensor.senseWall()) {
			  lookingFor=false;
			  pos = pos.plus(v.times(delta_t));  
			  return;
		  }
		  if(lookingFor) {
			  pos = pos.plus(v.times(delta_t));  
			  return;
		  }
		  turnLeft();
		  lookingFor=true;
		  return;   
	   }
	   
	}
   /** DistanceSensor
   *  Clase que trabaja la distancia del sensor
   *  @param dir corresponde a la direccion donde apunta el sensor
   *  @param range corresponde al rango establecido para el sensor
   */
   private class DistanceSensor {  
	   public DistanceSensor(Vector2D dir, double range) {
	         this.dir = dir;
	         this.range = range;
	      }
         /**turnLeft
         *  Metodo que provee el gito hacia la izquierda del sensor 
         */
	      public void turnLeft(){
	         dir.turnLeft();
	      }
         /**turnRight
         *  Metodo que provee el gito hacia la derecha del sensor 
         */         
	      public void turnRight() {
	         dir.turnRight();
	      }
         /**senseWall
         *  Metodo que verifica la existencia de alguna muralla cercana
         *  @return verdadero si existe muralla o falso si es que no la hay 
         */         
	      public boolean senseWall(){
	    	  Vector2D s = new Vector2D();
	    	  for(float i=0;i<range;i+=presition) {
	    		  s=pos.plus(this.dir.times(i)); 
				  if (world.isThere_a_wall((int)s.getX(),(int)s.getY()))
				  	return true;
	    	  }
	    	  return false;
	      }
         
         /** toString
         *   Metodo que entrega la informacion de senWall
         *   @return Palabra correspondiente a la informacion de sensWall 
         */
	      public String toString() {
	         return " "+senseWall();
	      }
	      
	      private Vector2D dir;  // orientation direction with respect to forward robot's direction
	      private double range;
	      private static final double presition = 0.4;
   }
   
   /** Clase RobotView
   *   Esta es la clase anidada dentro de Robot que permite dibujar al robot dentro
   *   del laberinto.
   */
   private class RobotView {
		public RobotView() {}
      
      /**draw
      *  Metodo que pinta en el frame el robot
      *  @param Imagen que sera repintada  
      */
		public void draw(Graphics2D g) {
			//Pintar cuerpo del robot
			g.setColor(Color.orange);
			int size_r=11;
			g.fillOval((int)(pos.getX()-Math.floor(size_r*0.5)),(int)(pos.getY()-Math.floor(size_r*0.5)), size_r, size_r);
			
			Vector2D v;
			int size_s=3;
			
			//pintar sensor frontal
			if(frontSensor.senseWall())
				g.setColor(Color.green);
			else
				g.setColor(Color.blue);
			v=pos.plus( frontSensor.dir.times(4.0));
			g.fillOval((int)(v.getX()-Math.floor(size_s*0.5)),(int)(v.getY()-Math.floor(size_s*0.5)), size_s, size_s);
			
			//pintar sensor derecho
			if(rightSensor.senseWall())
				g.setColor(Color.green);
			else
				g.setColor(Color.blue);
			v=pos.plus( rightSensor.dir.times(4.0));
			g.fillOval((int)(v.getX()-Math.floor(size_s*0.5)),(int)(v.getY()-Math.floor(size_s*0.5)),size_s,size_s);
			
			//pintar sensor izquierdo
			if(leftSensor.senseWall())
				g.setColor(Color.green);
			else
				g.setColor(Color.blue);
			v=pos.plus( leftSensor.dir.times(4.0));
			g.fillOval((int)(v.getX()-Math.floor(size_s*0.5)),(int)(v.getY()-Math.floor(size_s*0.5)),size_s,size_s);
			
			//setea el color en su default
			g.setColor(Color.black);
			
		}
		 
	}
   
   public void draw(Graphics2D g) {
	   RobotView view=new RobotView();
	   view.draw(g);
	   }
      
   private Maze maze;
   private Vector2D pos;
   private Vector2D v;
   private MyWorld world;
   private DistanceSensor rightSensor, frontSensor, leftSensor;
   private Pilot pilot;
}