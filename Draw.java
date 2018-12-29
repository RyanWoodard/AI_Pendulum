import javax.swing.*; 
import java.awt.* ; 
import java.lang.Math;
public class Draw extends JPanel{
  
  Thing a,c,current;
  Thing[] b;
  
  public Draw(){
    // number must be even
    b = createManyThings(200);
    testThings(b);
       c = b[getMax(b)];
       current = c;
    for(int i = 0; i<100;i++){
      if(i%10 == 0){
        System.out.println("Current iteration: " + i);
        System.out.println("Current best distance: " + b[getMax(b)].getDistance());
      }
      b = evolve(b);
      testThings(b);
    }
    int temp = getMax(b);
    System.out.println(b[temp].getDistance());
    a = b[temp];
  }
  
  // Creates an array of random pendulums
  // i represents the size of the array
  public Thing[] createManyThings(int i){
    Thing[] list = new Thing[i];
    for(int j = 0;j < i;j++){
      list[j] = createRandom();
    }
    return list;
  }
  
  // Simulates the array of pendulums and sets the distance reached by the ball
  public void testThings(Thing[] list){
    
    for(Thing swing : list){
      if(swing.getDistance() == 0){
      while(!Ball.landed){
        swing.rotate();
      }
      swing.reset();
      Ball.launched = false;
      Ball.landed = false;
    }
    }
  }
  
  // Takes and array of pendulums and keeps the best half and mutates them to fill the empty spots
  public Thing[] evolve(Thing[] list){
    int max = getMax(list);
    double average = getAverage(list);
    Thing[] newList = new Thing[list.length];
    int count = 2;
    newList[0] = list[max];
    newList[1] = list[max].mutate();
    for(Thing swing : list){
      if(swing.getDistance() > average && count < list.length){
        newList[count] = swing;
        newList[count + 1] = swing.mutate();//mutation
        count += 2;
      }
      
    }
    if(count < list.length){
      while(count < list.length){
        newList[count] = list[max].mutate();//mutation
        count+=1;
      }
    }
    return newList;
  }
  
  // returns the index of the furthest throwing pendulum
  public int getMax(Thing[] list){
    double max = -99999;
    double temp;
    int index = 0;
    for(int i = 0;i < list.length;i++){
      temp = list[i].getDistance();
      if(temp > max){
        index = i;
        max = temp;
      }
      
    }

    return index;
  }
  
  // returns the average distance thrown by all the pendulums in the array
  public double getAverage(Thing[] list){
    double sum = 0;
    double temp;
    int index = 0;
    for(int i = 0;i < list.length;i++){
      temp = list[i].getDistance();
      sum = sum + temp;
      
    }
    return sum/list.length;
  }
  
  // moves the pendulum between frames
  private void move(){
    current.rotate();
    while(Ball.landed){
      current.reset();
      if(current == a)
        current = c;
      else
        current = a;
      Ball.launched = false;
      Ball.landed = false;
    }
    
  }
  
  // paints the pendulum
  @Override  public void paint(Graphics g) {
    
    Graphics2D g2d = (Graphics2D) g;
    super.paint(g2d);
    g2d.setColor(Color.BLUE);
    current.paint1(g2d);
    
    
  }
  
  // returns a random pendukum
  public Thing createRandom(){
    double theta = Math.random() * 2*Math.PI; 
    double length = Math.random()*50+50; 
    int segments = 4;
    return new Thing(theta, length, segments);
    
  }
  
  public static void main(String[] args)throws InterruptedException {     
    JFrame frame = new JFrame("COLLISIONS");   //Add our JPanel to the frame 
    Draw d = new Draw();     
    frame.add(d); 
    frame.setSize(1350, 700);     
    frame.setVisible(true); 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.setFocusable(true);
    while (true) {  
      d.move();
      d.repaint();  
      Thread.sleep(20);  
    }
  } 
}
