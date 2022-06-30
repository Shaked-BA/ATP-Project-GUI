package Model;

import javafx.scene.input.KeyCode;

import java.util.Observer;

public interface IModel {

    void assignObserver(Observer o);

    void generate(int row, int col);

    void solve();

    void move(KeyCode movement);

    void save();

    void load();

    boolean isFinished();

    int[][] getMazeCells();

    void getHint();

    int getPlayerRow();

    int getPlayerColumn();

    int[] getGoal();
}