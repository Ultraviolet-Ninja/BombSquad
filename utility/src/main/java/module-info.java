module utility {
    requires com.opencsv;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires org.jetbrains.annotations;

    exports tools;
    exports tools.data.structures.graph;
    exports tools.data.structures.queue;
    exports tools.data.structures.ring;
    exports tools.data.structures.trie;
    exports tools.event;
    exports tools.filter;
    exports tools.logic;
    exports tools.number;
    exports tools.pattern.facade;
    exports tools.pattern.factory;
    exports tools.string;
}