module com.example.atpprojectgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.atpprojectgui to javafx.fxml;
    exports com.example.atpprojectgui;
}