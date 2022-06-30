package View;

import Server.Server;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Properties;
import java.util.ResourceBundle;

public class Option extends Observable implements Initializable {
    public javafx.scene.control.Button btn_close;
    public javafx.scene.control.Button btn_save;
    public ChoiceBox<String> generators = new ChoiceBox<>();
    public ChoiceBox<String> algorithms = new ChoiceBox<>();
    public ChoiceBox<String> threads = new ChoiceBox<>();
    private StringProperty generator = new SimpleStringProperty("MyMazeGenerator");
    private StringProperty searcher = new SimpleStringProperty("BestFirstSearch");
    private StringProperty threadPool = new SimpleStringProperty("3");

    public void close() {
        Platform.exit();
    }

    public void closeWindow() {
        Stage s = (Stage) btn_close.getScene().getWindow();
        s.close();
    }

    public void save() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Settings Saved:\nGenerating algorithm- " + generator + "\nSearching algorithm- "+ searcher + "\nNumber of thread- " + threadPool);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InputStream input = Server.class.getClassLoader().getResourceAsStream("resources/config.properties");
            if (input == null) {
                OutputStream output = new FileOutputStream("resources/config.properties");
                Properties properties = new Properties();
                properties.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
                properties.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
                properties.setProperty("threadPoolSize", "3");
                properties.store(output, null);
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        generators.getItems().addAll("EmptyMazeGenerator", "SimpleMazeGenerator", "MyMazeGenerator");
        algorithms.getItems().addAll("BreadthFirstSearch", "DepthFirstSearch","BestFirstSearch");
        threads.getItems().addAll("1", "2", "3", "4", "5");
    }

    public void setConfiguration() throws IOException {
        OutputStream outputStream = new FileOutputStream("resources/config.properties");
        Properties prop = new Properties();
        switch (generators.getValue()) {
            case "BreadthFirstSearch":
                generator.set("BreadthFirstSearch");
            case "DepthFirstSearch":
                generator.set("DepthFirstSearch");
            default:
                generator.set("BestFirstSearch");
        }
        switch (algorithms.getValue()) {
            case "EmptyMazeGenerator":
                searcher.set("EmptyMazeGenerator");
            case "SimpleMazeGenerator":
                searcher.set("SimpleMazeGenerator");
            default:
                searcher.set("MyMazeGenerator");
        }
        switch (threads.getValue()) {
            case "1":
                threadPool.set("1");
            case "2":
                threadPool.set("2");
            case "4":
                threadPool.set("4");
            case "5":
                threadPool.set("5");
            default:
                threadPool.set("3");
        }
        save();
    }
}