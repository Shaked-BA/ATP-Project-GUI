package View;

import ViewModel.MyViewModel;

public interface IView {
    /**
     * initializer of the View controller
     * @param viewModel MyViewModel
     */
    void initialize(MyViewModel viewModel);

    /**
     * display the maze
     * @param maze int[][]
     */
    void displayMaze(int[][] maze);
}