package DAL;

import BLL.UsableHandler;
import BLL.item.*;
import DAL.yaml.IOYaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles everything related to database of items.
 */
class DatabaseHandler implements Loadable {
	private IOYaml IOYaml;
	private List<Item> database;
	private UsableHandler usableHandler;

	/**
	 * Constructs a new Database handler.
	 * @param file where the item database is
	 */
	DatabaseHandler(File file) {
		this.IOYaml = new IOYaml(file);
		database = null;
	}

	/**
	 * Sets the usable handler given by business layer.
	 * @param usableHandler
	 */
	public void setUsableHandler(UsableHandler usableHandler) {
		this.usableHandler = usableHandler;
	}

	/**
	 * Gets the item at given index from the database.
	 * @param index
	 * @return item
	 */
	Item getItemById(int index) {
		return database.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load() throws IOException {
		Map<Integer, Map<String, Object>> map = IOYaml.getYaml().load(new FileReader(IOYaml.getFile()));

		if(!map.isEmpty()) {
			database = new ArrayList<>(map.size());

			Item item;
			int id;

			for(Map.Entry<Integer, Map<String, Object>> entry : map.entrySet()) {
				id = entry.getKey();

				switch(ItemType.valueOf((String) entry.getValue().get("itemType"))) {
					case DEFAULT: item = getItem(id, entry.getValue()); break;
					case COMPONENT: item = getComponent(id, entry.getValue()); break;
					case CLUE: item = getClue(id, entry.getValue()); break;
					case PORTALGUN: item = getPortalGun(id, entry.getValue()); break;
					default: item = getItem(id, entry.getValue());
				}

				if(entry.getValue().containsKey("usableId")) {
					item.setUsable(usableHandler.getUsable((Integer) entry.getValue().get("usableId")));
				}

				database.add(item);
			}
		}
	}

	/**
	 * Gets an item based on the Yaml given.
	 * Only invokes by {@link DatabaseHandler}.
	 * @param o the map from Yaml
	 * @return an item
	 */
	private Item getItem(int id, Map<String, Object> o) {
		return new Item(id,
				(String) o.get("name"),
				(String) o.get("description"),
				(double) o.get("weight"),
				(boolean) o.get("pickupable"),
				(boolean) o.get("dropable")
		);
	}

	/**
	 * Gets an component based on the Yaml given.
	 * Only invokes by {@link DatabaseHandler}.
	 * @param o the map from Yaml
	 * @return a component
	 */
	private Item getComponent(int id, Map<String, Object> o) {
		return new ItemComponent(id, (String) o.get("name"),
				(String) o.get("description"),
				(double) o.get("weight"),
				(boolean) o.get("pickupable"),
				(boolean) o.get("dropable"),
				ComponentType.valueOf((String) o.get("componentType")),
				Color.valueOf((String) o.get("color")),
				State.valueOf((String) o.get("state"))
		);
	}

	/**
	 * Gets a clue based on the Yaml given.
	 * Only invokes by {@link DatabaseHandler}.
	 * @param o the map from Yaml
	 * @return a no-use clue
	 */
	private Item getClue(int id, Map<String, Object> o) {
		return new ItemClue(id, (String) o.get("name"),
				(String) o.get("description"),
				(double) o.get("weight"),
				(boolean) o.get("pickupable"),
				(boolean) o.get("dropable"),
				ComponentType.valueOf((String) o.get("componentType")),
				Color.valueOf((String) o.get("color")),
				State.valueOf((String) o.get("state"))
		);
	}

	/**
	 * Gets an item of type {@link ItemPortalGun}.
	 * Only invoked by {@link DatabaseHandler}.
	 * @param o the map from Yaml
	 * @return a PortalGun
	 */
	private Item getPortalGun(int id, Map<String, Object> o) {
		return new ItemPortalGun(id, (String) o.get("name"),
				(String) o.get("description"),
				(double) o.get("weight"),
				(boolean) o.get("pickupable"),
				(boolean) o.get("dropable")
		);
	}
}