package UI.GameComponents;

import BLL.ACQ.IItemStack;
import BLL.entity.npc.NPC;
import UI.SearchTask;
import UI.controller.GameController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

import java.util.ArrayList;

public class PlanetView {

    GameController controller;
    private AnchorPane parent;
    private AnchorPane planetViewWrapper;
    private StackPane planetViewInnerWrapper;
    private ScrollPane scrollPane;
    private AnchorPane image;
    private Pane imageGradient;
    private VBox vbox;
    StackPane headerWrapper;
    private Button pickupButton;
    private ListView<IItemStack> itemList;
    private ListView<NPC> NPCList;
    private ObservableList<IItemStack> items;
    private ObservableList<NPC> npcs;
    HBox listsWrapper;
    private Task task;
    private boolean isVisible;


    public PlanetView(AnchorPane parent, GameController controller) {
        this.parent = parent;
        this.controller = controller;

    }

    public void landOnPlanet(String name, String description, String imagePath) {
        task = null;
        isVisible = true;

        planetViewWrapper = new AnchorPane();
        planetViewInnerWrapper = new StackPane();
        headerWrapper = new StackPane();
        scrollPane = new ScrollPane();
        image = new AnchorPane();
        imageGradient = new Pane();
        vbox = new VBox();
        scrollPane = new ScrollPane();
        pickupButton = new Button();
        itemList = new ListView<>();
        NPCList = new ListView<>();
        listsWrapper = new HBox();

        scrollPane.setStyle("-fx-background-color: transparent;");
        planetViewInnerWrapper.setStyle("-fx-background-color: transparent;");
        vbox.setStyle("-fx-background-color: transparent;");


        // ADD CONTENT TO THE VBOX ------------------------------
        Label topLabel = new Label("YOU JUST LANDED ON");
        topLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14; -fx-font-family: 'Circular Std Book';");

        Label planetName = new Label(name);
        planetName.setStyle("-fx-text-fill: white; -fx-font-size: 60; -fx-font-family: 'Circular Std Black';");

        //-----
        Button button__leavePlanet = new Button();
        button__leavePlanet.setText("LEAVE PLANET");
        button__leavePlanet.getStyleClass().add("button__leavePlanet");
        button__leavePlanet.setOnAction(event -> {
            leavePlanet();
        });

        VBox header = new VBox();
        header.setStyle("-fx-padding: 10px 0 70px 0;");
        header.setMinHeight(400);
        header.setPrefHeight(400);
        header.setMaxHeight(400);
        header.setAlignment(Pos.BOTTOM_LEFT);
        header.getChildren().addAll(new VBox(topLabel, planetName), button__leavePlanet);
        header.setSpacing(20);
        //-----

        //-----
        Label descriptionTitle = new Label("About this planet");
        descriptionTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Circular Std Medium';");
        Label descriptionText = new Label(description);
        descriptionText.setStyle("-fx-text-fill: rgba(255,255,255,0.8); -fx-font-size: 18; -fx-font-family: 'Circular Std Book'; -fx-padding: 10px 0 25px 0;");
        descriptionText.setWrapText(true);

        VBox planetDescription = new VBox();
        planetDescription.getChildren().addAll(descriptionTitle, descriptionText, divider());
        //-----

        //-----
        Label listsTitle = new Label("Stuff on this planet");
        listsTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-family: 'Circular Std Medium';");
        Region listHeaderSpacer = new Region();
        Button button__searchPlanet = new Button();
        button__searchPlanet.setText("SEARCH PLANET");
        button__searchPlanet.getStyleClass().add("button__searchPlanet");
        ProgressBar barSearch = new ProgressBar();
        barSearch.getStyleClass().add("searchProgress");
        barSearch.setVisible(false);
        button__searchPlanet.setContentDisplay(ContentDisplay.TEXT_ONLY);
        button__searchPlanet.setGraphic(barSearch);
        HBox listsHeaderBar = new HBox(listsTitle, listHeaderSpacer, button__searchPlanet);
        HBox.setHgrow(listHeaderSpacer, Priority.ALWAYS);
        listsHeaderBar.setAlignment(Pos.CENTER);
        VBox listsHeader = new VBox(listsHeaderBar, divider());
        listsHeader.setSpacing(10);

        Label notSearched = new Label();
        notSearched.setText("You have not searched this planet yet!");
        notSearched.setStyle("-fx-text-fill: rgba(255,255,255,1); -fx-font-size: 16; -fx-font-family: 'Circular Std Book'; -fx-padding: 5px 15px; -fx-background-color: rgba(249,90,62, 0.4);");
        listsWrapper.getChildren().add(notSearched);

        VBox planetLists = new VBox();
        planetLists.getChildren().addAll(listsHeader, listsWrapper);
        planetLists.setSpacing(10);

        //-----

        vbox.getChildren().addAll(header, planetDescription, planetLists);
        vbox.setStyle("-fx-padding: 0 60px 18px 50px;");
        vbox.setSpacing(30);

        // ------------------------------------------------------


        // ADD MOST OUTER VBOX TO STACKPANE (planetViewInnerWrapper)
        planetViewInnerWrapper.getChildren().addAll(vbox);
        vbox.setMaxWidth(1440);
        //vbox.setMinHeight(5000);
        planetViewInnerWrapper.setAlignment(Pos.TOP_CENTER);

        // ADD STACKPANE (planetViewInnerWrapper) TO SCROLLPANE
        scrollPane.setContent(planetViewInnerWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.getStyleClass().add("view__scroll-pane");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);


        // ADD GRADIENTOVERLAY OVER IMAGE
        image.getChildren().add(imageGradient);
        image.getStyleClass().add("planet__view");
        String backgroundImage = "-fx-background-image: url(" + imagePath + ");";
        image.setStyle(backgroundImage);
        image.setMinHeight(400);
        Stop[] imageGradientStops = {new Stop(0, Color.rgb(18, 18, 18, 1)), new Stop(1, Color.rgb(18, 18, 18, 0.1))};
        LinearGradient lg = new LinearGradient(0, 0, Math.cos(Math.toRadians(90)), Math.sin(Math.toRadians(90)), true, CycleMethod.NO_CYCLE, imageGradientStops);
        imageGradient.setBackground(new Background(new BackgroundFill(lg, null, null)));
        imageGradient.setRotate(180);
        AnchorPane.setLeftAnchor(imageGradient, 0.0);
        AnchorPane.setRightAnchor(imageGradient, 0.0);
        AnchorPane.setTopAnchor(imageGradient, 0.0);
        AnchorPane.setBottomAnchor(imageGradient, 0.0);


        // ADD IMAGE AND SCROLLPANE TO MOST OUTER WRAPPER
        planetViewWrapper.getChildren().addAll(image, scrollPane);
        planetViewWrapper.setStyle("-fx-background-color: #181818;");
        AnchorPane.setLeftAnchor(image, 0.0);
        AnchorPane.setRightAnchor(image, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setBottomAnchor(scrollPane, 100.0);


        parent.getChildren().add(planetViewWrapper);
        AnchorPane.setLeftAnchor(planetViewWrapper, 0.0);
        AnchorPane.setRightAnchor(planetViewWrapper, 0.0);
        AnchorPane.setTopAnchor(planetViewWrapper, 0.0);
        AnchorPane.setBottomAnchor(planetViewWrapper, 0.0);


        // --------------------------------------------------------------
        // CONTROL IMAGE OPACITY VIA SCROLL POSITION
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            image.setOpacity(1 - (newValue.doubleValue() * 10));
        });

        // EVENT HANDLER FOR LEAVING PLANET
        button__leavePlanet.setOnAction(event -> controller.leavePlanet());

        // EVENT HANDLER FOR THE NPC LIST
        NPCList.setOnMouseClicked(event -> {
            if (NPCList.getSelectionModel().getSelectedItem() != null) {
                controller.startInteract(NPCList.getSelectionModel().getSelectedItem(), 0);
            }
        });

        // EVENT HANDLER FOR THE NPC LIST
        itemList.setOnMouseClicked(event -> {
            if (itemList.getSelectionModel().getSelectedItem() != null) {
                pickupButton.setDisable(false);
            } else{
                pickupButton.setDisable(true);
            }
        });

        pickupButton.setOnAction(event ->  {
            IItemStack selectedItem = itemList.getSelectionModel().getSelectedItem();
            if(selectedItem != null){
                controller.getDomain().pickupItem(selectedItem);
                tickLists();
            }
        });

        // EVENT HANDLER FOR SEARCHING PLANET
        button__searchPlanet.setOnAction(event -> {

            if (task == null || !task.isRunning()) {
                task = new SearchTask(false);

                barSearch.setPrefWidth(button__searchPlanet.getWidth() * 5.7 / 10);
                barSearch.setVisible(true);
                button__searchPlanet.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                button__searchPlanet.setText("");


                controller.getDomain().searchPlanet();



                tickLists();


                // player.getCurrentPlanet().getPermSearched()
                Thread th = new Thread(task);

                barSearch.progressProperty().bind(task.progressProperty());
                task.setOnSucceeded(event1 -> {
                    button__searchPlanet.setContentDisplay(ContentDisplay.TEXT_ONLY);
                    button__searchPlanet.setText("SEARCH PLANET");
                    barSearch.setVisible(false);
                    barSearch.setPrefWidth(0);
                    planetLists.setMinHeight(500);

                    Timeline displayLists = new Timeline();
                    ArrayList<KeyFrame> keyFrames = new ArrayList<>();
                    keyFrames.add(new KeyFrame(Duration.millis(0), new KeyValue(itemList.opacityProperty(), 0, Interpolator.EASE_BOTH)));
                    keyFrames.add(new KeyFrame(Duration.millis(0), new KeyValue(itemList.translateYProperty(), 40, Interpolator.EASE_BOTH)));
                    keyFrames.add(new KeyFrame(Duration.millis(455), new KeyValue(itemList.opacityProperty(), 1, Interpolator.EASE_BOTH)));
                    keyFrames.add(new KeyFrame(Duration.millis(455), new KeyValue(itemList.translateYProperty(), 0, Interpolator.EASE_BOTH)));
                    keyFrames.add(new KeyFrame(Duration.millis(100), new KeyValue(NPCList.opacityProperty(), 0, Interpolator.EASE_BOTH)));
                    keyFrames.add(new KeyFrame(Duration.millis(100), new KeyValue(NPCList.translateYProperty(), 40, Interpolator.EASE_BOTH)));
                    keyFrames.add(new KeyFrame(Duration.millis(555), new KeyValue(NPCList.opacityProperty(), 1, Interpolator.EASE_BOTH)));
                    keyFrames.add(new KeyFrame(Duration.millis(555), new KeyValue(NPCList.translateYProperty(), 0, Interpolator.EASE_BOTH)));
                    displayLists.getKeyFrames().addAll(keyFrames);

                    listsWrapper.getChildren().clear();
                    Label itemListHeader = new Label("Items");
                    Label NPCListHeader = new Label("Characters");
                    itemListHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-family: 'Circular Std Medium';");
                    NPCListHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16; -fx-font-family: 'Circular Std Medium';");

                    pickupButton.setText("Pickup");
                    pickupButton.getStyleClass().add("planetViewButton--pickup");
                    pickupButton.setDisable(true);

                    VBox itemVbox = new VBox(itemListHeader, itemList, pickupButton);
                    VBox NPCVbox = new VBox(NPCListHeader, NPCList);
                    listsWrapper.getChildren().addAll(itemVbox, NPCVbox);
                    HBox.setHgrow(itemVbox, Priority.ALWAYS);
                    HBox.setHgrow(NPCVbox, Priority.ALWAYS);
                    itemList.setOpacity(0);
                    NPCList.setOpacity(0);
                    itemList.getStyleClass().add("searchList");
                    NPCList.getStyleClass().add("searchList");
                    displayLists.play();

                });

                th.start();
            }

        });

    }

    public void leavePlanet() {
        isVisible = false;

        parent.getChildren().remove(planetViewWrapper);

    }


    /**
     * Returns a new divider for layout purposes
     *
     * @return AnchorPane
     */
    private AnchorPane divider() {
        AnchorPane divider = new AnchorPane();
        divider.setStyle("-fx-border-color: #282828; -fx-border-width: 1 0 0 0;");
        return divider;
    }

    public boolean isVisible() {
        return isVisible;
    }


    public void tickLists(){
        items = FXCollections.observableArrayList(controller.getDomain().getPlayer().getCurrentPlanet().getInventory().getIContent());
        itemList.setItems(items);

        npcs = FXCollections.observableArrayList(controller.getDomain().getPlayer().getCurrentPlanet().getNPCs());
        NPCList.setItems(npcs);
        NPCList.setCellFactory(param -> {
            return new NPCFormatCell();
        });
    }


    /**
     * Inner class that extends {@link ListCell}, overriding the updateItem method.
     * The updateItem method is called whenever the item in the cell changes.
     */
    public class NPCFormatCell extends ListCell<NPC> {
        private ImageView imageView = new ImageView();

        public NPCFormatCell() {
        }

        @Override
        protected void updateItem(NPC item, boolean empty) {
            super.updateItem(item, empty);
            setText(item == null ? " " : item.getName());
            setTextFill(new Color(1., 1., 1., 1.));

            if (item != null) {
                imageView.setImage(new Image("./DAL/resource/images/npcs/profputri.png"));
                imageView.setFitHeight(40);
                imageView.setPreserveRatio(true);
                setGraphic(imageView);
            }

        }
    }

}