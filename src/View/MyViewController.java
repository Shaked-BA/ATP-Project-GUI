package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements Observer, IView {

    public Pane MazePane;
    private MyViewModel viewModel;
    @FXML
    private MazeDisplayer mazeDisplayer = new MazeDisplayer();
    private int mazesCounter = 0;
    public javafx.scene.control.TextField txt_rows;
    public javafx.scene.control.TextField txt_columns;
    public javafx.scene.control.Label lbl_row;
    public javafx.scene.control.Label lbl_column;
    public javafx.scene.control.Button btn_generate;
    public javafx.scene.control.Button btn_solve;
    public javafx.scene.control.Button btn_hint;
    public javafx.scene.control.Button btn_save;
    public static MediaPlayer mediaPlayer;
    private Finish finish;
    private About about;
    private Help help;
    private Option option;
    private int height;
    private int width;

    public StringProperty sp_playerRow = new SimpleStringProperty();
    public StringProperty sp_playerColumn = new SimpleStringProperty();
    /**
     * initializer of the View controller
     * @param viewModel MyViewModel
     */
    @Override
    public void initialize(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        bindProperties(viewModel);
        playMusic("resources/Sound/mazeSound.mp3");
        btn_solve.setVisible(false);
        btn_hint.setVisible(false);
        btn_save.setVisible(false);
        btn_solve.setDisable(true);
        btn_hint.setDisable(true);
        btn_save.setDisable(true);
    }

    /**
     * this method execute when the observable Object is notifying
     * all of his observers that he make a change or update
     * in this method we handle the update
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            displayMaze(viewModel.getMaze());
            if (viewModel.IsFinished()) {
                btn_solve.setVisible(false);
                btn_hint.setVisible(false);
                btn_solve.setDisable(true);
                btn_hint.setDisable(true);
                finish();
            }
        }
    }

    /**
     * method to display the maze for the user
     * @param maze int[][]
     */
    @Override
    public void displayMaze(int[][] maze) {
        mazeDisplayer.setGoal(viewModel.getGoal());
        mazeDisplayer.setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerColumn());
        mazeDisplayer.setMaze(maze);
        mazeDisplayer.displayMaze();
    }

    /**
     * bind the properties of the ViewModel into the text property of the maze displayer
     * @param viewModel MyViewModel
     */
    private void bindProperties(MyViewModel viewModel) {
        lbl_row.textProperty().bind(viewModel.sp_playerRow);
        lbl_column.textProperty().bind(viewModel.sp_playerColumn);
    }

    /**
     * method that generate the maze by passing it to the ViewModel
     */
    public void generateMaze() {
        try {
            height = Integer.parseInt(txt_rows.getText());
            width = Integer.parseInt(txt_columns.getText());
        } catch (Exception e) {
            showAlert("Please enter valid rows and columns numbers.");
        }
        btn_solve.setVisible(true);
        btn_hint.setVisible(true);
        btn_save.setVisible(true);
        btn_solve.setDisable(false);
        btn_hint.setDisable(false);
        btn_save.setDisable(false);
        viewModel.generateMaze(height, width);
    }
    /**
     * method that solving the maze by passing it to the ViewModel
     */
    public void solveMaze(ActionEvent actionEvent) {
        showAlert("Oh, Courage... No fear, Muriel is here to guide you!");
        viewModel.solve();
        btn_solve.setVisible(false);
        btn_hint.setVisible(false);
        btn_solve.setDisable(true);
        btn_hint.setDisable(true);
    }

    /**
     * this method pass to the user a hint for the next step
     * in order to get to the goal position
     * @param actionEvent ActionEvent
     */
    public void getHint(ActionEvent actionEvent) {
        viewModel.getHint();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    /**
     * show a generic structure for alert
     * @param alertMessage String
     */
    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    /**
     * this method handle situation when key pressed by the user
     * @param key KeyEvent
     */
    public void KeyPressed(KeyEvent key) {
        viewModel.move(key.getCode());
        key.consume();
    }

    /**
     * automatic resize of the window
     * @param scene Scene
     */
    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> mazeDisplayer.displayMaze());
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> mazeDisplayer.displayMaze());
    }

    /**
     * method that run when the user push the About label
     * @param actionEvent ActionEvent
     */
    public void about(ActionEvent actionEvent) {
        try {
            Stage aboutStage = new Stage();
            aboutStage.setTitle("About");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("box.css");
            aboutStage.setScene(scene);
            aboutStage.setResizable(false);
            aboutStage.initModality(Modality.APPLICATION_MODAL);
            aboutStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method that run when the user push the Help label
     * @param actionEvent ActionEvent
     */
    public void help(ActionEvent actionEvent) {
        Stage helpStage = new Stage();
        helpStage.setTitle("Help");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("box.css");
            helpStage.setScene(scene);
            helpStage.setResizable(false);
            helpStage.initModality(Modality.APPLICATION_MODAL);
            helpStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * method that run when the user push the Option label
     * @param actionEvent ActionEvent
     */
    public void option(ActionEvent actionEvent) {
        Stage optionStage = new Stage();
        optionStage.setTitle("Options");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("box.css");
            optionStage.setScene(scene);
            optionStage.setResizable(false);
            optionStage.initModality(Modality.APPLICATION_MODAL);
            optionStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * method that run when the user getting to the goal position
     */
    private void finish() {
        Stage finishStage = new Stage();
        finishStage.setTitle("Finished");
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("Finish.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("box.css");
            finishStage.setScene(scene);
            finishStage.setResizable(false);
            finishStage.initModality(Modality.APPLICATION_MODAL);
            finishStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method play the music from a path
     * @param path String
     */
    public static void playMusic(String path) {
        Media music = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    /**
     * method that saves the game
     */
    public void saveGame() {
        viewModel.save();
    }
    /**
     * method that loads the game
     */
    public void loadGame() {
        btn_solve.setVisible(true);
        btn_hint.setVisible(true);
        btn_save.setVisible(true);
        btn_save.setVisible(true);
        btn_solve.setDisable(false);
        btn_hint.setDisable(false);
        btn_save.setDisable(false);
        viewModel.load();
    }

    /**
     * this method handle with click on the game schens
     * @param mouseEvent MouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }
}