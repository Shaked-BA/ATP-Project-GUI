package View;

import ViewModel.MyViewModel;

public interface IView {

    void initialize(MyViewModel viewModel);

    void displayMaze(int[][] maze);
}