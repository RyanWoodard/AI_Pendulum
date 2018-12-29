import java.lang.Math.*;
import java.awt.* ; 
public class Thing {
  // set acceleration of pendulums
  public static double acceleration = 0.1;
  // set the pendulum attached below this pendulum
  Thing swing = null;
  Ball ball;
  // set 
  double theta;
  // stores value of theta for when the pendulum is reset
  double theta2;
  double length;
  //set fixed x and y
  double x1 = 300;
  double y1 = 300;
  double velocity = 0;

  // instantiates the first pendulum
  public Thing(double theta, double length,int segments){
    // sets the angles
    this.theta = theta;
    theta2 = this.theta;
    // sets the length
    this.length = length;
    // recursively creates the next pendulum if necessary
    segments -= 1;
    if(segments != 0)
      swing = new Thing(0.01,100,getx2(),gety2(), segments);
    // sets ball if no more pendulums to create
    else
      ball = new Ball(getx2(),gety2());
  }
  
  
  public Thing(double theta, double length, double x1, double y1,int segments){
    // set angle
    this.theta = theta;
    this.theta2 = theta;
    // set length
    this.length = length;
    // set x and y
    this.x1 = x1;
    this.y1 = y1;
    // recursively set the next pendulum if neccesary
    segments -= 1;
    if(segments != 0)
      swing = new Thing(Math.random() * 2*Math.PI , Math.random()*50+50,getx2(),gety2(), segments);
    // attach ball if last pendulum
    else
      ball = new Ball(getx2(),gety2());
  }
  
  
  public void paint1(Graphics g){
    // paints the whole pendulum recursively
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.BLUE);
    g2d.drawLine((int)x1,(int)y1,(int)(getx2()),(int)(gety2()));
    if(swing != null){
      swing.paint1(g2d);
    }
    if(ball != null)
      ball.paint1(g2d);
  }
  
  // physics for rotating the first pendulum
  public void rotate(){
    velocity = velocity + acceleration*Math.cos(theta+Math.PI/2)/length;
    theta = theta - velocity;
    if(swing != null){
      swing.rotate(getx2(),gety2(),velocity*length);
      velocity = velocity + swing.velocity*swing.length/length/200;
    }
    if(ball != null)
      ball.update(getx2(), gety2());
    velocity = velocity*0.995;
  }
  
  // physics for rotating the other pendulums
  public void rotate(double newX, double newY, double v){
    x1 = newX;
    y1 = newY;
    velocity = velocity + acceleration*Math.cos(theta+Math.PI/2)/length - v/length/200;
    theta = theta - velocity;
    if(swing != null){
      swing.rotate(getx2(),gety2(),velocity/length/2);
      velocity = velocity + swing.velocity*swing.length/length/200;
    }
    if(ball != null)
      ball.update(getx2(), gety2());
    velocity = velocity*0.995;
  }
  
  // returns a pendulum that is similar to the current one
  public Thing mutate(){
    // slightly changes lenght and angle
    double angle = this.theta + (Math.random()*4 - 2);
    double lengtha = this.length + (Math.random()*40 - 20);
    if(lengtha > 100)
      lengtha = 100;
    if(lengtha < 20)
      lengtha = 20;
    Thing mutation = new Thing(angle, lengtha, 1);
    if(this.swing != null){
      mutation.ball = null;
      mutation.swing = this.swing.mutate();
    }
    if(this.ball != null){
      mutation.ball.count2 = this.ball.count2 + (int)(Math.random()*500 - 250);
      if(mutation.ball.count2 < 70)
        mutation.ball.count2 = 70;
      mutation.ball.count = mutation.ball.count2;
    }
  return mutation;
  
  }
  
  
  
  
  
  // getters
  public double getx2(){
    return x1 - Math.sin(theta)*length;
  }
  
  public double gety2(){
    return y1 - Math.cos(theta)*length;
  }
  
  public double getDistance(){
    if(ball != null)
      return ball.distance;
    else
      return swing.getDistance();
  }
  
  // recursively resets the whole pendulum
  public void reset(){
    this.theta = theta2;
    this.velocity = 0;
    if(swing != null)
      swing.reset();
  }
  
  
}