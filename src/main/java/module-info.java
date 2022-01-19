module bomb {
    requires core.bomb;
    requires javafx.controls;
    requires javafx.fxml;

    exports bomb;
    opens bomb to javafx.fxml;
}