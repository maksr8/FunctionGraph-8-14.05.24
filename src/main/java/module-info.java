module org.example.functiongraph_8_140524 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.jfree.jfreechart;
    requires org.jfree.chart.fx;
    requires java.desktop;

    opens org.example.functiongraph_8_140524 to javafx.fxml;
    exports org.example.functiongraph_8_140524;
}