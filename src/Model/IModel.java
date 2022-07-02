package Model;

import javafx.scene.input.KeyCode;

import java.util.Observer;

public interface IModel {

<<<<<<< HEAD
    /**
     * assign observer into the observable object
     * @param o
     */
    void assignObserver(Observer o);

    /**
     * method to generate a game
     * @param row
     * @param col
     */
    void generate(int row, int col);

    /**
     * method to solve the game
     */
    void solve();

    /**
     * method that handle the movement from the user in the game
     * @param movement
     */
    void move(KeyCode movement);

    /**
     * method that save the game
     */
    void save();
    /**
     * load file and create maze according the file
     */
    void load();

    /**
     * method that return boolean if we finish to play
     * @return boolean
     */
    boolean isFinished();

    /**
     * getting the maze cells
     * @return int[][]
     */
    int[][] getMazeCells();

    /**
     * method to get a hint while solving the game
     */
    void getHint();

    /**
     * return the player row position
     * @return int
     */
    int getPlayerRow();
    /**
     * return the player column position
     * @return int
     */
    int getPlayerColumn();
    /**
     * return the goal position
     * @return int[]
     */
=======
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

>>>>>>> mvvm-packages
    int[] getGoal();
}