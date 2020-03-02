package ie.ait.main;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        SceneRoot sceneRoot = new SceneRoot();
        Application.launch(sceneRoot.getClass(), args);
    }
}
