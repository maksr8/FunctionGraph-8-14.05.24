package org.example.functiongraph_8_140524;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FunctionGraph extends Application {
    private TextField textFieldA = new TextField();
    private TextField textFieldB = new TextField();
    private TextField textFieldC = new TextField();
    private TextField textFieldFrom = new TextField();
    private TextField textFieldTo = new TextField();
    private TextField textFieldStep = new TextField();
    private Button saveButton = new Button("Save");

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = getScene();
        stage.setTitle("Parametric Function Graph");
        stage.setScene(scene);
        stage.show();
    }

    private Scene getScene() {
        Label functionLabel = new Label("x = +-sqrt(A*cos(Bt)*cos(t) + C)\ny = +-sqrt(A*cos(Bt)*sin(t) + C)");
        functionLabel.setFont(Font.font("Times New Roman", 20.0));
        functionLabel.setWrapText(true);

        Label labelParam = new Label("Parameters:");
        labelParam.setFont(Font.font("Times New Roman", 17.0));

        Label labelA = new Label("A");
        textFieldA = new TextField();
        GridPane gridPaneA = getGridPaneForLabel(labelA, textFieldA);

        Label labelB = new Label("B");
        textFieldB = new TextField();
        GridPane gridPaneB = getGridPaneForLabel(labelB, textFieldB);

        Label labelC = new Label("C");
        textFieldC = new TextField();
        GridPane gridPaneC = getGridPaneForLabel(labelC, textFieldC);

        Label labelRange = new Label("Range of t:");
        labelRange.setFont(Font.font("Times New Roman", 17.0));

        Label labelFrom = new Label("From");
        textFieldFrom = new TextField();
        GridPane gridPaneFrom = getGridPaneForLabel(labelFrom, textFieldFrom);

        Label labelTo = new Label("To");
        textFieldTo = new TextField();
        GridPane gridPaneTo = getGridPaneForLabel(labelTo, textFieldTo);

        Label labelStep = new Label("Step");
        textFieldStep = new TextField();
        GridPane gridPaneStep = getGridPaneForLabel(labelStep, textFieldStep);

        VBox vBox = new VBox(10, functionLabel, labelParam, gridPaneA, gridPaneB, gridPaneC, labelRange, gridPaneFrom, gridPaneTo, gridPaneStep);
        for (Node child : vBox.getChildren()) {
            VBox.setMargin(child, new Insets(0, 0, 0, 10));
        }

        saveButton.setFont(Font.font("Times New Roman", 20.0));
        HBox buttonBox = new HBox(saveButton);
        buttonBox.setAlignment(Pos.CENTER);
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        vBox.getChildren().add(spacer);
        vBox.getChildren().add(buttonBox);
        VBox.setMargin(buttonBox, new Insets(0, 0, 10, 0));

        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setBackground(Background.fill(new Color(0.5, 0.9, 0.9, 0.5)));
        BorderPane borderPane1 = new BorderPane();
        borderPane1.setRight(scrollPane);

        return new Scene(borderPane1, 1000, 700);
    }

    private GridPane getGridPaneForLabel(Label label, TextField textField) {
        label.setFont(Font.font("Times New Roman", 15.0));
        GridPane.setHalignment(label, HPos.CENTER);
        label.setLabelFor(textField);
        GridPane gridPane = new GridPane();
        gridPane.add(label, 0, 0);
        gridPane.add(textField, 1, 0);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column1);
        return gridPane;
    }
}