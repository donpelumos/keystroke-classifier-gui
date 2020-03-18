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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Bootstrapper.init();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scene-root.fxml"));
        homeLoader = new FXMLLoader(getClass().getClassLoader().getResource("scene-home.fxml"));
        //Parent homeRoot = FXMLLoader.load(getClass().getClassLoader().getResource("scene-home.fxml"));
        Parent homeRoot = homeLoader.load();
        FXMLLoader trainLoader = new FXMLLoader(getClass().getClassLoader().getResource("scene-train.fxml"));
        Parent trainRoot = trainLoader.load();
        //Parent trainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("scene-train.fxml"));

        primaryStage.setTitle("Keystroke Classifier");
        primaryStage.setScene(new Scene(root, 1000, 600));

        TabPane tabPane = new TabPane();
        Pane homePane = new Pane(homeRoot);
        Pane trainPane = new Pane(trainRoot);


        Tab homeTab = new Tab("Home", homePane);
        Tab trainTab = new Tab("Train"  , trainPane);
        Tab tab3 = new Tab("Test" , new Label("Show all test available"));
        Tab tab4 = new Tab("Settings" , new Label("Show all settings available"));

        homeTab.setId("homeTab");
        trainTab.setId("trainTab");

        tabPane.getTabs().add(homeTab);
        tabPane.getTabs().add(trainTab);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);

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
