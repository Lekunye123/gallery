module com.example.gallery {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gallery to javafx.fxml;
    exports com.example.gallery;
}