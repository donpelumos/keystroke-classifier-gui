package ie.ait.main;

import ie.ait.bootstrap.Bootstrapper;
import ie.ait.controllers.HomeController;
import ie.ait.controllers.TrainController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneRoot extends Application {
    private FXMLLoader homeLoader;
    private FXMLLoader trainLoader;
    private FXMLLoader testLoader;
    private FXMLLoader settingsLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Bootstrapper.init();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scene-root.fxml"));
        homeLoader = new FXMLLoader(getClass().getClassLoader().getResource("scene-home.fxml"));
        //Parent homeRoot = FXMLLoader.load(getClass().getClassLoader().getResource("scene-home.fxml"));
        Parent homeRoot = homeLoader.load();
        trainLoader = new FXMLLoader(getClass().getClassLoader().getResource("scene-train.fxml"));
        Parent trainRoot = trainLoader.load();
        //Parent trainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("scene-train.fxml"));
        testLoader = new FXMLLoader((getClass().getClassLoader().getResource("scene-test.fxml")));
        Parent testRoot = testLoader.load();
        settingsLoader = new FXMLLoader((getClass().getClassLoader().getResource("scene-settings.fxml")));
        Parent settingsRoot = settingsLoader.load();

        primaryStage.setTitle("Keystroke Classifier");
        primaryStage.setScene(new Scene(root, 1000, 550));

        TabPane tabPane = new TabPane();
        Pane homePane = new Pane(homeRoot);
        Pane trainPane = new Pane(trainRoot);
        Pane testPane = new Pane(testRoot);
        Pane settingsPane = new Pane(settingsRoot);

        Tab homeTab = new Tab("Home", homePane);
        Tab trainTab = new Tab("Train"  , trainPane);
        Tab testTab = new Tab("Test" , testPane);
        Tab settingsTab = new Tab("Settings" , settingsPane);
        //Tab tab4 = new Tab("Settings" , new Label("Show all settings available"));

        homeTab.setId("homeTab");
        trainTab.setId("trainTab");
        testTab.setId("testTab");
        settingsTab.setId("settingsTab");

        tabPane.getTabs().add(homeTab);
        tabPane.getTabs().add(trainTab);
        tabPane.getTabs().add(testTab);
        tabPane.getTabs().add(settingsTab);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);

        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();

        homeTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                boolean isSelected = ((Tab)(event.getTarget())).isSelected();
                if(isSelected) {
                    System.out.println("HOME TAB CLICKED");
                    ((HomeController)homeLoader.getController()).initialize();
                }
            }
        });
        trainTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                boolean isSelected = ((Tab)(event.getTarget())).isSelected();
                if(isSelected) {
                    System.out.println("TRAIN TAB CLICKED");
                    ((TrainController)trainLoader.getController()).initialize();
                }
            }
        });
    }
}
