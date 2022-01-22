module bomb {
    requires com.jfoenix;
    requires core.bomb;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires org.jetbrains.annotations;
    requires utility;

    exports bomb;
    opens bomb to javafx.fxml;
}