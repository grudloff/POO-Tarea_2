import java.awt.Color;
import java.awt.Graphics2D;

public class Robot {
   private Robot() { 
      this(new Vector2D(), new Vector2D(), 0, null,false);
   }
   public Robot(Vector2D position, Vector2D velocity, double sensorRange, MyWorld w,boolean u) {
	   this.u=u;
	   pos = position;
	   v = velocity;
	   world = w;
	   Vector2D dir = v.getUnitary();
	   rightSensor = new DistanceSensor(new Vector2D(dir.getY(), -dir.getX()), sensorRange);
	   frontSensor = new DistanceSensor(dir, sensorRange);
	   leftSensor = new DistanceSensor(new Vector2D(-dir.getY(), dir.getX()), sensorRange);
   // similar to Robot in previous stage without the logic to set the course
   /// that ti is now asked to the pilot
	   pilot = new MyPilot(u);
   }



public Vector2D getPosition() {
   return pos;
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
		if(!u) {
	   pilot.setCourse(delta_t);
		}
		else {
			pilot.setCourse2(delta_t);
		}
	   return;
}
public String getDescription() {
   return pos.getDescription() + ",  leftS frontS rightS";
}
public String toString() {
   return pos.toString()+ "," +leftSensor.toString() + frontSensor.toString()+rightSensor.toString()+ ", " +v.toString();
}
public void markRoute(Maze m){
   m.markPoint(pos);
}
//
	public class Pilot {
	   public Pilot(){
		   // set the lookingForRightWall depending on the left sensor state
		   lookingFor=!rightSensor.senseWall();
	   }
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
	   private boolean lookingFor; //it is used to find the right wall at the beginning
	}
	public class MyPilot extends Pilot{
	   public MyPilot(boolean u){
		   //Cuando es true se usa la alternativa 2
		   super();
		   if(u)	lookingFor=!leftSensor.senseWall();
	   }
	   public void setCourse2(double delta_t){
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
	   
	   private boolean lookingFor; //it is used to find the right/left wall at the beginning and in corners
	}
   public class DistanceSensor {  // now public because it is also used my Pilot
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
   
   public class RobotView {
		public RobotView() {}

		public void draw(Graphics2D g) {
			//Pintar cuerpo del robot
			g.setColor(Color.orange);
			int size_r=11;
			g.fillOval((int)(pos.getX()-Math.floor(size_r*0.5)),(int)(pos.getY()-Math.floor(size_r*0.5)), size_r, size_r);
			
			Vector2D v;
			int size_s=3;
			
			//pintar sensor frontal
			g.setColor(Color.blue);
			v=pos.plus( frontSensor.dir.times(4.0));
			g.fillOval((int)(v.getX()-Math.floor(size_s*0.5)),(int)(v.getY()-Math.floor(size_s*0.5)), size_s, size_s);
			
			//pintar sensor derecho
			g.setColor(Color.blue);
			v=pos.plus( rightSensor.dir.times(4.0));
			g.fillOval((int)(v.getX()-Math.floor(size_s*0.5)),(int)(v.getY()-Math.floor(size_s*0.5)),size_s,size_s);
			
			//pintar sensor izquierdo
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
      
   private Vector2D pos;
   private Vector2D v;
   private MyWorld world;
   private DistanceSensor rightSensor, frontSensor, leftSensor;
   private MyPilot pilot;
   private boolean u;
}