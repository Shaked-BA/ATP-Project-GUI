package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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
    public javafx.scene.text.Text txt_insertRow;
    public javafx.scene.text.Text txt_insertColumn;
    public javafx.scene.text.Text txt_currentRow;
    public javafx.scene.text.Text txt_currentColumn;
    public javafx.scene.control.TextField txt_rows;
    public javafx.scene.control.TextField txt_columns;
    public javafx.scene.control.Label lbl_row;
    public javafx.scene.control.Label lbl_column;
    public javafx.scene.control.Button btn_generate;
    public javafx.scene.control.Button btn_solve;
    public javafx.scene.control.Button btn_hint;
    public javafx.scene.control.Button btn_save;
    public static MediaPlayer mediaPlayer;
    private int height;
    private int width;

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
            resetZoomEvent();
            displayMaze(viewModel.getMaze());
            if (viewModel.IsFinished()) {
                showAll();
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
            if (height < 2 || width < 2)
                throw new Exception();
        } catch (Exception e) {
            showAlert("Please enter valid rows and columns numbers.");
            return;
        }
        showAll();
        viewModel.generateMaze(height, width);
    }

    /**
     * method that solving the maze by passing it to the ViewModel
     */
    public void solveMaze(ActionEvent actionEvent) {
        showAlert("Oh, Courage... No fear, Muriel is here to guide you!");
        viewModel.solve();
        showAll();
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

    /**
     * Exits stage.
     * @param actionEvent
     */
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
        if (key.getCode() == KeyCode.CONTROL) {
            zoomScroll();
        }
        viewModel.move(key.getCode());
        key.consume();
    }

    /**
     * automatic resize of the window
     * @param scene Scene
     */
    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> System.out.println("Width: " + newSceneWidth));
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> System.out.println("Height: " + newSceneHeight));
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
            scene.getStylesheets().add("Generic.css");
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
            scene.getStylesheets().add("Generic.css");
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
            Parent root = fxmlLoader.load(getClass().getResource("Option.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add("Generic.css");
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
            scene.getStylesheets().add("Generic.css");
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
        showAll();
        viewModel.load();
    }

    /**
     * this method handle with click on the game schens
     * @param mouseEvent MouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        this.mazeDisplayer.requestFocus();
    }

    /**
     * Resets zoom.
     */
    public void resetZoomEvent(){
        mazeDisplayer.setTranslateX(mazeDisplayer.getParent().getTranslateX());
        mazeDisplayer.setTranslateY(mazeDisplayer.getParent().getTranslateY());
        mazeDisplayer.setScaleX(mazeDisplayer.getParent().getScaleX());
        mazeDisplayer.setScaleY(mazeDisplayer.getParent().getScaleY());
    }

    public void zoomScroll(){
        double originalX = mazeDisplayer.getScaleX();
        mazeDisplayer.setOnScroll((ScrollEvent scrollEvent) -> {
            double zoomFactor = 1.05;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            mazeDisplayer.setScaleX(mazeDisplayer.getScaleX() * zoomFactor);
            mazeDisplayer.setScaleY(mazeDisplayer.getScaleY() * zoomFactor);
            if (originalX < mazeDisplayer.getScaleX()) {
                hideAll();
            }
            else {
                showAll();
            }
            mazeDisplayer.requestFocus();
            scrollEvent.consume();
        });
    }

    public void hideAll() {
        btn_generate.setVisible(false);
        btn_solve.setVisible(false);
        btn_hint.setVisible(false);
        btn_save.setVisible(false);
        txt_rows.setVisible(false);
        txt_columns.setVisible(false);
        txt_currentRow.setVisible(false);
        txt_currentColumn.setVisible(false);
        txt_insertRow.setVisible(false);
        txt_insertColumn.setVisible(false);
        lbl_row.setVisible(false);
        lbl_column.setVisible(false);
        btn_generate.setDisable(true);
        btn_solve.setDisable(true);
        btn_hint.setDisable(true);
        btn_save.setDisable(true);
        txt_rows.setDisable(true);
        txt_columns.setDisable(true);
        txt_currentRow.setDisable(true);
        txt_insertColumn.setDisable(true);
        txt_insertRow.setDisable(true);
        txt_insertColumn.setDisable(true);
        lbl_row.setDisable(true);
        lbl_column.setDisable(true);
    }

    private void showAll() {
        btn_generate.setVisible(true);
        btn_solve.setVisible(true);
        btn_hint.setVisible(true);
        btn_save.setVisible(true);
        txt_rows.setVisible(true);
        txt_columns.setVisible(true);
        txt_currentRow.setVisible(true);
        txt_currentColumn.setVisible(true);
        txt_insertRow.setVisible(true);
        txt_insertColumn.setVisible(true);
        lbl_row.setVisible(true);
        lbl_column.setVisible(true);
        btn_generate.setDisable(false);
        btn_solve.setDisable(false);
        btn_hint.setDisable(false);
        btn_save.setDisable(false);
        txt_rows.setDisable(false);
        txt_columns.setDisable(false);
        txt_currentRow.setDisable(false);
        txt_insertColumn.setDisable(false);
        txt_insertRow.setDisable(false);
        txt_insertColumn.setDisable(false);
        lbl_row.setDisable(false);
        lbl_column.setDisable(false);
    }
}