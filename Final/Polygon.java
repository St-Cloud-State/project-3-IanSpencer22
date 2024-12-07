import java.awt.*;
import java.util.List;

public class Polygon extends Item {
    private List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public boolean includes(Point point) {
        int intersections = 0;
        int numPoints = points.size();
        for (int i = 0; i < numPoints; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get((i + 1) % numPoints);

            // Check if the ray intersects the edge (p1, p2)
            if (rayIntersectsSegment(point, p1, p2)) {
                intersections++;
            }
        }
        // If the number of intersections is odd, the point is inside
        return (intersections % 2) == 1;
    }

    private boolean rayIntersectsSegment(Point p, Point p1, Point p2) {
        // Ensure p1 is below p2
        if (p1.y > p2.y) {
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }

        // Check if the point is outside the vertical bounds of the segment
        if (p.y == p1.y || p.y == p2.y) {
            p.y += 0.1; // Adjust point slightly to avoid edge cases
        }

        if (p.y < p1.y || p.y > p2.y) {
            return false;
        }

        // Check if the point is to the right of the segment
        if (p.x >= Math.max(p1.x, p2.x)) {
            return false;
        }

        // Calculate the x-coordinate of the intersection of the ray with the segment
        if (p.x < Math.min(p1.x, p2.x)) {
            return true;
        }

        double xIntersection = (p.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
        return p.x < xIntersection;
    }

    @Override
    public void render(UIContext uiContext) {
        if (points.size() < 2) return; // Do not render if less than 2 points

        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);
            uiContext.drawLine(start, end);
        }
        // Optionally close the polygon if it has more than 2 points
        if (points.size() > 2) {
            Point start = points.get(points.size() - 1);
            Point end = points.get(0);
            uiContext.drawLine(start, end);
        }
    }

    @Override
    public void translate(int dx, int dy) {
        for (Point point : points) {
            point.translate(dx, dy);
        }
    }

    @Override
    public String toString() {
        return "Polygon with vertices " + points;
    }
} 