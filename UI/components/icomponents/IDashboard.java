package UI.components.icomponents;

/**
 * This interface defines the contract between components of type {@link IDashboard} and a parent controller.
 */
public interface IDashboard extends IComponent {

    /**
     * Method to add an event listener.
     * Event to be fired when backpack visibility is toggled to visible.
     * @param listener  listener to be added
     */
    void onBackpack(IEventListener<?> listener);

    /**
     * Method to add an event listener.
     * Event to be fired when help is opened.
     * @param listener  listener to be added
     */
    void onHelp(IEventListener<?> listener);


    // Methods
    /**
     * Accessor method for the avatar component.
     * @return  the avatar component.
     */
    IAvatar getAvatar();

    /**
     * Method to set the value of the fuel progress bar.
     * @param value the value to be set.
     * @param textualValue the textual value to be set.
     */
    void setFuelValue(double value, String textualValue);

    /**
     * Method to set the value of the backpack progress bar.
     * @param value the value to be set.
     * @param textualValue the textual value to be set.
     */
    void setBackpackValue(double value, String textualValue);

}
