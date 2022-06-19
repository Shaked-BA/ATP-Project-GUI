package Model;

import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel{
    private int[][] maze;
    private int rowPlayer;
    private int colPlayer;

    public MyModel()
    {
        this.maze = null;
        this.rowPlayer = 0;
        this.colPlayer = 0;
    }
    public int getRowPlayer() {
        return rowPlayer;
    }

    public int getColPlayer() {
        return colPlayer;
    }

    public int[][] getMaze() {
        return maze;
    }

    public void solveMaze(int[][] /* TODO changed it into Maze representation*/ maze)
    {
        /*
            Solving the maze
         */
        setChanged();
        notifyObservers();
    }
    public void getSolution()
    {
        /*
        Getting the solution
         */
        setChanged();
        notifyObservers();
    }
    public void updatePlayerPosition(int direction)
    {
        /*
        1 -> UP
        2 -> DOWN
        3 -> LEFT
        4 -> RIGHT
        */
        switch (direction)
        {
            case 1:
                if (this.rowPlayer!=0)
                    this.rowPlayer--;
                break;
            case 2:
                if (this.rowPlayer!=this.maze.length-1)
                    this.rowPlayer++;
                break;
            case 3:
                if (this.colPlayer!=0)
                    this.rowPlayer--;
                break;
            case 4:
                if (this.rowPlayer!=maze[0].length-1)
                    this.rowPlayer++;
                break;
        }
        setChanged();
        notifyObservers();
    }

    public void generateRandomMaze(int rows, int cols){
        int[][] mazeG = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = (int) Math.round(Math.random());
            }
        }
        this.maze = mazeG;
        setChanged();
        notifyObservers();
    }

    public void assignObserver(Observer o)
    {
        this.addObserver(o);
    }
}
