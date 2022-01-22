module core.bomb {
    requires com.opencsv;
    requires org.jgrapht.core;
    requires org.jetbrains.annotations;
    requires javafx.controls;
    requires javafx.fxml;
    requires javatuples;
    requires utility;

    exports core.bomb;
    exports core.bomb.abstractions;
    exports core.bomb.enumerations;
    exports core.bomb.modules.dh.hexamaze;
    exports core.bomb.modules.dh.hexamaze.hexalgorithm.storage;
    exports core.bomb.modules.s.simon;
}