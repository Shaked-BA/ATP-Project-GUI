package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Observer;

public interface IModel {
    public int getRowPlayer();
    public int getColPlayer();
    public Maze getMaze();
    public boolean updatePlayerPosition(KeyCode direction);
    public void generateMaze(int rows, int cols);
    public void assignObserver(Observer o);
    public void solveMaze(Maze maze);
    public ArrayList<AState> getSolution();
}
