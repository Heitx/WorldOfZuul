package UI.GameComponents;

import BLL.ACQ.IPlanet;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;


public class Innerscene {

    private SubScene subScene;
    private Camera camera;
    private Player player;
    private Pane rootPane;
    private Map map;


    public Innerscene(SubScene subScene, Stage stage){
        this.subScene = subScene;
        camera = new ParallelCamera();
        map = new Map();
        buildSubscene(stage);
    }


    private void buildSubscene(Stage stage){

        subScene.setRoot(map.getRoot());

        subScene.setCamera(camera);

        ImageView img = new ImageView("./UI/resources/img/spaceShip-01-01.png");
        img.setFitWidth(50);
        img.setFitHeight(88);
        img.setRotate(90);
        img.setCache(true);
        img.setTranslateY(0);
        img.setTranslateX(20);

        StackPane playerRoot = new StackPane(img);
        playerRoot.setEffect(new DropShadow(20,0,0,Color.rgb(0,0,0,0.3)));
        playerRoot.setCenterShape(true);

        player = new UI.GameComponents.Player(50,50, playerRoot);
        player.setVelocity(new Point2D(0,0));
        GameObject.addGameObject(player, Map.mapWidth /2, Map.mapHeight /2, map.getRootPane());

    }


    public void centerView(GameObject o) {

        camera.setTranslateX((o.getView().getTranslateX()) - (subScene.getWidth()/2) + player.getWidth()/2);
        camera.setTranslateY((o.getView().getTranslateY()) - (subScene.getHeight()/2) + player.getHeight()/2);


        /* COMMENT OUT IF CAMERA SHOULD STOP WHEN PLAYER REACHES THE END OF MAP */
//        if (camera.getTranslateX() < rootPane.getTranslateX()){
//            camera.setTranslateX(rootPane.getTranslateX());
//        }
//
//        if (camera.getTranslateX() > rootPane.getWidth() - subScene.getWidth()){
//            camera.setTranslateX(rootPane.getWidth() - subScene.getWidth());
//        }
//
//        if (camera.getTranslateY() < rootPane.getTranslateY()){
//            camera.setTranslateY(rootPane.getTranslateY());
//        }
//
//        if (camera.getTranslateY() > rootPane.getHeight() - subScene.getHeight()){
//            camera.setTranslateY(rootPane.getHeight() - subScene.getHeight());
//        }
    }

    public void keepPlayerInMap(){
        if(player.getView().getTranslateX() < map.getRootPane().getTranslateX()){
            player.getView().setTranslateX(map.getRootPane().getTranslateX());
        }

        if (player.getView().getTranslateX() > map.getRootPane().getWidth() - player.getWidth()){
            player.getView().setTranslateX(map.getRootPane().getWidth() - player.getWidth());
        }

        if (player.getView().getTranslateY() < map.getRootPane().getTranslateY()){
            player.getView().setTranslateY(map.getRootPane().getTranslateY());
        }

        if (player.getView().getTranslateY() > map.getRootPane().getHeight() - player.getHeight()){
            player.getView().setTranslateY(map.getRootPane().getHeight() - player.getHeight());
        }
    }

    public void createPlanets(java.util.Map<String, ? extends IPlanet> planets){
        map.createPlanets(planets);
    }


    public Player getPlayer() {
        return player;
    }

    public SubScene getSubScene() {
        return subScene;
    }

    public Map getMap() {
        return map;
    }
}
