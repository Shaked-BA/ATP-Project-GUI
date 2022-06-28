package View;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Finish {
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Label label = new Label();

    public void initialize() {
        label.setWrapText(true);
        label.setText("Courage got home safely!\nFOR NOW...");
        MyViewController.mediaPlayer.pause();
        MyViewController.playMusic("resources/Sound/finishSound.mp3");
    }

    public void close() {
        MyViewController.mediaPlayer.pause();
        MyViewController.playMusic("resources/Sound/mazeSound.mp3");
        Platform.exit();
    }

    public void closeWindow() {
        MyViewController.mediaPlayer.pause();
        MyViewController.playMusic("resources/Sound/mazeSound.mp3");
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}