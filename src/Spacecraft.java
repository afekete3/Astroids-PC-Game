
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Spacecraft extends VectorSprite {

    int lives;

    public Spacecraft() {
        lives = 5;
        active = true;
        gunLeft = new Polygon();
        gunLeft.addPoint(0, -12);
        gunLeft.addPoint(22, -10);
        gunLeft.addPoint(15, -6);
        gunRight = new Polygon();
        gunRight.addPoint(15, 6);
        gunRight.addPoint(22, 10);
        gunRight.addPoint(0, 12);

        drawgunLeft = new Polygon();
        drawgunLeft.addPoint(0, -12);
        drawgunLeft.addPoint(22, -10);
        drawgunLeft.addPoint(15, -6);
        drawgunRight = new Polygon();
        drawgunRight.addPoint(15, 6);
        drawgunRight.addPoint(22, 10);
        drawgunRight.addPoint(0, 12);

        shape = new Polygon();
        shape.addPoint(25, 0);
        shape.addPoint(-10, 15);
        shape.addPoint(0, 0);
        shape.addPoint(-10, -15);

        drawShape = new Polygon();
        drawShape.addPoint(25, 0);
        drawShape.addPoint(-10, 15);
        drawShape.addPoint(0, 0);
        drawShape.addPoint(-10, -15);


        xPosition = 600;
        yPosition = 325;
        angle = -Math.PI / 2;
        thrust = 0.1;
        turnRate = 0.1;
    }
  
    public void accelerate() {
        xSpeed += Math.cos(angle)* thrust;
        ySpeed += Math.sin(angle)* thrust;
    }

    public void rotateRight() {
        angle += turnRate;

    }
    public void rotateLeft() {
        angle -= turnRate;
    }
  
    public void decelerate() {
        int brakeX = (int)Math.round(xSpeed);
        if (brakeX != 0){
        xSpeed += Math.cos(angle + Math.PI)* thrust;
        }
        else{
        xSpeed = 0;
        }
        int brakeY = (int)Math.round(ySpeed);
        if (brakeY != 0){
        ySpeed += Math.sin(angle + Math.PI)* thrust;
        }
        else{
        ySpeed = 0;
        }
    }

    public void updatePosition() {
        
      
        super.updatePosition();
        int w,z;
        for (int i = 0; i < gunLeft.npoints; i++){
             w = (int)Math.round(gunLeft.xpoints [i] * Math.cos(angle) -
                                gunLeft.ypoints [i] * Math.sin(angle));
             z = (int)Math.round(gunLeft.xpoints [i] * Math.sin(angle) +
                                gunLeft.ypoints [i] * Math.cos(angle));
            drawgunLeft.xpoints [i] = w;
            drawgunLeft.ypoints [i] = z;
             w = (int)Math.round(gunRight.xpoints [i] * Math.cos(angle) -
                                gunRight.ypoints [i] * Math.sin(angle));
             z = (int)Math.round(gunRight.xpoints [i] * Math.sin(angle) +
                                gunRight.ypoints [i] * Math.cos(angle));
            drawgunRight.ypoints [i] = z;
            drawgunRight.xpoints [i] = w;
        }

       drawgunLeft.invalidate();
       drawgunRight.invalidate();
       drawgunLeft.translate((int)Math.round(xPosition), (int)Math.round(yPosition));
       drawgunRight.translate((int)Math.round(xPosition), (int)Math.round(yPosition));
    }

    public void paintShip(Graphics g) {
        g.drawPolygon(drawShape);
        g.setColor(Color.red);
        g.fillPolygon(drawgunLeft);
        g.drawPolygon(drawgunLeft);
        g.fillPolygon(drawgunRight);
        g.drawPolygon(drawgunRight);
    }

    public void hit() {
        active = false;
        counter = 0;
        lives--;
    }
    public void reset() {
            xPosition = 600;
            yPosition = 325;
            xSpeed = 0;
            ySpeed = 0;
            angle = -Math.PI / 2;
            active = true;
    }
}
