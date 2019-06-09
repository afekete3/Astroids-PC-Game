
import java.awt.Polygon;


public class TrackerRock extends VectorSprite {
    double tempx, tempy;

    int rockSize;
     public TrackerRock(int s,double speed) {
        rockSize = s;
        initializeTrackerRock();
    }
    public TrackerRock(double x, double y, int size, double speed) {
        rockSize = size;
        initializeTrackerRock();
        xSpeed = speed;
        ySpeed = speed;
        xPosition = x;
        yPosition = y;
    }
    public void updatePosition( double xShip, double yShip , double xTrack, double yTrack)
   {
       double a = (xShip)- (xTrack);
       double b = (yShip) - (yTrack );
       double h = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2)); 
      
        
       double xMove = (a/h * xSpeed);
       double yMove = (b/h* ySpeed);
       counter ++;
      
        xPosition += xMove;
        yPosition += yMove;
        if (xPosition > 1200) {
            xPosition = 1200;
        }
        if (yPosition > 650) {
            yPosition = 650;
        }
        if (xPosition < 0) {
            xPosition = 0;
        }
        if (yPosition < 0) {
            yPosition = 0;
        }

        int x,y;
        for(int i=0; i<shape.npoints; i++) {
            x = (int)Math.round(shape.xpoints [i] * (a/h) -
                                shape.ypoints [i] * (b/h));
            y = (int)Math.round(shape.xpoints [i] * (b/h) +
                                shape.ypoints [i] * (a/h));
           
            drawShape.xpoints [i] = x;
            drawShape.ypoints [i] = y;          
        }
        
       drawShape.invalidate();
      
       drawShape.translate((int)Math.round(xPosition), (int)Math.round(yPosition));
    }
    public void initializeTrackerRock() {

        active = true;
        shape = new Polygon();
        shape.addPoint(30* rockSize, 0* rockSize);
        shape.addPoint(0* rockSize, 15* rockSize);
        shape.addPoint(-15* rockSize, 20* rockSize);
        shape.addPoint(0* rockSize, 5* rockSize);
        shape.addPoint(-20* rockSize, 5* rockSize);
        shape.addPoint(-20* rockSize, -5* rockSize);
        shape.addPoint(0* rockSize, -5* rockSize);
        shape.addPoint(-15* rockSize, -20* rockSize);
        shape.addPoint(0* rockSize, -15* rockSize);

        drawShape = new Polygon();
        drawShape.addPoint(30* rockSize, 0* rockSize);
        drawShape.addPoint(0* rockSize, 15* rockSize);
        drawShape.addPoint(-15* rockSize, 20* rockSize);
        drawShape.addPoint(0* rockSize, 5* rockSize);
        drawShape.addPoint(-20* rockSize, 5* rockSize);
        drawShape.addPoint(-20* rockSize, -5* rockSize);
        drawShape.addPoint(0* rockSize, -5* rockSize);
        drawShape.addPoint(-15* rockSize, -20* rockSize);
        drawShape.addPoint(0* rockSize, -15* rockSize);

        xPosition = 600;
        yPosition = 325;
        turnRate = 0.1;
        double h,a;
        h = Math.random()+ 5;
        a = Math.random()* 2* Math.PI;
        xSpeed = 2;
        ySpeed = 2;
        h = Math.random()* 400 + 200;
        a = Math.random()* 2* Math.PI;
        xPosition = Math.cos(a)* h + 600;
        yPosition = Math.sin(a)* h + 325;
       
        angle = -Math.PI / 2;
      
        
    }
}
