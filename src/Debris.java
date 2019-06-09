
import java.awt.Polygon;

public class Debris extends VectorSprite {

    public Debris(double x, double y) {

        shape = new Polygon();
        shape.addPoint(1, 1);
        shape.addPoint(-1, -1);
        shape.addPoint(-1, 1);
        shape.addPoint(1, -1);

        drawShape = new Polygon();
        drawShape.addPoint(1, 1);
        drawShape.addPoint(-1, -1);
        drawShape.addPoint(-1, 1);
        drawShape.addPoint(1, -1);

        xPosition = x;
        yPosition = y;
        
        double a;
        a = Math.random()* 2* Math.PI;
        xSpeed = Math.cos(a)* a;
        ySpeed = Math.sin(a)* a;

    }

}
