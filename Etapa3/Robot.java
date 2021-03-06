import java.awt.Color;
import java.awt.Graphics2D;

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

public void setMaze(Maze maze) {
	this.maze=new Maze(maze);
}

public Maze getMaze() {
	return maze;
}

public Vector2D getPosition() {
   return pos;
}
public void setPosition(Vector2D pos) {
	this.pos=pos;
}
public Vector2D getVelocity() {
   return v;
}
public void turnLeft(){
   v.turnLeft();
   rightSensor.turnLeft();
   frontSensor.turnLeft();
   leftSensor.turnLeft();
   //System.out.println("turning left...");  // for debugging
   return; 
}
public void turnRight(){
   v.turnRight();
   rightSensor.turnRight();
   frontSensor.turnRight();
   leftSensor.turnRight();      
   //System.out.println("turning right...");
   return; 
}
public DistanceSensor getRightSensor() {
	return rightSensor;
}
public DistanceSensor getLeftSensor() {
	return leftSensor;
}
public DistanceSensor getFrontSensor() {
	return frontSensor;
}
public void moveDelta_t(double delta_t) {
	   pilot.setCourse(delta_t);
}
public String getDescription() {
   return pos.getDescription() + ",  leftS frontS rightS";
}
public String toString() {
   return pos.toString()+ "," +leftSensor.toString() + frontSensor.toString()+rightSensor.toString()+ ", " +v.toString();
}
public void markRoute(){
	//Cambiao
   maze.markPoint(pos);
}
//
	private interface Pilot{
		public void setCourse(double delta_t);
	}

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
   private class DistanceSensor {  
	   public DistanceSensor(Vector2D dir, double range) {
	         this.dir = dir;
	         this.range = range;
	      }
	      public void turnLeft(){
	         dir.turnLeft();
	      }
	      public void turnRight() {
	         dir.turnRight();
	      }
	      public boolean senseWall(){
	    	  Vector2D s = new Vector2D();
	    	  for(float i=0;i<range;i+=presition) {
	    		  s=pos.plus(this.dir.times(i)); 
				  if (world.isThere_a_wall((int)s.getX(),(int)s.getY()))
				  	return true;
	    	  }
	    	  return false;
	      }
	      public String toString() {
	         return " "+senseWall();
	      }
	      
	      private Vector2D dir;  // orientation direction with respect to forward robot's direction
	      private double range;
	      private static final double presition = 0.4;
   }
   
   private class RobotView {
		public RobotView() {}

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