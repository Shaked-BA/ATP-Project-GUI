package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class About extends Observable implements Initializable {
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setWrapText(true);
        label.setText("""
                Welcome to our game!
                We are Shaked and Elad, the game's creators.
                This is a classic maze game, but with a different goal:
                get Courage the cowardly dog back home, and quick! There are a lot of monsters out there...
                About the maze:
                We're generating mazes using Wilson's algorithm, and solving them using BFS, DFS and best-First-Search algorithms.
                You can load an existing maze, save a maze and generate a new one with as many rows and columns as you wish.
                You can try to get Courage home by yourself, but if you're scared you can use a hint or get the full solution of the maze.
                Best of luck!
                You'll need it...""");
    }

    /**
     * A close method for this Scene
     */
    public void close() {
        Platform.exit();
    }
    /**
     * this method is for closing the window "about"
     */
    public void closeWindow() {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}