import java.awt.Graphics2D;
import java.awt.*;

public class RobotView {
	public RobotView() {}

	public void draw(Graphics2D g, Robot r) {
		//Pintar cuerpo del robot
		g.setColor(Color.orange);
		int size_r=10;
		Vector2D pos = new Vector2D(r.getPosition().getX()-size_r*0.5,r.getPosition().getY()-size_r*0.5);
		g.fillOval((int)pos.getX(),(int)pos.getY(), size_r, size_r);
		//pintar sensor frontal
		int size_s=4;
		g.setColor(Color.blue);
		Vector2D pos_f=r.getPosition().plus( r.getVelocity().getUnitary().times(4.0));
		
		g.fillOval((int)(pos_f.getX()-size_s*0.5),(int)(pos_f.getY()-size_s*0.5), size_s, size_s);
		//pintar sensor derecho
		g.setColor(Color.blue);
		Vector2D aux1 = new Vector2D(-1.0*r.getVelocity().getUnitary().getY(),r.getVelocity().getUnitary().getX());
		Vector2D pos_d=r.getPosition().plus(aux1.times(4.0));
		g.fillOval((int)(pos_d.getX()-size_s*0.5),(int)(pos_d.getY()-size_s*0.5), 4,4);
		//pintar sensor izquierdo
		g.setColor(Color.blue);
		Vector2D aux2 = new Vector2D(r.getVelocity().getUnitary().getY(),-1.0*r.getVelocity().getUnitary().getX());
		Vector2D pos_i=r.getPosition().plus(aux2.times(4.0));
		g.fillOval((int)(pos_i.getX()-size_s*0.5),(int)(pos_i.getY()-size_s*0.5), 4,4);
		g.setColor(Color.black);
	}

	 
}

