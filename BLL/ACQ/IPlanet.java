package BLL.ACQ;

import BLL.entity.npc.NPC;

import java.io.File;
import java.util.List;

/**
 * Limits the functionality of {@link BLL.world.Planet}.
 * Casted from Planet and sent to the GUI.
 */
public interface IPlanet {

	/**
	 * Gets the name of the planet.
	 * @return name
	 */
	String getName();

	/**
	 * Gets the description of the planet.
	 * @return description
	 */
	String getDescription();

	/**
	 * Gets the arrive message.
	 * @return arrive message
	 */
	String getArriveMessage();

	/**
	 * Returns a boolean based on both the planet has been searched.
	 * @return if planet has been searched
	 */
	boolean hasSearched();

	/**
	 * Returns a list of all the NPCs currently who are on the planet.
	 * @return a list of NPC
	 */
	List<NPC> getNPCs();

	/**
	 * Gets the inventory of the planet.
	 * @return the inventory
	 */
	IInventory getInventory();

	/**
	 * Gets the X-coordinate of the planet.
	 * It comes from a {@link javafx.geometry.Point2D} object within the planet.
	 * @return the x-coordinate of the planet
	 */
	double getX();

	/**
	 * Gets the Y-coordinate of the planet.
	 * It comes from a {@link javafx.geometry.Point2D} object within the planet.
	 * @return the y-coordinate of the planet
	 */
	double getY();

	/**
	 * Gets a object of type File containing the planet's image
	 * @return	the file of the planet's image.
	 */
	File getImage();

	/**
	 * Gets a object of type File containing the planet's 2D map
	 * @return	the file of the planet's 2D map
	 */
	File getMap2D();

}
