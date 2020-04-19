package ie.ait.controllers;

import ie.ait.models.enums.AlertType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.util.Optional;


/**
 * Created by Pelumi.Oyefeso on 19-Mar-2020
 */
public class AlertController {
    private int ALERT_WIDTH = 750;
    private int ALERT_HEIGHT = 300;
    public void display(String title, String headerText, String headerDescription, String message, AlertType... alertType){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(ALERT_WIDTH);
        window.setMinHeight(ALERT_HEIGHT);

        Label headerTextLabel = new Label();
        Font headerFont = new Font(20);
        headerTextLabel.setFont(headerFont);
        headerTextLabel.setText(headerText);

        Label headerDescriptionLabel = new Label();
        headerDescriptionLabel.setFont(headerFont);
        headerDescriptionLabel.setText(headerDescription);

        Button closeButton = new Button("Click to close");
        closeButton.setVisible(false);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                window.close();
            }
        });

        HBox headerLayout = new HBox(10);
        headerLayout.getChildren().addAll(headerTextLabel, headerDescriptionLabel);
        headerLayout.setAlignment(Pos.CENTER_LEFT);

        TextArea alertBodyTextArea = new TextArea();
        alertBodyTextArea.setMinWidth(ALERT_WIDTH);
        alertBodyTextArea.setMinHeight(ALERT_HEIGHT - 350);
        alertBodyTextArea.setMaxHeight(ALERT_HEIGHT - 100);
        alertBodyTextArea.setWrapText(true);
        alertBodyTextArea.setFont(Font.font(16));
        alertBodyTextArea.setEditable(false);
        alertBodyTextArea.setText(message);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0,20,0,20));
        if(alertType.length > 0) {
            if(alertType[0] == AlertType.ERROR || alertType[0] == AlertType.WARNING) {
                layout.getChildren().addAll(headerLayout, alertBodyTextArea);
            }
            else{
                layout.getChildren().addAll(headerLayout);
                ALERT_HEIGHT = 100;
                window.setMinHeight(ALERT_HEIGHT);
            }
        }
        else{
            layout.getChildren().addAll(headerLayout);
            ALERT_HEIGHT = 100;
            window.setMinHeight(ALERT_HEIGHT);
        }
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.resizableProperty().setValue(Boolean.FALSE);
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        window.setX((screenWidth-ALERT_WIDTH)/2);
        window.setY((screenHeight - ALERT_HEIGHT)/2);

        window.showAndWait();
    }
}
