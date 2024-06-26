package org.example.functiongraph_8_140524;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JavaFX App for plotting and saving the graph of the parametric function:
 * x = +-sqrt(|A*cos(Bt)*cos(t) + C|)
 * y = +-sqrt(|A*cos(Bt)*sin(t) + C|)
 * where A, B, C are parameters, t is a parameter from the range [from, to] with a step.
 * The graph is saved as a PNG file.
 * The app uses JFreeChart library for plotting the graph.
 * The app is implemented using JavaFX.
 * Created for educational practice task v8
 *
 * @author Розумєй Максим Віталійович
 */
public class FunctionGraph extends Application {
    private Spinner<Double> spinnerA;
    private Spinner<Double> spinnerB;
    private Spinner<Double> spinnerC;
    private Spinner<Double> spinnerFrom;
    private Spinner<Double> spinnerTo;
    private Spinner<Double> spinnerStep;
    private JFreeChart chart;
    private Button saveButton;

    public static void main(String[] args) {
        launch();
    }

    /**
     * Start the JavaFX application
     * @param stage Stage
     */
    @Override
    public void start(Stage stage) {
        Scene scene = getScene();
        stage.setTitle("Parametric Function Graph");
        stage.setScene(scene);
        stage.setMinHeight(200);
        stage.setMinWidth(500);
        parametersChangedEvent();
        stage.show();
    }

    /**
     * Create the main scene of the application
     * @return Scene
     */
    private Scene getScene() {
        Label functionLabel = new Label("x = +-sqrt(|A*cos(Bt)*cos(t) + C|)\ny = +-sqrt(|A*cos(Bt)*sin(t) + C|)");
        functionLabel.setFont(Font.font("Times New Roman", 20.0));
        functionLabel.setWrapText(true);
        functionLabel.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

        Label labelParam = new Label("Parameters:");
        labelParam.setFont(Font.font("Times New Roman", 17.0));

        Label labelA = new Label("A");
        spinnerA = new Spinner<>(-100.0, 100.0, 3.0, 0.1);
        GridPane gridPaneA = getGridPaneForLabel(labelA, spinnerA);

        Label labelB = new Label("B");
        spinnerB = new Spinner<>(-100.0, 100.0, 2.0, 0.01);
        GridPane gridPaneB = getGridPaneForLabel(labelB, spinnerB);

        Label labelC = new Label("C");
        spinnerC = new Spinner<>(-5.0, 5.0, 0.82, 0.03);
        GridPane gridPaneC = getGridPaneForLabel(labelC, spinnerC);

        Label labelRange = new Label("Range of t:");
        labelRange.setFont(Font.font("Times New Roman", 17.0));

        Label labelFrom = new Label("From");
        spinnerFrom = new Spinner<>(-100.0, 100.0, -5.0, 0.1);
        GridPane gridPaneFrom = getGridPaneForLabel(labelFrom, spinnerFrom);

        Label labelTo = new Label("To");
        spinnerTo = new Spinner<>(-100.0, 100.0, 5.0, 0.1);
        GridPane gridPaneTo = getGridPaneForLabel(labelTo, spinnerTo);

        Label labelStep = new Label("Step/1000");
        spinnerStep = new Spinner<>(1, 10000.0, 10, 1);
        GridPane gridPaneStep = getGridPaneForLabel(labelStep, spinnerStep);

        VBox vBox = new VBox(10, functionLabel, labelParam, gridPaneA, gridPaneB, gridPaneC, labelRange,
                gridPaneFrom, gridPaneTo, gridPaneStep);
        for (Node child : vBox.getChildren()) {
            VBox.setMargin(child, new Insets(0, 0, 0, 10));
        }

        saveButton = new Button("Save");
        saveButton.setFont(Font.font("Times New Roman", 20.0));
        HBox buttonBox = new HBox(saveButton);
        buttonBox.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        vBox.getChildren().add(spacer);
        vBox.getChildren().add(buttonBox);
        VBox.setMargin(buttonBox, new Insets(0, 0, 10, 0));
        saveButton.setOnAction(event -> {
            buttonClickedEvent();
        });

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
        
        createChart();
        ChartViewer chartViewer = new ChartViewer(chart);

        vBox.setBackground(Background.fill(new Color(0.5, 0.9, 0.9, 0.5)));
        BorderPane borderPane1 = new BorderPane();
        borderPane1.setCenter(chartViewer);
        borderPane1.setRight(scrollPane);
        borderPane1.setBackground(Background.fill(new Color(0.6, 0.8, 0.6, 0.5)));

        return new Scene(borderPane1, 960, 700);
    }

    /**
     * Event handler for the Save button
     * Save the chart as a PNG file
     * Show an alert dialog with the result
     * Uses JFreeChart ChartUtils to save the chart
     * Uses Date to get the current date and time for the file name
     */
    private void buttonClickedEvent() {
        System.out.println("A = " + spinnerA.getValue());
        System.out.println("B = " + spinnerB.getValue());
        System.out.println("C = " + spinnerC.getValue());
        System.out.println("From = " + spinnerFrom.getValue());
        System.out.println("To = " + spinnerTo.getValue());
        System.out.println("Step = " + spinnerStep.getValue());

        try {
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
            String currentDateTime = dateFormat.format(currentDate);
            File outputFile = new File("graph8_"+ currentDateTime + ".png");
            if(outputFile.createNewFile()){
                ChartUtils.saveChartAsPNG(outputFile, chart, 960, 1000);
                System.out.println("Chart saved to " + outputFile.getAbsolutePath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Success!");
                alert.setContentText("Chart saved to " + outputFile.getAbsolutePath());
                alert.showAndWait();
            } else {
                System.out.println("File already exists.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("File already exists.");
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Create the line chart with the default dataset and author subtitle
     */
    private void createChart() {
        XYSeries series = new XYSeries("Example Series");
        XYDataset dataset = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart("Example Chart", "X-Axis", "Y-Axis", dataset);
        chart.addSubtitle(new TextTitle("Author: Maksym Rozumiei", new java.awt.Font("Arial",
                java.awt.Font.PLAIN, 12)));
    }

    /**
     * Create a GridPane with a Label and a Spinner
     * @param label Label
     * @param spinner Spinner
     * @return GridPane
     */
    private GridPane getGridPaneForLabel(Label label, Spinner<Double> spinner) {
        label.setFont(Font.font("Times New Roman", 15.0));
        GridPane.setHalignment(label, HPos.CENTER);
        label.setLabelFor(spinner);
        spinner.setEditable(true);
        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Spinner value changed from " + oldValue + " to " + newValue);
            parametersChangedEvent();
        });
        GridPane gridPane = new GridPane();
        gridPane.add(label, 0, 0);
        gridPane.add(spinner, 1, 0);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column1);
        return gridPane;
    }

    /**
     * Event handler for the Spinner value change
     * Rebuild the chart with the new parameters
     * Disable the Save button while the chart is being built
     */
    private void parametersChangedEvent() {
        saveButton.setDisable(true);
        buildChart();
        saveButton.setDisable(false);
    }

    /**
     * Build the chart with the current parameters
     * Set the title of the chart with the current parameters
     */
    private void buildChart() {
        double a = spinnerA.getValue();
        double b = spinnerB.getValue();
        double c = spinnerC.getValue();
        double from = spinnerFrom.getValue();
        double to = spinnerTo.getValue();
        double step = spinnerStep.getValue()/1000.0;

        chart.setTitle("x = +-sqrt(|"+a+"*cos("+b+"t)*cos(t) + ("+c+")|)\n" +
                "y = +-sqrt(|"+a+"*cos("+b+"t)*sin(t) + ("+c+")|)\n" +
                "t from " + from + " to " + to + " with step " + step);

        XYSeries series1 = new XYSeries("Generated Series 1", false, true);
        XYSeries series2 = new XYSeries("Generated Series 2", false, true);
        XYSeries series3 = new XYSeries("Generated Series 3", false, true);
        XYSeries series4 = new XYSeries("Generated Series 4", false, true);
        for (double t = from; t <= to; t += step) {
            double x = Math.sqrt(Math.abs(a * Math.cos(b * t) * Math.cos(t) + c));
            double y = Math.sqrt(Math.abs(a * Math.cos(b * t) * Math.sin(t) + c));
            series1.add(x, y);
            series2.add(-x, y);
            series3.add(x, -y);
            series4.add(-x, -y);
        }

        XYDataset dataset = new XYSeriesCollection(series1);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDataset(0, dataset);
        plot.setDataset(1, new XYSeriesCollection(series2));
        plot.setDataset(2, new XYSeriesCollection(series3));
        plot.setDataset(3, new XYSeriesCollection(series4));
    }
}