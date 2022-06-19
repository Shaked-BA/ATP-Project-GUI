package Model;

import java.util.Observer;

public interface IModel {
    public int getRowPlayer();
    public int getColPlayer();
    public int[][] getMaze();
    public void updatePlayerPosition(int direction);
    public void generateRandomMaze(int rows, int cols);
    public void assignObserver(Observer o);
    public void solveMaze(int[][] /* TODO changed it into Maze representation*/ maze);
    public void getSolution();
}
