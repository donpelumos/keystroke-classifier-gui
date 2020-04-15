package ie.ait.main;

import ie.ait.bootstrap.Bootstrapper;
import ie.ait.controllers.HomeController;
import ie.ait.controllers.TrainController;
import ie.ait.models.enums.AlertType;
import ie.ait.utils.Utils;
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
        checkForJavaAndPython();
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
        //tabPane.getTabs().add(settingsTab);

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

    private void checkForJavaAndPython(){
        String javaVersion = Utils.getJavaVersion().trim();
        String pythonVersion = Utils.getPythonVersion().trim();
        double pythonVersionNumber = Double.parseDouble(pythonVersion.split("\\.")[0]+"."+pythonVersion.split("\\.")[1]);
        if(javaVersion.equals("") || pythonVersion.equals("")){
            String errorDescription = "Language Dependency Not Found";
            String errorBody = "Java or Python is not installed. Kindly check that both java and python are installed.\\n" +
                    "In the event that both are installed, ensure that both have been set to root variables and can be accessed " +
                    "via the command line from any directory.";
            Utils.showAlert("Error",errorDescription, errorBody, AlertType.ERROR);
            Utils.logError(getClass(), errorDescription+ " => "+errorBody);
            System.exit(0);
        }
        else if(pythonVersionNumber < 3.4){
            String errorDescription = "Old Python Version";
            String errorBody = "This application requires Python 3.4 or later. Kindly check to ensure that a version of Python " +
                    "equivalent to or later than 3.4 is installes on the system";
            Utils.showAlert("Error",errorDescription, errorBody, AlertType.ERROR);
            Utils.logError(getClass(), errorDescription+ " => "+errorBody);
            System.exit(0);
        }
    }
}
