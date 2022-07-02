package View;

import ViewModel.MyViewModel;

public interface IView {

<<<<<<< HEAD
    /**
     * Initializer of the View controller.
     * @param viewModel MyViewModel
     */
    void initialize(MyViewModel viewModel);

    /**
     * Displays the maze.
     * @param maze int[][]
     */
=======
    void initialize(MyViewModel viewModel);

>>>>>>> mvvm-packages
    void displayMaze(int[][] maze);
}