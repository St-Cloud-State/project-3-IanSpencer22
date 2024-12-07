import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PolygonButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private MouseHandler mouseHandler;
    private PolygonCommand polygonCommand;
    private UndoManager undoManager;
    private List<Point> points;

    public PolygonButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Polygon");
        this.undoManager = undoManager;
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.points = new ArrayList<>();
        addActionListener(this);
        mouseHandler = new MouseHandler();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.HAND_CURSOR));
        drawingPanel.addMouseListener(mouseHandler);
        System.out.println("Polygon draw mode activated");
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Point point = View.mapPoint(event.getPoint());
            if (SwingUtilities.isLeftMouseButton(event)) {
                points.add(point);
                view.setTemporaryPoints(points);
                view.refresh();
            } else if (SwingUtilities.isRightMouseButton(event) && points.size() > 2) {
                // Complete the polygon
                polygonCommand = new PolygonCommand(new ArrayList<>(points));
                undoManager.beginCommand(polygonCommand);
                undoManager.endCommand(polygonCommand);
                drawingPanel.removeMouseListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                points.clear();
                System.out.println("Polygon created");
            }
        }
    }
} 