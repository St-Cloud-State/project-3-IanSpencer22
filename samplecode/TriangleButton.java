import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TriangleButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private MouseHandler mouseHandler;
    private TriangleCommand triangleCommand;
    private UndoManager undoManager;
    private List<Point> points;

    public TriangleButton(UndoManager undoManager, View view, JPanel drawingPanel) {
        super("Triangle");
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
        System.out.println("Triangle draw mode activated");
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            Point point = View.mapPoint(event.getPoint());
            points.add(point);

            if (points.size() == 3) {
                // Create and execute TriangleCommand
                triangleCommand = new TriangleCommand(points.get(0), points.get(1), points.get(2));
                undoManager.beginCommand(triangleCommand);
                undoManager.endCommand(triangleCommand);
                drawingPanel.removeMouseListener(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                points.clear();
                System.out.println("Triangle created");
            }
            view.setTemporaryPoints(points); // Update the view with the current points
            view.refresh();
        }
    }
} 