package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas{
    private int[][] maze;
    private int rowPlayer = 0;
    private int colPlayer = 0;
    /*
    The unique thing in StringProperty is that he is like an Observer,
    when we update it once we can "notify" all the other references of this filed
    that we update this filed
     */
    private StringProperty imageWallFile = new SimpleStringProperty();
    private StringProperty imagePlayerFile = new SimpleStringProperty();

    public String getImageWallFile() {
        return imageWallFile.get();
    }

    public StringProperty imageWallFileProperty() {
        return imageWallFile;
    }

    public void setImageWallFile(String imageWallFile) {
        this.imageWallFile.set(imageWallFile);
    }

    public String getImagePlayerFile() {
        return imagePlayerFile.get();
    }

    public StringProperty imagePlayerFileProperty() {
        return imagePlayerFile;
    }

    public void setImagePlayerFile(String imagePlayerFile) {
        this.imagePlayerFile.set(imagePlayerFile);
    }


    public void setRowPlayer(int rowPlayer) {
        this.rowPlayer = rowPlayer;
    }

    public int getColPlayer() {
        return colPlayer;
    }

    public void setColPlayer(int colPlayer) {
        this.colPlayer = colPlayer;
    }

    public int getRowPlayer() {
        return rowPlayer;
    }

    public void setPlayerPosition(int row, int col)
    {
        setRowPlayer(row);
        setColPlayer(col);
        draw();
    }
    public void drawMaze(int[][] maze) {
        this.maze = maze;
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.length;
            int cols = maze[0].length;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.RED);

            Image wallI = null;
            try {
                wallI = new Image(new FileInputStream(getImageWallFile()));
            } catch (FileNotFoundException e) {
                System.out.println("No image was found");
            }
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if(maze[i][j] == 1){
                        //if it is a wall:
                        double w = j * cellWidth;
                        double h = i * cellHeight;
                        if (wallI == null)
                            graphicsContext.fillRect(w, h, cellWidth, cellHeight);
                        else
                            graphicsContext.drawImage(wallI ,w, h, cellWidth, cellHeight);
                    }
                }
            }
            /* Player section */
            double hPlayer = getRowPlayer() * cellHeight;
            double wPlayer = getColPlayer() * cellWidth;
            Image playerI = null;
            try{ playerI = new Image(new FileInputStream(getImagePlayerFile())); }
            catch (FileNotFoundException e){ System.out.println("No player image founded"); }
            graphicsContext.drawImage(playerI ,wPlayer, hPlayer, cellWidth, cellHeight);
        }
    }
}
