package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
<<<<<<< HEAD

    /**
     * this method build all the game and connect View, Model and ViewModel all together
     * after the launch method, this method will execute
     * @param stage Stage
     * @throws IOException
     */
=======
>>>>>>> mvvm-packages
    @Override
    public void start(Stage stage) throws IOException {
        MyModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("MyView.fxml").openStream());

        Scene scene = new Scene(root,800,700);
        stage.setMinHeight(800);
        stage.setMinWidth(700);
        stage.setTitle("Courage the Cowardly Dog Maze");
        stage.getIcons().add(new Image("file:resources/images/icon.jpeg"));
        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());

        MyViewController view = fxmlLoader.getController();
        view.setResizeEvent(scene);
        view.initialize(viewModel);
        stage.setScene(scene);

        setStageCloseEvent(stage);
        stage.show();
    }

<<<<<<< HEAD
    /**
     * Closes the stage.
     * @param stage
     */
=======
>>>>>>> mvvm-packages
    private void setStageCloseEvent(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure?\nUnsaved progress will be lost.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    stage.close();
                    Platform.exit();
                } else {
                    windowEvent.consume();
                }
            }
        });
    }

<<<<<<< HEAD
    /**
     * Launches main.
     * @param args
     */
=======
>>>>>>> mvvm-packages
    public static void main(String[] args) {
        launch();
    }
}