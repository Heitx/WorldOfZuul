package UI.GameComponents;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class GameObject {

    private Node view;
    private Point2D velocity;

    double speed;
    double maxSpeed;

    public GameObject(Node view){
        this.view = view;
    }

    public void update(double dt){
        System.out.println("Override this");
    }

    public void rotateRight(){
        view.setRotate(view.getRotate() + 5);
    }

    public void rotateLeft(){
        view.setRotate(view.getRotate() - 5);
    }

    public void accelerate(){
        speed++;
    }

    public void decelerate(){
        speed = 0;
    }

    public static void addGameObject(GameObject o, double x, double y, Pane root){
        o.getView().setTranslateY(y);
        o.getView().setTranslateX(x);
        root.getChildren().add(o.getView());
    }

    public boolean isColliding(GameObject other){
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }


    /* GETTER */
    public Node getView() {
        return view;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public double getRotate(){
        return view.getRotate();
    }

    public Point2D getVelocity() {
        return velocity;
    }
}
