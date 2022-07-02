package View;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Observable;

public class Help extends Observable {
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Label label = new Label();

<<<<<<< HEAD
    /**
     * Shows a guide to the user.
     */
    public void initialize() {
        label.setWrapText(true);
        label.setText("""
                    To create a new maze, insert rows and columns sizes in the text boxes, and click file->New Maze.
                    To save the maze, click file->Save Maze, and choose where to save it in your computer.
                    To load maze click file->Load Maze, and choose where to save it from your computer.
                    To change the game properties click Options->Properties.
                    To exit the game click Exit.
                    
                    How to play:
                        With keyboard's numpad:
                            Up = 8
                            Down = 2
                            Right = 6
                            Left = 4
                            Diagonal Up-Left = 7
                            Diagonal Up-Right = 9
                            Diagonal Down-Left = 1
                            Diagonal Down-Right = 3
                        With keyboard:
                            Up = up arrow
                            Down = down arrow
                            Right = right arrow
                            Left = left arrow
                            Diagonal Up-Left = q
                            Diagonal Up-Right = w
                            Diagonal Down-Left = a
                            Diagonal Down-Right = s
                    """);
    }

    /**
     * A closing method for this Scene.
     */
=======
    public void initialize() {
        label.setWrapText(true);
        label.setText("if you want save maze, create new maze or load maze->> press file.\n"+
                "you can also see the properties and change them in options.\n"+
                "Up = 8       Slant Up & Left = 7 \n"+
                "Down = 2     Slant Up & Right = 9 \n"+
                "Left = 4     Slant Down & Left = 1\n"+
                "Right = 6    Slant Down & Right = 3");
    }
>>>>>>> mvvm-packages
    public void close() {
        Platform.exit();
    }

<<<<<<< HEAD
    /**
     * Closes the window.
     */
=======
>>>>>>> mvvm-packages
    public void closeWindow() {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}