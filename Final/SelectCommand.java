import java.awt.*;
import java.util.*;
public class SelectCommand extends Command {
  private Item item;
  public SelectCommand() {
    System.out.println("SelectCommand initialized. Ready to select items.");
  }
  public boolean setPoint(Point point) {
    System.out.println("Attempting to select an item at point: " + point);
	boolean found = false;
    Enumeration enumeration = model.getItems();
    while (enumeration.hasMoreElements()) {
      item = (Item)(enumeration.nextElement());
      if (item.includes(point)) {
        model.markSelected(item);
		found = true;
        System.out.println("Item selected: " + item);
        break;
      }
    }
	if (!found) {
	  System.out.println("No item found at point: " + point);
	}
	if (found) {
	  model.setChanged();
	  // Call a method to refresh the view, e.g., view.refresh();
	}
	return found;
  }
  public boolean undo() {
    System.out.println("Undoing selection of item: " + item);
    model.unSelect(item);
    return true;
  }
  public boolean  redo() {
    System.out.println("Redoing selection of item: " + item);
    execute();
    return true;
  }
  public void execute() {
    System.out.println("Executing selection of item: " + item);
    model.markSelected(item);
    model.setChanged();
    // Call a method to refresh the view, e.g., view.refresh();
  }
}
