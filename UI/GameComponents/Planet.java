package UI.GameComponents;

import BLL.ACQ.IPlanet;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.FloatMap;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Planet extends GameObject {


    private static List<Planet> planets = new ArrayList<>();
    private StackPane planetWrapper;
    private Label name;
    private Sphere sphere;
    PhongMaterial texture;

    public Planet(IPlanet planet, Image diffuseMap){

        super(new StackPane());
        planetWrapper = (StackPane) getView();
        this.name = new Label(planet.getName());
        texture = new PhongMaterial();
        sphere = new Sphere(280);


        texture.setDiffuseMap(diffuseMap);
        sphere.setMaterial(texture);


        Group planetGroup = new Group(sphere, name);
        planetWrapper.getChildren().add(planetGroup);

        planetGroup.setTranslateX(-sphere.getRadius());
        planetGroup.setTranslateY(-sphere.getRadius());



        planets.add(this);
    }

    public static List<Planet> getPlanets() {
        return planets;
    }

}
