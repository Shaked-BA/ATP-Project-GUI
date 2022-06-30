module View {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires ATP.Project.PartB;


    opens View to javafx.fxml;
    exports View;
}