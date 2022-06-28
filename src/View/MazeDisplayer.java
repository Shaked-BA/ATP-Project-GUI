package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;

public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private int row;
    private int column;
    private int[] goal;
    private Image wallImage;
    private Image floorImage;
    private Image characterImage;
    private Image solutionImage;
    private Image hintImage;
    private Image goalImage;

    public MazeDisplayer() {
        try {
            wallImage = new Image(new FileInputStream("resources/images/wall.jpg"));
            floorImage = new Image(new FileInputStream("resources/images/floor.jpg"));
            characterImage= new Image(new FileInputStream("resources/images/character.png"));
            solutionImage = new Image(new FileInputStream("resources/images/solution.png"));
            hintImage = new Image(new FileInputStream("resources/images/hint.jpg"));
            goalImage = new Image(new FileInputStream("resources/images/goal.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayMaze() {
        if (maze != null) {
            double cellHeight = getHeight() / maze.length;
            double cellWidth = getWidth() / maze[0].length;
            GraphicsContext graphicsContext2D = getGraphicsContext2D();
            graphicsContext2D.clearRect(0, 0, getWidth(), getHeight());
            for (int r = 0; r < maze.length; r++) {
                for (int c = 0; c < maze[r].length; c++) {
                    if (maze[r][c] == 1)
                        graphicsContext2D.drawImage(wallImage, c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                    else if (maze[r][c] == 0)
                        graphicsContext2D.drawImage(floorImage, c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                    else
                        graphicsContext2D.drawImage(solutionImage, c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                }
            }
            graphicsContext2D.drawImage(characterImage , column * cellWidth, row * cellHeight, cellWidth, cellHeight);
            graphicsContext2D.drawImage(goalImage, goal[1] * cellWidth, goal[0] * cellHeight, cellWidth, cellHeight);
        }
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void setGoal(int[] goal) {
        this.goal = goal;
    }

    public void setPlayerPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setWallImage(Image wallImage) {
        this.wallImage = wallImage;
    }

    public void setFloorImage(Image floorImage) {
        this.floorImage = floorImage;
    }

    public void setCharacterImage(Image characterImage) {
        this.characterImage = characterImage;
    }

    public void setSolutionImage(Image solutionImage) {
        this.solutionImage = solutionImage;
    }

    public Image getWallImage() {
        return wallImage;
    }

    public Image getFloorImage() {
        return floorImage;
    }

    public Image getCharacterImage() {
        return characterImage;
    }

    public Image getSolutionImage() {
        return solutionImage;
    }

    public Image getHintImage() {
        return hintImage;
    }
}