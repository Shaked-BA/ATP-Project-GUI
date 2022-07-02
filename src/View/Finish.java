package View;

import javafx.application.Platform;
import javafx.scene.control.Label;
<<<<<<< HEAD
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileInputStream;

=======
import javafx.stage.Stage;

>>>>>>> mvvm-packages
public class Finish {
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Label label = new Label();

<<<<<<< HEAD

    /**
     * Shows new stage to the user when getting to the goal position.
     */
    public void initialize() {
        label.setFont(Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 25));
        label.setWrapText(true);
        label.setText("Courage got home safely!\nFOR NOW...");
        try {
            ImageView view = new ImageView(new Image(new FileInputStream("resources/images/success.png")));
            view.setFitHeight(220);
            view.setPreserveRatio(true);
            label.setGraphic(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
=======
    public void initialize() {
        label.setWrapText(true);
        label.setText("Courage got home safely!\nFOR NOW...");
>>>>>>> mvvm-packages
        MyViewController.mediaPlayer.pause();
        MyViewController.playMusic("resources/Sound/finishSound.mp3");
    }

<<<<<<< HEAD
    /**
     * A closing method for this Scene.
     */
=======
>>>>>>> mvvm-packages
    public void close() {
        MyViewController.mediaPlayer.pause();
        MyViewController.playMusic("resources/Sound/mazeSound.mp3");
        Platform.exit();
    }

<<<<<<< HEAD
    /**
     * Closes the window.
     */
=======
>>>>>>> mvvm-packages
    public void closeWindow() {
        MyViewController.mediaPlayer.pause();
        MyViewController.playMusic("resources/Sound/mazeSound.mp3");
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}