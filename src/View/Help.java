package View;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Observable;

public class Help extends Observable {
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Label label = new Label();

    /**
     * this method prints guides to the user
     */
    public void initialize() {
        label.setWrapText(true);
        label.setText("if you want save maze, create new maze or load maze->> press file.\n"+
                "you can also see the properties and change them in options.\n"+
                "Up = 8       Slant Up & Left = 7 \n"+
                "Down = 2     Slant Up & Right = 9 \n"+
                "Left = 4     Slant Down & Left = 1\n"+
                "Right = 6    Slant Down & Right = 3");
    }
    public void close() {
        Platform.exit();
    }

    /**
     * this method is for closing the window
     */
    public void closeWindow() {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}