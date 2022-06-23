package Model;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.*;
import algorithms.search.AState;
import algorithms.search.Solution;

public class MyModel extends Observable implements IModel{
    private Maze maze;
    private int rowPlayer;
    private int colPlayer;

    private Server generator = new Server(5400, 1000, new ServerStrategyGenerateMaze());
    private Server solver = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
    Solution mazeSolution;
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

    public Maze getMaze() {
        return maze;
    }

    public void solveMaze(Maze maze)
    {
        solver.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        /*MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(50, 50);*/
                        maze.print();
                        toServer.writeObject(maze);
                        toServer.flush();
                        mazeSolution = (Solution)fromServer.readObject();
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();

                        for(int i = 0; i < mazeSolutionSteps.size(); ++i) {
                            System.out.println(String.format("%s. %s", i, ((AState)mazeSolutionSteps.get(i)).toString()));
                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
        solver.stop();
        setChanged();
        notifyObservers();
    }

    public ArrayList<AState> getSolution()
    {
        if (this.mazeSolution == null)
            this.solveMaze(this.maze);
        return this.mazeSolution.getSolutionPath();
        /*
        Getting the solution
         */
        /*setChanged();
        notifyObservers();*/
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
                if (isLegalCell(this.rowPlayer-1, this.colPlayer))
                    this.rowPlayer--;
                break;
            case 2:
                if (isLegalCell(this.rowPlayer+1, this.colPlayer))
                    this.rowPlayer++;
                break;
            case 3:
                if (isLegalCell(this.rowPlayer, this.colPlayer-1))
                    this.colPlayer--;
                break;
            case 4:
                if (isLegalCell(this.rowPlayer, this.colPlayer+1))
                    this.colPlayer++;
                break;
        }
        setChanged();
        notifyObservers("moving");
    }

    public void generateMaze(int rows, int cols){
        try {
            this.generator.start();
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, cols};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[rows*cols+12];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                        maze.print();
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
        this.generator.stop();
        setChanged();
        notifyObservers();
    }

    public void assignObserver(Observer o)
    {
        this.addObserver(o);
    }

    private boolean isLegalCell(int row, int column) {
        return (row >= 0 && column >= 0 && row < maze.getRows() && column < maze.getColumns() && maze.getCellValue(row, column) != 1);
    }
}
