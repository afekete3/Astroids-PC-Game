
import java.awt.Polygon;

public class EnergyBlaster extends VectorSprite{
    // keeps count of how many small rocks that were produced from the bigrocks were destroyed by each energyblast
    int smallCount;

    public EnergyBlaster(double x, double y, double a) {
        smallCount = 0;
        active = true;
        shape = new Polygon();
        shape.addPoint(0, 0);
        shape.addPoint(-30, -60);
        shape.addPoint(30, -60);
        shape.addPoint(60, 0);
        shape.addPoint(30, 60);
        shape.addPoint(-30, 60);
        drawShape = new Polygon();
        drawShape.addPoint(0, 0);
        drawShape.addPoint(-30, -60);
        drawShape.addPoint(30, -60);
        drawShape.addPoint(60, 0);
        drawShape.addPoint(30, 60);
        drawShape.addPoint(-30, 60);
        xPosition = x;
        yPosition = y;
        angle = a;
        thrust = 2;

        xSpeed = Math.cos(a)* thrust;
        ySpeed = Math.sin(a)* thrust;
    }

}
