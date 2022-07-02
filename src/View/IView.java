package View;

import ViewModel.MyViewModel;

public interface IView {

    /**
     * Initializer of the View controller.
     * @param viewModel MyViewModel
     */
    void initialize(MyViewModel viewModel);

    /**
     * Displays the maze.
     * @param maze int[][]
     */
    void displayMaze(int[][] maze);
}