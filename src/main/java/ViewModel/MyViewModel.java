package ViewModel;

import Model.IModel;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    int rowPlayer;
    int colPlayer;

    private int[][] maze;

    public MyViewModel(IModel model)
    {
        this.model = model;
        this.model.assignObserver(this);
    }
    /*
    We use update method when an update from our observer (IModel)
    like: - generate maze
          - update player location
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof  IModel)
        {
            if (maze == null)
            {
                this.maze = this.model.getMaze();
            }
            else
            {
                int[][] mazer = model.getMaze();
                if (this.maze == mazer) // it's means that the update is not generate maze it's update player
                {
                    int rowPlayerT = this.model.getRowPlayer();
                    int colPlayerT = this.model.getColPlayer();
                    if (this.colPlayer == colPlayerT && this.rowPlayer == rowPlayerT) // Which means that the user Not change location on the grid, solve maze method
                    {
                        this.model.getSolution();
                    }
                    else
                    {
                        this.rowPlayer = rowPlayerT;
                        this.colPlayer = colPlayerT;
                    }
                }
                else { this.maze = mazer; }
            }
            setChanged();
            notifyObservers();
        }
    }
    public int[][] /* TODO changed this to class Maze */ getMaze() { return maze; }
    public int getRowPlayer() {
        return rowPlayer;
    }

    public int getColPlayer() {
        return colPlayer;
    }
     public void generateMaze(int rows, int cols)
     {
         this.model.generateRandomMaze(rows, cols);
     }
    public void updatePlayer(KeyEvent key)
    {
        int moveTO = 0;
        switch (key.getCode())
        {
            case UP:
                this.model.updatePlayerPosition(1);
                break;
            case DOWN:
                this.model.updatePlayerPosition(2);
                break;
            case LEFT:
                this.model.updatePlayerPosition(3);
                break;
            case RIGHT:
                this.model.updatePlayerPosition(4);
                break;
        }
    }

    public void solveMaze(int[][] /* TODO changed it into Maze representation*/ maze)
    {
        this.model.solveMaze(maze);
    }
    public void getSolution()
    {
        this.model.getSolution();
    }
}
