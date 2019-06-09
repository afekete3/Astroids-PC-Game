
import java.awt.Polygon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RP4K
 */
public class Minigun extends VectorSprite{

    public Minigun(double x, double y, double a) {
    
        active = true;
        shape = new Polygon();
        shape.addPoint(0, -3);
        shape.addPoint(3, 0);
        shape.addPoint(0, 3);
        shape.addPoint(-7, 0);
        drawShape = new Polygon();
        drawShape.addPoint(0, -3);
        drawShape.addPoint(3, 0);
        drawShape.addPoint(0, 3);
        drawShape.addPoint(-7, 0);
        xPosition = x;
        yPosition = y;
        angle = a;
        thrust = 10;

        xSpeed = Math.cos(a)* thrust;
        ySpeed = Math.sin(a)* thrust;
    }


}
