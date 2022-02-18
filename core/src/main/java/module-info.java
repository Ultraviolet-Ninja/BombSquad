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
    exports core.bomb.modules.ab.alphabet;
    exports core.bomb.modules.ab.astrology;
    exports core.bomb.modules.ab.bitwise;
    exports core.bomb.modules.ab.blind_alley;
    exports core.bomb.modules.ab.boolean_venn;
    exports core.bomb.modules.dh.hexamaze;
    exports core.bomb.modules.dh.hexamaze.hexalgorithm.storage;
    exports core.bomb.modules.s.simon;
}