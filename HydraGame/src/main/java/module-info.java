module com.example.jawad2_assignment1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jawad2_assignment1 to javafx.fxml;
    exports com.example.jawad2_assignment1;
}