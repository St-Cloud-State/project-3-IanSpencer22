import java.awt.*;
import java.util.List;

public class PolygonCommand extends Command {
    private Polygon polygon;

    public PolygonCommand(List<Point> points) {
        polygon = new Polygon(points);
    }

    @Override
    public void execute() {
        model.addItem(polygon);
    }

    @Override
    public boolean undo() {
        model.removeItem(polygon);
        return true;
    }

    @Override
    public boolean redo() {
        execute();
        return true;
    }
} 