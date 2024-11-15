module topic_2_ledger_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swt;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;

    opens topic_2_ledger_system to javafx.fxml;
    exports topic_2_ledger_system;
}
