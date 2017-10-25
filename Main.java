
import BLL.Game;
import DAL.item.Color;
import DAL.item.ItemType;
import DAL.item.State;

public class Main {
	/* runs the game */
	public static void main(String[] args) {
		new Game().start();
	}

	private static void itemTemplate(int id, String name, ItemType type, Color color, State state) {
		System.out.println(id + ":");
		System.out.println(String.format("  name: \"%s\"", name));
		System.out.println("  description: \"...\"");
		System.out.println("  itemType: " + type.ordinal());
		System.out.println("  color: " + color.toString());
		System.out.println("  state: " + state.name());
		System.out.println("  pickupable: true");
		System.out.println("  dropable: true");
		System.out.println("  weight: 1.0");
	}
}