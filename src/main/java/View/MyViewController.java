package View;


import Model.IModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;


public class MyViewController implements IView, Initializable, Observer {
    private MyViewModel viewModel;
    public MazeGenerator generator;
    @FXML
    public TextField textField_mazeRows; // the name is like the name of the id: in the fxml file
    @FXML
    public TextField textField_mazeColumns; // the name is like the name of the id: in the fxml file
    @FXML
    public MazeDisplayer mazeDisplayer; // the name is like the name of the id: in the fxml file
    @FXML
    public Label lbl_playerRow;
    @FXML
    public Label lbl_playerColumn;
    public StringProperty updatePlayerRow = new SimpleStringProperty();
    public StringProperty updatePlayerColumn = new SimpleStringProperty();
    private Maze maze;
    int[][] solution;

    public void setViewModel(MyViewModel mvm) { this.viewModel = mvm; }
    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public StringProperty updatePlayerRowProperty() {
        return updatePlayerRow;
    }

    public void setUpdatePlayerRow(String updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow);
    }

    public String getUpdatePlayerColumn() {
        return updatePlayerColumn.get();
    }

    public StringProperty updatePlayerColumnProperty() {
        return updatePlayerColumn;
    }

    public void setUpdatePlayerColumn(String updatePlayerColumn) {
        this.updatePlayerColumn.set(updatePlayerColumn);
    }



    public void generateMaze(ActionEvent actionEvent) {

        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());
        viewModel.generateMaze(rows, cols);
        mazeDisplayer.drawMaze(maze);
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        this.solution = this.viewModel.getSolution();
    }

    public void showAlert (String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public void keyPressed(KeyEvent keyEvent) {
        boolean finish = this.viewModel.updatePlayer(keyEvent);
        if (finish)
        {
            IsItTheEnd finale = new IsItTheEnd("Good Job!", "You get to the goal!");
        }
        // this will allow us to focus just on the user's keyboard input and not to move to another layout's component
        keyEvent.consume();
    }

    /* When clicked on the maze the user can now play on the maze */
    public void mouseClicked(MouseEvent mouseEvent) { mazeDisplayer.requestFocus(); }

    /*
    When we load the Controller once, this method is running
    and we want to bind between the Labels into the player position
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_playerRow.textProperty().bind(/*this is the observable and when the observer will update this attribute will update too*/updatePlayerRow);
        lbl_playerColumn.textProperty().bind(/*this is the observable and when the observer will update this attribute will update too*/updatePlayerColumn);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MyViewModel)
        {
            if (maze == null) // Generate Maze
            {
                if ("generate maze" == arg){
                    this.maze = this.viewModel.getMaze();
                    this.mazeDisplayer.setPlayerPosition(this.maze.getStartPosition().getRowIndex(), this.maze.getStartPosition().getColumnIndex()); // not sure about this method!
                    drawMaze();
                }
            }
            else
            {
                if ("generate maze" == arg){
                    this.maze = this.viewModel.getMaze();
                    this.mazeDisplayer.setPlayerPosition(this.maze.getStartPosition().getRowIndex(), this.maze.getStartPosition().getColumnIndex()); // not sure about this method!
                    drawMaze();
                }
                Maze mazer = this.viewModel.getMaze();
                int rowPlayer = this.mazeDisplayer.getRowPlayer();
                int colPlayer = this.mazeDisplayer.getColPlayer();
                int rowFromViewModel = this.viewModel.getRowPlayer();
                int colFromViewModel = this.viewModel.getColPlayer();
                if ("moving" == arg)
                {
                    setUpdatePlayerRow(rowFromViewModel + "");
                    setUpdatePlayerColumn(colFromViewModel + "");
                    this.mazeDisplayer.setPlayerPosition(rowFromViewModel, colFromViewModel);
                    drawMaze();
                }
                if ("getting solution" == arg)
                {
                    solution = this.viewModel.getSolution();
                }
                if (this.maze != mazer) // it's means that the update is not generate maze it's update player
                {
                    this.maze = mazer;
                    drawMaze();
                }
            }
        }
    }
    private void drawMaze()
    {
        this.mazeDisplayer.drawMaze(this.maze);
    }

    /*@Override
    public void update(Observable o, Object arg) {
        if (o instanceof MyViewModel)
        {
            if (maze == null) // Generate Maze
            {
                this.maze = this.viewModel.getMaze();
                drawMaze();
            }
            else
            {
                Maze mazer = this.viewModel.getMaze();
                if (this.maze == mazer) // it's means that the update is not generate maze it's update player
                {
                    int rowPlayer = this.mazeDisplayer.getRowPlayer();
                    int colPlayer = this.mazeDisplayer.getColPlayer();
                    int rowFromViewModel = this.viewModel.getRowPlayer();
                    int colFromViewModel = this.viewModel.getColPlayer();
                    if ("moving" == arg)
                    {
                        setUpdatePlayerRow(rowFromViewModel + "");
                        setUpdatePlayerColumn(colFromViewModel + "");
                        this.mazeDisplayer.setPlayerPosition(rowFromViewModel, colFromViewModel);
                        drawMaze();
                    }
                    if (colPlayer == rowFromViewModel && rowPlayer == colFromViewModel) // Solving Maze
                    {
                        solution = this.viewModel.getSolution();
                        showAlert("Solving Maze");
                    }
                    else
                    {
                        setUpdatePlayerRow(rowFromViewModel + "");
                        setUpdatePlayerColumn(colFromViewModel + "");
                        this.mazeDisplayer.setPlayerPosition(rowFromViewModel, colFromViewModel);
                        drawMaze();
                    }
                }
                else
                {
                    this.maze = mazer;
                    drawMaze();
                }
            }
        }
    }*/

}
class IsItTheEnd extends Stage
{
    private static final Interpolator EXP_IN = new Interpolator() {
        @Override
        protected double curve(double v) {
            return (v == 1.0 ? 1.0 : 1 - Math.pow(2.0, -10*v));
        }
    };
    private static final Interpolator EXP_OUT = new Interpolator() {
        @Override
        protected double curve(double v) {
            return (v == 0.0 ? 0.0 : 1 - Math.pow(2.0, -10*(v-1)));
        }
    };
    private ScaleTransition scale1 = new ScaleTransition();
    private ScaleTransition scale2 = new ScaleTransition();
    private SequentialTransition anim = new SequentialTransition(scale1, scale2);
    IsItTheEnd(String header, String content)
    {
        Pane root = new Pane();

        scale1.setFromX(0.01);
        scale1.setFromY(0.01);
        scale1.setToY(1.0);
        scale1.setDuration(Duration.seconds(0.33));
        scale1.setInterpolator(EXP_IN);
        scale1.setNode(root);

        scale2.setFromX(0.01);
        scale2.setToX(1.0);
        scale2.setDuration(Duration.seconds(0.33));
        scale2.setInterpolator(EXP_OUT);
        scale2.setNode(root);


        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);

        Rectangle backGround = new Rectangle(350, 150, Color.INDIGO);
        backGround.setStroke(Color.BLANCHEDALMOND);
        backGround.setStrokeWidth(1.5);

        Text headerText = new Text(header);
        headerText.setFont(Font.font(20));

        Text contentText = new Text(content);
        contentText.setFont(Font.font(14));

        VBox box = new VBox(10, headerText, new Separator(Orientation.HORIZONTAL), contentText);
        box.setPadding(new Insets(15));
        Button ok = new Button("OK Got It");
        ok.setTranslateX(backGround.getWidth()-100);
        ok.setTranslateY(backGround.getHeight()-100);
        ok.setOnAction(e -> closeDialog());

        root.getChildren().addAll(box, ok);
        setScene(new Scene(root, null));
        openDialog();
    }

    public void openDialog(){ show(); anim.play(); }
    public void closeDialog()
    {
        anim.setOnFinished(e -> close());
        anim.setAutoReverse(true);
        anim.setCycleCount(2);
        anim.playFrom(Duration.seconds(0.66));
    }
}