package ViewModel;

import Model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;

    private int row;
    private int column;

    public StringProperty sp_playerRow = new SimpleStringProperty("1");
    public StringProperty sp_playerColumn = new SimpleStringProperty("1");

    /**
     * A constructor of MyViewModel
     * @param model get a model which the ViewModel observes
     */
    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
    }

    /**
     * this method execute when the observable Object is notifying
     * all of his observers that he make a change or update
     * in this method we handle the update
     * @param obs     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable obs, Object arg) {
        if (obs == model) {
            row = model.getPlayerRow();
            sp_playerRow.set(row + "");
            column = model.getPlayerColumn();
            sp_playerColumn.set(column + "");
        }
        setChanged();
        notifyObservers();
    }

    /**
     * method to generate a maze and pass it to the model
     * @param rows int
     * @param columns int
     */
    public void generateMaze(int rows, int columns) {
        model.generate(rows, columns);
    }
    /**
     * method to solve the maze by passing it to our model,
     * and update all the observers afterwards
     */
    public void solve() {
        model.solve();
    }
    /**
     * method that handle the movement from the View in the maze
     * @param movement KeyCode
     */
    public void move(KeyCode movement) {
        model.move(movement);
    }
    /**
     * method that save the game
     */
    public void save() {
        model.save();
    }
    /**
     * load file and create maze according the file
     */
    public void load() {
        model.load();
    }
    /**
     * method that return boolean if we finish to play
     * @return boolean
     */
    public boolean IsFinished() {
        return model.isFinished();
    }
    /**
     * getter to get the maze
     * @return int[][]
     */
    public int[][] getMaze() {
        return model.getMazeCells();
    }

    public void getHint() {
        model.getHint();
    }
    /**
     * return the goal position
     * @return int[]
     */
    public int[] getGoal() {
        return model.getGoal();
    }
    /**
     * return the player row position
     * @return int
     */
    public int getPlayerRow() {
        return row;
    }
    /**
     * return the player column position
     * @return int
     */
    public int getPlayerColumn() {
        return column;
    }
}