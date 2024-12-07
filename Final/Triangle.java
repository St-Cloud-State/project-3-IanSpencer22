import java.awt.*;
import java.util.Stack;

public class Triangle extends Item {
    private Point v1, v2, v3;
    private Stack<Point> undoStack = new Stack<>();
    private Stack<Point> redoStack = new Stack<>();

    public Triangle(Point v1, Point v2, Point v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
    }

    public Triangle(Point p1, Point p2) {
        this.v1 = p1;
        this.v2 = p2;
        this.v3 = null;
    }

    public Triangle(Point p1) {
        this.v1 = p1;
        this.v2 = null;
        this.v3 = null;
    }

    public Triangle() {
        this.v1 = null;
        this.v2 = null;
        this.v3 = null;
    }

    @Override
    public boolean includes(Point point) {
        // Calculate the area of the triangle using the vertices
        double areaABC = area(v1, v2, v3);
        // Calculate the area of the triangle formed by the point and two vertices of the triangle
        double areaPAB = area(point, v1, v2);
        double areaPBC = area(point, v2, v3);
        double areaPCA = area(point, v3, v1);

        // Check if the sum of the areas of the smaller triangles equals the area of the original triangle
        return Math.abs(areaABC - (areaPAB + areaPBC + areaPCA)) < 1e-6;
    }

    private double area(Point a, Point b, Point c) {
        return Math.abs(a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0;
    }

    @Override
    public void render(UIContext uiContext) {
        uiContext.drawLine(v1, v2);
        uiContext.drawLine(v2, v3);
        uiContext.drawLine(v3, v1);
    }

    public void setPoint1(Point point) {
        v1 = point;
    }

    public void setPoint2(Point point) {
        v2 = point;
    }

    public void setPoint3(Point point) {
        v3 = point;
    }

    public Point getPoint1() {
        return v1;
    }

    public Point getPoint2() {
        return v2;
    }

    public Point getPoint3() {
        return v3;
    }

    @Override
    public void translate(int dx, int dy) {
        if (v1 != null) {
            v1.translate(dx, dy);
        }
        if (v2 != null) {
            v2.translate(dx, dy);
        }
        if (v3 != null) {
            v3.translate(dx, dy);
        }
        undoStack.push(new Point(dx, dy));
        redoStack.clear(); // Clear redo stack on new translation
        printPoints();
    }

    public boolean undo() {
        if (!undoStack.isEmpty()) {
            Point lastTranslation = undoStack.pop();
            System.out.println("Undoing translation: (" + lastTranslation.x + ", " + lastTranslation.y + ")");
            v1.translate(-lastTranslation.x, -lastTranslation.y);
            v2.translate(-lastTranslation.x, -lastTranslation.y);
            v3.translate(-lastTranslation.x, -lastTranslation.y);
            redoStack.push(lastTranslation);
            printPoints();
            return true;
        }
        System.out.println("Undo stack is empty.");
        return false;
    }

    public boolean redo() {
        if (!redoStack.isEmpty()) {
            Point lastTranslation = redoStack.pop();
            System.out.println("Redoing translation: (" + lastTranslation.x + ", " + lastTranslation.y + ")");
            v1.translate(lastTranslation.x, lastTranslation.y);
            v2.translate(lastTranslation.x, lastTranslation.y);
            v3.translate(lastTranslation.x, lastTranslation.y);
            undoStack.push(lastTranslation);
            printPoints();
            return true;
        }
        System.out.println("Redo stack is empty.");
        return false;
    }

    private void printPoints() {
        System.out.println("Current Points: v1=" + v1 + ", v2=" + v2 + ", v3=" + v3);
    }
} 