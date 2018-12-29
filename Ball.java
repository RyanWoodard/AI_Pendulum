import java.lang.Math.*;
import java.awt.* ; 
public class Ball {
  public static double acceleration = 0.3;
  double length = 50;
  static boolean launched,landed;
  int count = (int)(Math.random()*900+400);
  int count2 = count;
  double x = 300;
  double y = 300;
  double vx = 0;
  double vy = 0;
  double prevx, prevy;
  double distance = 0;
  
  // creates a new instance of a ball
  public Ball(double x,double y){
    this.x = x;
    this.y = y;
    launched = false;
    landed = false;
  }

  // updates the position of the ball
  public void update(double x, double y){
    if(!launched){
      landed = false;
      prevx = this.x;
      prevy = this.y;
      this.x = x;
      this.y = y;
      count-=1;
      if(count == 0)
        launch();
    }
    else{
      if(this.y < 650){
        this.x = this.x - vx;
        this.y = this.y - vy;
        vy = vy - acceleration;
      }
      else{
      distance = this.x;
      landed = true;
      count = count2;
  
      }
    }
  }
  
  // paints the ball
  public void paint1(Graphics g){
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.GREEN);
    g2d.fillOval((int)x-5,(int)y-5,10,10);
  }
  
  // releases the ball from the pendulum
  public void launch(){
    launched = true;
    vx = prevx - x;
    vy = prevy - y;
  }
  
  
}