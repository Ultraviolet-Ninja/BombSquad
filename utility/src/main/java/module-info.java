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

    opens tools;
    opens tools.data.structures.graph;
    opens tools.data.structures.queue;
    opens tools.data.structures.ring;
    opens tools.data.structures.trie;
    opens tools.event;
    opens tools.filter;
    opens tools.logic;
    opens tools.number;
    opens tools.pattern.facade;
    opens tools.pattern.factory;
    opens tools.string;
}