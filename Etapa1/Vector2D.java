/* same class as assignment 1 */

public class Vector2D {
   private double x, y;  // we will use cartesian coordinates
   public Vector2D () {
      x = y = 0;
   }
   public Vector2D (double x, double y) {
      this.x = x;
      this.y = y;
   }
   public double getX(){
      return x;
   }
   public double getY(){
      return y;
   }
   public Vector2D getUnitary(){
      return times(getModule());
   }
   public double getModule() {
      return Math.sqrt(x*x+y*y);
   } 
   public void setTo(double x, double y){ // to set a new position
      this.x=x;
      this.y=y;
   }
   public void turnLeft() {
      setTo(-y, x);
   }
   public void turnRight() {
      setTo(y, -x);
   }
   public Vector2D plus(Vector2D v) {
      if (v==null) return new Vector2D(x,y);
      else return new Vector2D(x+v.x, y+v.y);
   }
   public Vector2D times(double scalar) {
      return new Vector2D(x*scalar, y*scalar);
   }
   public Vector2D minus(Vector2D v) {
      if (v==null) return  new Vector2D(x,y);
      else return new Vector2D(x-v.x, y-v.y);
   }
   public double distanceTo(Vector2D p) {
      double dx=p.x-x;
      double dy=p.y-y;
      return Math.sqrt(dx*dx+dy*dy);
   }
   public String getDescription(){
      return "x,\ty";
   }
   public String toString(){
      return x + ",\t" + y;
   }
}
   