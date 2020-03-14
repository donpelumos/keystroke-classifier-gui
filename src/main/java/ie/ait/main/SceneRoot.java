package ie.ait.main;

import ie.ait.bootstrap.Bootstrapper;
import javafx.application.Application;
import javafx.collections.ObservableList;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Bootstrapper.init();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("scene-root.fxml"));
        Parent homeRoot = FXMLLoader.load(getClass().getClassLoader().getResource("scene-home.fxml"));
        Parent trainRoot = FXMLLoader.load(getClass().getClassLoader().getResource("scene-train.fxml"));

        primaryStage.setTitle("Keystroke Classifier");
        primaryStage.setScene(new Scene(root, 1000, 600));

        TabPane tabPane = new TabPane();
        Pane homePane = new Pane(homeRoot);
        Pane trainPane = new Pane(trainRoot);


        Tab tab1 = new Tab("Home", homePane);
        Tab tab2 = new Tab("Train"  , trainPane);
        Tab tab3 = new Tab("Test" , new Label("Show all test available"));
        Tab tab4 = new Tab("Settings" , new Label("Show all settings available"));

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);

        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();
    }
}
