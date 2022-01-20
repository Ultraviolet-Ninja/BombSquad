module core.bomb {
    requires com.opencsv;
    requires org.jetbrains.annotations;
//    requires javafx.controls;
//    requires javafx.fxml;

    exports core.bomb;
    exports core.bomb.enumerations;

    opens core.bomb;
    opens core.bomb.enumerations;
}