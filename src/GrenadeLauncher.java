import java.awt.Polygon;

public class GrenadeLauncher extends VectorSprite{

    public GrenadeLauncher(double x, double y, double a) {

        active = true;
        shape = new Polygon();
        shape.addPoint(4* 2,0* 2);
        shape.addPoint(1* 2,1* 2);
        shape.addPoint(1* 2,4* 2);
        shape.addPoint(-1* 2,2* 2);
        shape.addPoint(-4* 2,3* 2);
        shape.addPoint(-1* 2, 0* 2);
        shape.addPoint(-4* 2,-3* 2);
        shape.addPoint(-1* 2,-2* 2);
        shape.addPoint(1* 2,-4* 2);
        shape.addPoint(1* 2,-1* 2);

        drawShape = new Polygon();
        drawShape.addPoint(4* 2,0* 2);
        drawShape.addPoint(1* 2,1* 2);
        drawShape.addPoint(1* 2,4* 2);
        drawShape.addPoint(-1* 2,2* 2);
        drawShape.addPoint(-4* 2,3* 2);
        drawShape.addPoint(-1* 2, 0* 2);
        drawShape.addPoint(-4* 2,-3* 2);
        drawShape.addPoint(-1* 2,-2* 2);
        drawShape.addPoint(1* 2,-4* 2);
        drawShape.addPoint(1* 2,-1* 2);

        xPosition = x;
        yPosition = y;
        angle = a;
        thrust = 7;

        xSpeed = Math.cos(a)* thrust;
        ySpeed = Math.sin(a)* thrust;
    }

}

