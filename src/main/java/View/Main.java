package View;


import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml") /*Main.class.getResource("MyView.fxml")*/);
        Parent root = fxmlLoader.load();
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 800, 650));
        stage.show();

        // Here we start a new instance of ViewModel that connects us with the Model from the View point of view
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = fxmlLoader.getController();
        view.setViewModel(viewModel);
    }

    public static void main(String[] args) {
        launch();
    }
}
