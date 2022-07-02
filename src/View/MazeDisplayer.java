package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
<<<<<<< HEAD
import javafx.scene.input.ScrollEvent;
=======
>>>>>>> mvvm-packages

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

<<<<<<< HEAD
    /**
     * constructor of the Maze Displayer class
     */
=======
>>>>>>> mvvm-packages
    public MazeDisplayer() {
        try {
            wallImage = new Image(new FileInputStream("resources/images/wall.jpg"));
            floorImage = new Image(new FileInputStream("resources/images/floor.jpg"));
            characterImage= new Image(new FileInputStream("resources/images/character.png"));
            solutionImage = new Image(new FileInputStream("resources/images/solution.png"));
<<<<<<< HEAD
=======
            hintImage = new Image(new FileInputStream("resources/images/hint.jpg"));
>>>>>>> mvvm-packages
            goalImage = new Image(new FileInputStream("resources/images/goal.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    /**
     * construct the maze and display it to the user
     */
=======
>>>>>>> mvvm-packages
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

<<<<<<< HEAD
    /**
     * setting the maze in our game
     * @param maze int[][]
     */
=======
>>>>>>> mvvm-packages
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

<<<<<<< HEAD
    /**
     * set the goal position
     * @param goal int[]
     */
=======
>>>>>>> mvvm-packages
    public void setGoal(int[] goal) {
        this.goal = goal;
    }

<<<<<<< HEAD
    /**
     * set the player position on the maze
     * @param row int
     * @param column int
     */
=======
>>>>>>> mvvm-packages
    public void setPlayerPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

<<<<<<< HEAD
    /**
     * this is a setter of the wall image
     * @param wallImage Image
     */
=======
>>>>>>> mvvm-packages
    public void setWallImage(Image wallImage) {
        this.wallImage = wallImage;
    }

<<<<<<< HEAD
    /**
     * this is a setter for a wall image
     * @param floorImage Image
     */
=======
>>>>>>> mvvm-packages
    public void setFloorImage(Image floorImage) {
        this.floorImage = floorImage;
    }

<<<<<<< HEAD
    /**
     * this is a setter for a character image
     * @param characterImage Image
     */
=======
>>>>>>> mvvm-packages
    public void setCharacterImage(Image characterImage) {
        this.characterImage = characterImage;
    }

<<<<<<< HEAD
    /**
     * this is a setter for a solution image
     * @param solutionImage Image
     */
=======
>>>>>>> mvvm-packages
    public void setSolutionImage(Image solutionImage) {
        this.solutionImage = solutionImage;
    }

<<<<<<< HEAD
    /**
     * this is a getter of the Wall image
     * @return Image
     */
=======
>>>>>>> mvvm-packages
    public Image getWallImage() {
        return wallImage;
    }

<<<<<<< HEAD
    /**
     * this is a getter of the Floor image
     * @return Image
     */
=======
>>>>>>> mvvm-packages
    public Image getFloorImage() {
        return floorImage;
    }

<<<<<<< HEAD
    /**
     * this i a getter of the Character image
     * @return Image
     */
=======
>>>>>>> mvvm-packages
    public Image getCharacterImage() {
        return characterImage;
    }

<<<<<<< HEAD
    /**
     * this is a getter of the Solution image
     * @return
     */
=======
>>>>>>> mvvm-packages
    public Image getSolutionImage() {
        return solutionImage;
    }

<<<<<<< HEAD
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
=======
    public Image getHintImage() {
        return hintImage;
    }
>>>>>>> mvvm-packages
}