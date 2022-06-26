package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private IModel model;
    int rowPlayer;
    int colPlayer;
    ArrayList<AState> solution;
    private Maze maze;

    public MyViewModel(IModel model)
    {
        this.model = model;
        this.model.assignObserver(this);
    }


    public Maze getMaze() { return maze; }
    public int getRowPlayer() {
        return rowPlayer;
    }

    public int getColPlayer() {
        return colPlayer;
    }
     public void generateMaze(int rows, int cols)
     {
         this.model.generateMaze(rows, cols);
         setChanged();
         notifyObservers("generate maze");
     }
    public boolean updatePlayer(KeyEvent key)
    {
        /*int moveTO = 0;*/
        KeyCode moveTo = key.getCode();
        boolean finish = this.model.updatePlayerPosition(moveTo);
        setChanged();
        notifyObservers("moving");
        return finish;
    }

    public void solveMaze(Maze maze)
    {
        this.model.solveMaze(maze);
        setChanged();
        notifyObservers("solving maze");
    }
    public int[][] getSolution()
    {
        if (this.solution == null)
        {
            solveMaze(this.maze);
        }
        this.solution = this.model.getSolution();
        int[][] solutionArray = new int[2][this.solution.size()];
        for (int i = 0; i < this.solution.size(); i++) {
            solutionArray[0][i] = ((MazeState)(this.solution.get(i))).getRow();
            solutionArray[1][i] = ((MazeState)(this.solution.get(i))).getColumn();
        }
        return solutionArray;
    }
    public void assignObserver(Observer o)
    {
        this.addObserver(o);
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
                Maze mazer = model.getMaze();
                if (this.maze == mazer) // it's means that the update is not generate maze it's update player
                {
                    int rowPlayerT = this.model.getRowPlayer();
                    int colPlayerT = this.model.getColPlayer();
                    if (this.colPlayer == colPlayerT && this.rowPlayer == rowPlayerT) // Which means that the user Not change location on the grid, solve maze method
                    {
                        this.solution = this.model.getSolution();
                    }
                    else
                    {
                        if ("moving" == arg) {
                            this.rowPlayer = rowPlayerT;
                            this.colPlayer = colPlayerT;
                        }

                    }
                }
                else { this.maze = mazer; }
            }
            setChanged();
            notifyObservers();
        }
    }
}
