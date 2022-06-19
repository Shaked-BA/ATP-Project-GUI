package View;


import Model.IModel;
import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private int[][] /* TODO change it to Maze class*/ maze;

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
        if(generator == null)
            generator = new MazeGenerator();

        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());
        viewModel.generateMaze(rows, cols);
        int[][] maze = generator.generateRandomMaze(rows, cols);

        mazeDisplayer.drawMaze(maze);
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
        this.viewModel.solveMaze(this.maze);
    }

    public void showAlert (String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    public void keyPressed(KeyEvent keyEvent) {
        this.viewModel.updatePlayer(keyEvent);

        /*
        int playerR = mazeDisplayer.getRowPlayer();
        int playerC = mazeDisplayer.getColPlayer();
        switch (keyEvent.getCode())
        {
            case UP:
                mazeDisplayer.setPlayerPosition(playerR-1, playerC);
                setUpdatePlayerRow(playerR-1 + "");
                setUpdatePlayerColumn(playerC + "");
                break;
            case DOWN:
                mazeDisplayer.setPlayerPosition(playerR+1, playerC);
                setUpdatePlayerRow(playerR+1 + "");
                setUpdatePlayerColumn(playerC + "");
                break;
            case LEFT:
                mazeDisplayer.setPlayerPosition(playerR, playerC-1);
                setUpdatePlayerRow(playerR + "");
                setUpdatePlayerColumn(playerC-1+ "");
                break;
            case RIGHT:
                mazeDisplayer.setPlayerPosition(playerR, playerC+1);
                setUpdatePlayerRow(playerR + "");
                setUpdatePlayerColumn(playerC+1 + "");
                break;
            default:
                mazeDisplayer.setPlayerPosition(playerR, playerC);
                setUpdatePlayerRow(playerR + "");
                setUpdatePlayerColumn(playerC + "");
        }*/
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
        //TODO
        if (o instanceof MyViewModel)
        {
            if (maze == null) // Generate Maze
            {
                this.maze = this.viewModel.getMaze();
                drawMaze();
            }
            else
            {
                int[][] mazer = this.viewModel.getMaze();
                if (this.maze == mazer) // it's means that the update is not generate maze it's update player
                {
                    int rowPlayer = this.mazeDisplayer.getRowPlayer();
                    int colPlayer = this.mazeDisplayer.getColPlayer();
                    int rowFromViewModel = this.viewModel.getRowPlayer();
                    int colFromViewModel = this.viewModel.getColPlayer();
                    if (colPlayer == rowFromViewModel && rowPlayer == colFromViewModel) // Solving Maze
                    {
                        this.viewModel.getSolution();
                        showAlert("Solving Maze");
                    }
                    else
                    {
                        setUpdatePlayerRow(rowFromViewModel + "");
                        setUpdatePlayerColumn(colFromViewModel + "");
                        this.mazeDisplayer.setPlayerPosition(rowFromViewModel, colFromViewModel);
                    }
                }
                else
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
}
