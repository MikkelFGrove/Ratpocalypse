module dk.lima.ratpocalypse {
    requires javafx.controls;
    requires javafx.fxml;


    opens dk.lima.ratpocalypse to javafx.fxml;
    exports dk.lima.ratpocalypse;
}