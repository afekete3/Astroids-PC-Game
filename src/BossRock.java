
import java.awt.Polygon;

public class BossRock extends VectorSprite {
    int HP;

    public BossRock() {

        active = true;
        HP = 100;
        shape = new Polygon();
        shape.addPoint(0, -75);
        shape.addPoint(35, -35);
        shape.addPoint(55, -55);
        shape.addPoint(50, -15);
        shape.addPoint(75, 0);
        shape.addPoint(50, 15);
        shape.addPoint(55, 55);
        shape.addPoint(35, 35);
        shape.addPoint(0, 75);
        shape.addPoint(-35, 35);
        shape.addPoint(-55, 55);
        shape.addPoint(-50, 15);
        shape.addPoint(-75, 0);
        shape.addPoint(-50, -15);
        shape.addPoint(-55, -55);
        shape.addPoint(-35, -35);

        drawShape = new Polygon();
        drawShape.addPoint(0, -75);
        drawShape.addPoint(35, -35);
        drawShape.addPoint(55, -55);
        drawShape.addPoint(50, -15);
        drawShape.addPoint(75, 0);
        drawShape.addPoint(50, 15);
        drawShape.addPoint(55, 55);
        drawShape.addPoint(35, 35);
        drawShape.addPoint(0, 75);
        drawShape.addPoint(-35, 35);
        drawShape.addPoint(-55, 55);
        drawShape.addPoint(-50, 15);
        drawShape.addPoint(-75, 0);
        drawShape.addPoint(-50, -15);
        drawShape.addPoint(-55, -55);
        drawShape.addPoint(-35, -35);

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
     public void updatePosition()
    {
        angle += turnRate;
        super.updatePosition();
    }
}
