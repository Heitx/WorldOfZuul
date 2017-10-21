
import BLL.Game;
import DAL.item.ItemType;
import DAL.yaml.ItemParser;
import java.io.IOException;
import java.util.Map;

public class Main {
	/* runs the game */
	public static void main(String[] args) {
		new Game().start();

		/* Testing the item parser to obtain the item database from text
		ItemParser parser = ItemParser.getInstance();

		try {
			Map<Integer, Map<String, Object>> map = parser.getDatabase();
			System.out.println(map.get(0).get("name"));
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		*/
	}

	private static void itemTemplate(int id, String name, ItemType type) {
		System.out.println(id + ":");
		System.out.println(String.format("  name: \"%s\"", name));
		System.out.println("  description: \"...\"");
		System.out.println("  itemType: " + type.ordinal());
		System.out.println("  pickupable: true");
		System.out.println("  dropable: true");
		System.out.println("  weight: 1.0");
	}
}