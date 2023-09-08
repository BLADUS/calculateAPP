package com.example.calculate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {

    private TextField inputField;
    private String currentInput = "";
    private String operator = "";
    private double result = 0.0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Калькулятор");

        inputField = new TextField();
        inputField.setEditable(false);
        inputField.setPrefColumnCount(10);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        String[][] buttonLabels = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"0", "C", "=", "+"}
        };

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Button button = new Button(buttonLabels[row][col]);
                button.setPrefSize(50, 50);
                button.setOnAction(new ButtonClickHandler());
                grid.add(button, col, row);
            }
        }

        grid.add(inputField, 0, 4, 4, 1);

        Scene scene = new Scene(grid, 250, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonClickHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Button clickedButton = (Button) event.getSource();
            String buttonText = clickedButton.getText();

            if (buttonText.matches("[0-9]")) {
                currentInput += buttonText;
                inputField.setText(currentInput);
            } else if (buttonText.equals("C")) {
                currentInput = "";
                operator = "";
                result = 0.0;
                inputField.clear();
            } else if (buttonText.matches("[+\\-*/]")) {
                if (!currentInput.isEmpty()) {
                    operator = buttonText;
                    result = Double.parseDouble(currentInput);
                    currentInput = "";
                }
            } else if (buttonText.equals("=")) {
                if (!currentInput.isEmpty()) {
                    double secondOperand = Double.parseDouble(currentInput);
                    switch (operator) {
                        case "+":
                            result += secondOperand;
                            break;
                        case "-":
                            result -= secondOperand;
                            break;
                        case "*":
                            result *= secondOperand;
                            break;
                        case "/":
                            if (secondOperand != 0) {
                                result /= secondOperand;
                            } else {
                                inputField.setText("Ошибка");
                                return;
                            }
                            break;
                    }
                    inputField.setText(Double.toString(result));
                    currentInput = "";
                    operator = "";
                }
            }
        }
    }
}
