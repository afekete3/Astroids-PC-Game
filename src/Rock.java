
import java.awt.Polygon;

public class Rock extends VectorSprite {

int rockSize;
    public Rock(int s) {
        rockSize = s;
        initializeRock();
    }
    public Rock(double x, double y, int size) {
        rockSize = size;
        initializeRock();
        xPosition = x;
        yPosition = y;
    }

    public void updatePosition()
    {
        angle += turnRate;
        super.updatePosition();
    }
    public void initializeRock() {
        active = true;
        shape = new Polygon();
        shape.addPoint(15* rockSize, 1* rockSize);
        shape.addPoint(2* rockSize, 17* rockSize);
        shape.addPoint(-12* rockSize, 5* rockSize);
        shape.addPoint(-8* rockSize, -7* rockSize);
        shape.addPoint(10* rockSize, -17* rockSize);
        drawShape = new Polygon();
        drawShape.addPoint(15* rockSize, 1* rockSize);
        drawShape.addPoint(2* rockSize, 17* rockSize);
        drawShape.addPoint(-12* rockSize, 5* rockSize);
        drawShape.addPoint(-8* rockSize, -7* rockSize);
        drawShape.addPoint(10* rockSize, -17* rockSize);
        xPosition = 600;
        yPosition = 325;
        turnRate = Math.random()/ 21;
        double h,a;
        h = Math.random()+ 1;
        a = Math.random()* 2* Math.PI;
        xSpeed = Math.cos(a)* h;
        ySpeed = Math.sin(a)* h;
        h = Math.random()* 400 + 200;
        a = Math.random()* 2* Math.PI;
        xPosition = Math.cos(a)* h + 600;
        yPosition = Math.sin(a)* h + 325;
    }

}
