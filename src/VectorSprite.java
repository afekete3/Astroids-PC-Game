


import java.awt.Graphics;
import java.awt.Polygon;


public class VectorSprite {
    double xPosition;
    double yPosition;
    int counter;
    double xSpeed;
    double ySpeed;
    double angle;
    double thrust;
    double turnRate;
    boolean active;
    Polygon shape, drawShape;
    Polygon gunLeft, gunRight;
    Polygon drawgunLeft, drawgunRight;

    // list of guns
    boolean miniGun, energyBlaster;

    public void WrapAround() {
        if (xPosition > 1200) {
            xPosition = 0;
        }
        if (yPosition > 650) {
            yPosition = 0;
        }
        if (xPosition < 0) {
            xPosition = 1200;
        }
        if (yPosition < 0) {
            yPosition = 650;
        }

    }
    public void paint(Graphics g) {
        g.drawPolygon(drawShape);
    }
    public void updatePosition() {
        counter ++;
        xPosition += xSpeed;
        yPosition += ySpeed;
        WrapAround();
        int x,y;
        for(int i=0; i<shape.npoints; i++) {
            x = (int)Math.round(shape.xpoints [i] * Math.cos(angle) -
                                shape.ypoints [i] * Math.sin(angle));
            y = (int)Math.round(shape.xpoints [i] * Math.sin(angle) +
                                shape.ypoints [i] * Math.cos(angle));
            drawShape.xpoints [i] = x;
            drawShape.ypoints [i] = y;          
        }
        
       drawShape.invalidate();
      
       drawShape.translate((int)Math.round(xPosition), (int)Math.round(yPosition));
       
    }
    
   
}
