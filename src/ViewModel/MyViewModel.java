package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Position;
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

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this);
    }

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

    public void generateMaze(int rows, int columns) {
        model.generate(rows, columns);
    }

    public void solve() {
        model.solve();
    }

    public void move(KeyCode movement) {
        model.move(movement);
    }

    public void save() {
        model.save();
    }

    public void load() {
        model.load();
    }

    public boolean IsFinished() {
        return model.isFinished();
    }

    public int[][] getMaze() {
        return model.getMazeCells();
    }

    public void getHint() {
        model.getHint();
    }

    public int[] getGoal() {
        return model.getGoal();
    }

    public int getPlayerRow() {
        return row;
    }

    public int getPlayerColumn() {
        return column;
    }
}