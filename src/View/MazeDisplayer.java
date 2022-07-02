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

    /**
     * constructor of the Maze Displayer class
     */
    public MazeDisplayer() {
        try {
            wallImage = new Image(new FileInputStream("resources/images/wall.jpg"));
            floorImage = new Image(new FileInputStream("resources/images/floor.jpg"));
            characterImage= new Image(new FileInputStream("resources/images/character.png"));
            solutionImage = new Image(new FileInputStream("resources/images/solution.png"));
            goalImage = new Image(new FileInputStream("resources/images/goal.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * construct the maze and display it to the user
     */
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

    /**
     * setting the maze in our game
     * @param maze int[][]
     */
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    /**
     * set the goal position
     * @param goal int[]
     */
    public void setGoal(int[] goal) {
        this.goal = goal;
    }

    /**
     * set the player position on the maze
     * @param row int
     * @param column int
     */
    public void setPlayerPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * this is a setter of the wall image
     * @param wallImage Image
     */
    public void setWallImage(Image wallImage) {
        this.wallImage = wallImage;
    }

    /**
     * this is a setter for a wall image
     * @param floorImage Image
     */
    public void setFloorImage(Image floorImage) {
        this.floorImage = floorImage;
    }

    /**
     * this is a setter for a character image
     * @param characterImage Image
     */
    public void setCharacterImage(Image characterImage) {
        this.characterImage = characterImage;
    }

    /**
     * this is a setter for a solution image
     * @param solutionImage Image
     */
    public void setSolutionImage(Image solutionImage) {
        this.solutionImage = solutionImage;
    }

    /**
     * this is a getter of the Wall image
     * @return Image
     */
    public Image getWallImage() {
        return wallImage;
    }

    /**
     * this is a getter of the Floor image
     * @return Image
     */
    public Image getFloorImage() {
        return floorImage;
    }

    /**
     * this i a getter of the Character image
     * @return Image
     */
    public Image getCharacterImage() {
        return characterImage;
    }

    /**
     * this is a getter of the Solution image
     * @return
     */
    public Image getSolutionImage() {
        return solutionImage;
    }

    /**
     * this is a getter for the Hint image
     * @return Image
     */
    public Image getHintImage() {
        return hintImage;
    }

    /**
     * Resizes stage.
     * @param width
     * @param height
     */
    public void resize(double width, double height) {
        setWidth(width);
        setHeight(height);
        displayMaze();
    }
}