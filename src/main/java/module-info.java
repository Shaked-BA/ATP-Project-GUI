module com.example.atpprojectgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires ATPProjectJAR;


    opens com.example.atpprojectgui to javafx.fxml;
    exports com.example.atpprojectgui;
    exports View;
    opens View to javafx.fxml;
}