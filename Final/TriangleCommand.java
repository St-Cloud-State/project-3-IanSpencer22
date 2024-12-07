import java.awt.*;

public class TriangleCommand extends Command {
    private Triangle triangle;
    private int pointCount;

    public TriangleCommand() {
        this(null, null, null);
        pointCount = 0;
    }

    public TriangleCommand(Point point) {
        this(point, null, null);
        pointCount = 1;
    }

    public TriangleCommand(Point point1, Point point2) {
        this(point1, point2, null);
        pointCount = 2;
    }

    public TriangleCommand(Point point1, Point point2, Point point3) {
        triangle = new Triangle(point1, point2, point3);
        pointCount = 3;
    }

    public void setTrianglePoint(Point point) {
        if (pointCount == 0) {
            triangle.setPoint1(point);
            pointCount++;
        } else if (pointCount == 1) {
            triangle.setPoint2(point);
            pointCount++;
        } else if (pointCount == 2) {
            triangle.setPoint3(point);
            pointCount++;
        }
    }

    @Override
    public void execute() {
        model.addItem(triangle);
    }

    @Override
    public boolean undo() {
        model.removeItem(triangle);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }

    public boolean end() {
        if (triangle.getPoint1() == null || triangle.getPoint2() == null || triangle.getPoint3() == null) {
            undo();
            return false;
        }
        return true;
    }
} 