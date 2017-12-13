package BLL.item;

import BLL.GameUtility;

/**
 * This defines the functionality of Components within the game.
 */
public class ItemComponent extends Item {
	private ComponentType componentType;
	private Color color;
	private State state;

	public ItemComponent(int id, String name, String description, double weight, boolean isPickupable, boolean isDropable, ComponentType componentType, Color color, State state) {
		super(id, name, description, ItemType.COMPONENT, weight, isPickupable, isDropable);
		this.componentType = componentType;
		this.color = color;
		this.state = state;
	}

	/**
	 * Gets the type of the component, declared by the enum {@link ComponentType}.
	 * @return
	 */
	public ComponentType getComponentType() {
		return componentType;
	}

	/**
	 * Gets the color of the component.
	 * {@link Color} is homemade.
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Gets the state of the component, declared by the enum {@link State}.
	 * State is abstract, however, it can be compared to a condition.
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]: %s", getName(), getComponentType(), GameUtility.replacePlaceHolders(getDescription(),"{{state}}", getState().name().toLowerCase(), "{{color}}", getColor().name().toLowerCase()));
	}
}