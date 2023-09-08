module com.example.calculate {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.calculate to javafx.fxml;
    exports com.example.calculate;
}