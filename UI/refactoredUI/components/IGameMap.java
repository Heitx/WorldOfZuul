package UI.refactoredUI.components;

import BLL.ACQ.IPlanet;
import javafx.scene.SubScene;

import java.util.AbstractMap;
import java.util.Map;

public interface IGameMap extends IComponent {

    double mapWidth = 8000;
    double mapHeight = 8000;

    // Methods

    /**
     * Method to render ui representations of planets.
     * @param planets   map of planets.
     */
    void renderPlanets(Map<String, ? extends IPlanet> planets);


    /**
     * Method to add an event listener.
     * Event to be fired when player / space ship gets updated coordinates.
     * Key is x, value is y.
     * @param listener  listener to be added.
     */
    void onMovement(IEventListener<AbstractMap.SimpleImmutableEntry<Double, Double>> listener);


    /**
     * Method to center viewport around player.
     * @param coordX    x coordinate of player.
     * @param coordY    y coordinate of player.
     */
    void centerCamera(double coordX, double coordY);

    void rotateSpaceshipLeft(boolean state);

    void rotateSpaceshipRight(boolean state);

    void accelerateSpaceship(boolean state);

    void decelerateSpaceship(boolean state);

    /**
     * Returns the x coordinate of the space ship component.
     * @return  x coordinate.
     */
    void setSpaceshipCoordX(double coordX);

    /**
     * Returns the y coordinate of the space ship component.
     * @return  y coordinate.
     */
    void setSpaceshipCoordY(double coordY);


}
