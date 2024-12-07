//Nathan Nelson

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class MoveButton extends JButton implements ActionListener {
    private JPanel drawingPanel;
    private View view;
    private UndoManager undoManager; 
    private MouseHandler mouseHandler;
    private Point startPoint;

    public MoveButton(View view, JPanel drawingPanel, UndoManager undoManager) {
        super("Move");
        this.view = view;
        this.drawingPanel = drawingPanel;
        this.undoManager = undoManager; 
        this.mouseHandler = new MouseHandler();
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        view.setCursor(new Cursor(Cursor.MOVE_CURSOR)); 
        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            startPoint = View.mapPoint(event.getPoint()); 
        }
        @Override
        public void mouseReleased(MouseEvent event) {
            if (startPoint == null) return;
            Point endPoint = View.mapPoint(event.getPoint()); 
            int dx = endPoint.x - startPoint.x;
            int dy = endPoint.y - startPoint.y;
            ArrayList<Item> selectedItems = new ArrayList<>();
            Enumeration<Item> items = Command.model.getSelectedItems().elements();
            while (items.hasMoreElements()) {
                selectedItems.add(items.nextElement());
            }

            if (!selectedItems.isEmpty()) {
                MoveCommand moveCommand = new MoveCommand(selectedItems, dx, dy);
                undoManager.beginCommand(moveCommand);
                undoManager.endCommand(moveCommand);
            }

            // Reset state
            startPoint = null;
            drawingPanel.removeMouseListener(this);
            drawingPanel.removeMouseMotionListener(this);
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
            view.refresh(); 
        }
    }
}
