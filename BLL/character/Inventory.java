package BLL.character;

import BLL.item.Item;
import BLL.item.ItemStack;

/**
 * Creating a new inventory, this interface has to be implemented.
 * It describes the general and necessary function to an inventory.
 */
public interface Inventory {
	/**
	 * Gets the max capacity an inventory can have.
	 * @return max capacity
	 */
	double getMaxCapacity();

	/**
	 * Returns the current capacity the inventory has.
	 * @return current capacity
	 */
	double getCurrentCapacity();

	/**
	 * Adds ItemStack to the inventory.
	 * @param itemStack the itemstack to add
	 * @return true, if successful
	 */
	boolean add(ItemStack itemStack);

	/**
	 * Removes an ItemStack based on only the item.
	 * Quantity should be ignored.
	 * @param item the item to remove
	 * @return true, if successful
	 */
	boolean remove(Item item);

	/**
	 * Removes an ItemStack based on only the item.
	 * @param itemStack the ItemStack to remove
	 * @return true, if successful
	 */
	boolean remove(ItemStack itemStack);

	/**
	 * Removes an ItemStack from the inventory at the given index.
	 * @param index the index to remove
	 * @throws ArrayIndexOutOfBoundsException index is not between empty and current
	 */
	void remove(int index) throws ArrayIndexOutOfBoundsException;

	/**
	 * Searches for item within the inventory.
	 * @param item item to check for
	 * @return true, item exists
	 */
	boolean contains(Item item);

	/**
	 * Searches for item within the inventory, but the quantity is a minimum.
	 * @param item item to search for
	 * @param quantity quantity
	 * @return true, if inventory contain item and at least the quantity
	 */
	boolean contains(Item item, int quantity);

	/**
	 * Similar to {@link #contains(Item, int)}, but as ItemStack.
	 * @param itemStack the itemstack to search for
	 * @return true, if inventory contain the item and quantity within ItemStack
	 */
	boolean contains(ItemStack itemStack);

	/**
	 * Gets ItemStack at the given index.
	 * @param index to get from
	 * @return the itemstack at index
	 * @throws ArrayIndexOutOfBoundsException the given index is out of the boundary
	 */
	ItemStack getItemStack(int index) throws ArrayIndexOutOfBoundsException;

	/**
	 * Sets ItemStack at the given index.
	 * @param index to set at
	 * @param item the new value
	 * @return the old value at index
	 * @throws ArrayIndexOutOfBoundsException the given index is out of the boundary
	 */
	ItemStack setItemStack(int index, ItemStack item) throws ArrayIndexOutOfBoundsException;

	/**
	 * Gets all the content inside inventory.
	 * @return all the content inside inventory
	 */
	ItemStack[] getContent();
}