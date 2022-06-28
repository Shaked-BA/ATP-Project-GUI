package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class MyModel extends Observable implements IModel {

    private Maze maze;
    private int[][] mazeCells;
    private ArrayList<AState> mazeSolutionSteps;
    private int row;
    private int column;
    private int cluesSent = 0;
    private Server generator;
    private Server solver;

    @Override
    public void assignObserver(Observer o) {
        addObserver(o);
    }

    @Override
    public void generate(int rows, int columns){
        generator = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        generator.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{rows, columns};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[2512];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                        row = maze.getStartPosition().getRowIndex();
                        column = maze.getStartPosition().getColumnIndex();
                        mazeCells = new int[rows][columns];
                        for (int r = 0; r < rows; r++)
                            for (int c = 0; c < columns; c++)
                                mazeCells[r][c] = maze.getCellValue(r, c);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
        generator.stop();
        mazeSolutionSteps = null;
        cluesSent = 0;
        setChanged();
        notifyObservers();
    }

    @Override
    public void solve() {
        if (mazeSolutionSteps == null)
            setSolution();
        for (AState step : mazeSolutionSteps) {
            MazeState mStep = (MazeState) step;
            mazeCells[mStep.getRow()][mStep.getColumn()] = -1;
        }
        setChanged();
        notifyObservers();
    }

    private void setSolution() {
        solver = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solver.start();
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        mazeSolutionSteps = ((Solution)fromServer.readObject()).getSolutionPath();
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
    }

    @Override
    public void move(KeyCode movement){
        if (maze == null)
            return;
        if (mazeSolutionSteps != null) {
            for (AState step : mazeSolutionSteps) {
                MazeState mStep = (MazeState) step;
                mazeCells[mStep.getRow()][mStep.getColumn()] = 0;
            }
        }
        switch (movement) {
            case UP: case NUMPAD8: //up
                if (isLegalCell(row - 1, column))
                    row--;
                break;
            case DOWN: case NUMPAD2: //down
                if (isLegalCell(row + 1, column))
                    row++;
                break;
            case LEFT: case NUMPAD4: //left
                if (isLegalCell(row, column - 1))
                    column--;
                break;
            case RIGHT: case NUMPAD6: //right
                if (isLegalCell(row, column + 1))
                    column++;
                break;
            case Q: case NUMPAD7: //up-left
                if (isLegalCell(row - 1, column - 1)) {
                    row--;
                    column--;
                }
                break;
            case W: case NUMPAD9: //up-right
                if (isLegalCell(row - 1, column + 1)) {
                    row--;
                    column++;
                }
                break;
            case A: case NUMPAD1: //down-left
                if (isLegalCell(row + 1, column - 1)) {
                    row++;
                    column--;
                }
                break;
            case S: case NUMPAD3: //down-right
                if (isLegalCell(row + 1, column + 1)) {
                    row++;
                    column++;
                }
                break;
            case HOME:
                row = maze.getStartPosition().getRowIndex();
                column = maze.getStartPosition().getColumnIndex();
            default:
                return;
        }
        setChanged();
        notifyObservers();
    }

    @Override
    public void save(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Maze");
        fileChooser.setInitialFileName("maze.txt");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try{
                if (!selectedFile.createNewFile()) {
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(selectedFile));
                    outputStream.writeObject(maze);
                    outputStream.writeObject(row);
                    outputStream.writeObject(column);
                    outputStream.flush();
                    outputStream.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * load file and create maze according the file
     */
    @Override
    public void load(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Maze");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try{
                ObjectInputStream  inputStream = new ObjectInputStream(new FileInputStream(selectedFile));
                maze = (Maze) inputStream.readObject();
                row = (int) inputStream.readObject();
                column = (int) inputStream.readObject();
                inputStream.close();
                mazeCells = new int[maze.getRows()][maze.getColumns()];
                for (int r = 0; r < mazeCells.length; r++) {
                    for (int c = 0; c < mazeCells[0].length; c++) {
                        mazeCells[r][c] = maze.getCellValue(r, c);
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        mazeSolutionSteps = null;
        cluesSent = 0;
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean isFinished(){
        if (maze == null)
            return false;
        return maze.getGoalPosition().getRowIndex() == getPlayerRow() && maze.getGoalPosition().getColumnIndex() == getPlayerColumn();
    }

    /**
     * getter
     * @return correct maze
     */
    @Override
    public int[][] getMazeCells(){
        return mazeCells;
    }

    /**
     * getter
     * @return the final solution maze in array
     */
    @Override
    public void getHint(){
        if (mazeSolutionSteps == null)
            setSolution();
        if (cluesSent == mazeSolutionSteps.size())
            return;
        cluesSent++;
        MazeState mazeState;
        for (int i = 0; i < cluesSent; i++) {
            mazeState = (MazeState) mazeSolutionSteps.get(i);
            mazeCells[mazeState.getRow()][mazeState.getColumn()] = -1;
        }
        setChanged();
        notifyObservers();
    }

    /**
     * getter
     * @return row position of character
     */
    @Override
    public int getPlayerRow(){
        return row;
    }

    /**
     * getter
     * @return col position of character
     */
    @Override
    public int getPlayerColumn(){
        return  column;
    }

    @Override
    public  int[] getGoal() {
        return new int[]{maze.getGoalPosition().getRowIndex(), maze.getGoalPosition().getColumnIndex()};
    }

    private boolean isLegalCell(int row, int column) {
        return (row >= 0 && column >= 0 && row < maze.getRows() && column < maze.getColumns() && maze.getCellValue(row, column) != 1);
    }
}