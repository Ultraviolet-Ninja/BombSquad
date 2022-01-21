module core.bomb {
    requires com.opencsv;
    requires org.jgrapht.core;
    requires org.jetbrains.annotations;
    requires javafx.controls;
    requires javafx.fxml;

    exports core.bomb;
    exports core.bomb.enumerations;
    exports core.bomb.tools.filter;

    opens core.bomb;
    opens core.bomb.enumerations;
    opens core.bomb.tools.filter;
}