package BLL.ACQ;

/**
 * Limits the functionality of {@link BLL.item.Item}.
 * Casted from Item and sent to the GUI.
 */
public interface IItem {

	/**
	 * Gets the Id of the item.
	 * @return id
	 */
	int getId();

	/**
	 * Gets the name of the item.
	 * @return name of item
	 */
	String getName();

	/**
	 * Gets the description of the item.
	 * @return description of item
	 */
	String getDescription();

	/**
	 * Returns whether the item is pickupable or not.
	 * @return true, if pickupable
	 */
	boolean isPickupable();

	/**
	 * Returns whether the item is dropable or not.
	 * @return true, if dropable
	 */
	boolean isDropable();

	/**
	 * Gets the weight of the item (quantity of 1)
	 * @return the weight
	 */
	double getWeight();
}
