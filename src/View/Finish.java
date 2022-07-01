package View;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Finish {
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Label label = new Label();

    /**
     * this method prints a message to the user when getting to the goal position
     */
    public void initialize() {
        try {
            // Setting font to the label
            Font font = Font.font("Brush Script MT", FontWeight.BOLD, FontPosture.REGULAR, 25);
            label.setFont(font);

            label.setWrapText(true);
            label.setText("Courage got home safely!\nFOR NOW...");

            // Creating a graphic (image)
            Image img = new Image(new FileInputStream("resources/images/success.png"));
            ImageView view = new ImageView(img);
            view.setFitHeight(220);
            view.setPreserveRatio(true);
            label.setGraphic(view);


            MyViewController.mediaPlayer.pause();
            MyViewController.playMusic("resources/Sound/finishSound.mp3");
        }
        catch(FileNotFoundException f){
            f.printStackTrace();
        }
    }

    public void close() {
        MyViewController.mediaPlayer.pause();
        MyViewController.playMusic("resources/Sound/mazeSound.mp3");
        Platform.exit();
    }

    public void closeWindow() {
        MyViewController.mediaPlayer.pause();
        // IsItTheEnd finale = new IsItTheEnd("Good Job!", "You get to the goal!");
        MyViewController.playMusic("resources/Sound/mazeSound.mp3");
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}
