//Nathan Nelson
import java.awt.*;
import java.util.ArrayList;

public class MoveCommand extends Command {
    private ArrayList<Item> items; 
    private int dx, dy; 

    public MoveCommand(ArrayList<Item> items, int dx, int dy) {
        this.items = new ArrayList<>(items); 
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        for (Item item : items) {
            item.translate(dx, dy); 
        }
        model.setChanged(); 
    }

    @Override
    public boolean undo() {
        for (Item item : items) {
            item.translate(-dx, -dy); 
        }
        model.setChanged(); 
        return true;
    }

    @Override
    public boolean redo() {
        execute(); 
        return true;
    }
}
