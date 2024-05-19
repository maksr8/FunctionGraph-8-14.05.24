module org.example.functiongraph_8_140524 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.functiongraph_8_140524 to javafx.fxml;
    exports org.example.functiongraph_8_140524;
}