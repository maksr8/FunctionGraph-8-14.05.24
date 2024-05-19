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
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Override
    public void start(Stage stage) {
        Scene scene = getScene();
        stage.setTitle("Parametric Function Graph");
        stage.setScene(scene);
        stage.setMinHeight(200);
        stage.setMinWidth(500);
        stage.show();
    }

    private Scene getScene() {
        Label functionLabel = new Label("x = +-sqrt(A*cos(Bt)*cos(t) + C)\ny = +-sqrt(A*cos(Bt)*sin(t) + C)");
        functionLabel.setFont(Font.font("Times New Roman", 20.0));
        functionLabel.setWrapText(true);
        functionLabel.setMaxSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

        Label labelParam = new Label("Parameters:");
        labelParam.setFont(Font.font("Times New Roman", 17.0));

        Label labelA = new Label("A");
        spinnerA = new Spinner<>(-100.0, 100.0, 3.0, 0.1);
        GridPane gridPaneA = getGridPaneForLabel(labelA, spinnerA);

        Label labelB = new Label("B");
        spinnerB = new Spinner<>(-100.0, 100.0, 2.0, 0.1);
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

        Label labelStep = new Label("Step");
        spinnerStep = new Spinner<>(0.1, 10.0, 0.1, 0.1);
        GridPane gridPaneStep = getGridPaneForLabel(labelStep, spinnerStep);

        VBox vBox = new VBox(10, functionLabel, labelParam, gridPaneA, gridPaneB, gridPaneC, labelRange, gridPaneFrom, gridPaneTo, gridPaneStep);
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
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        
        createChart();
        ChartViewer chartViewer = new ChartViewer(chart);

        vBox.setBackground(Background.fill(new Color(0.5, 0.9, 0.9, 0.5)));
        BorderPane borderPane1 = new BorderPane();
        borderPane1.setCenter(chartViewer);
        borderPane1.setRight(scrollPane);
        borderPane1.setBackground(Background.fill(new Color(0.6, 0.8, 0.6, 0.5)));

        return new Scene(borderPane1, 1000, 700);
    }

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
                ChartUtils.saveChartAsPNG(outputFile, chart, 800, 600);
                System.out.println("Chart saved to " + outputFile.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createChart() {
        XYSeries series = new XYSeries("Example Series");
        series.add(1.0, 1.0);
        series.add(2.0, 2.0);
        series.add(3.0, 3.0);
        XYDataset dataset = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart("Example Chart", "X-Axis", "Y-Axis", dataset);
    }

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

    private void parametersChangedEvent() {
        saveButton.setDisable(true);
        setChartText();
        buildChart();
        saveButton.setDisable(false);
    }

    private void buildChart() {
    }

    private void setChartText() {
        chart.setTitle("This is some text information");
    }
}