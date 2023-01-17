import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import java.lang.NumberFormatException;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

public class MatrixDetGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage stage) {
        Label label = new Label("Enter matrix sixe (1 - 10): ");
        label.setFont(new Font(13));
        TextField matrixSizeField = new TextField();
        matrixSizeField.setMaxSize(50, 50);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        Label answer = new Label();
        Button matrixSize = new Button("Set Matrix");
        VBox process = new VBox();
        process.setSpacing(10);
        Button calculate = new Button("Calculate Determinant");
        Button reset = new Button("Reset");

        matrixSize.setOnAction(e -> {
            try {
                grid.getChildren().clear();
                process.getChildren().clear();
                answer.setText("");
                int size = Integer.parseInt(matrixSizeField.getCharacters().toString());
                if (size < 1 || size > 10) {
                    throw (new NumberFormatException("Number out of bounds"));
                }
                for(int i = 0; i < size; i++) {
                    for(int j = 0; j < size; j++) {
                        TextField temp = new TextField();
                        temp.setMaxWidth(50);
                        grid.add(temp, j, i);
                    }
                }
                calculate.setOnAction(er -> {
                     try {
                        int[][] matrix = new int[grid.getColumnCount()][grid.getRowCount()];
                        for (int i = 0; i < matrix.length; i++) {
                            for (int j = 0; j < matrix[i].length; j++) {
                                matrix[i][j] = Integer.parseInt(((TextField)(grid.getChildren().get((i * matrix.length) + j))).getCharacters().toString());
                            }
                        }
                        int determinant = MatrixOperations.det(matrix, 0);
                        answer.setText("Determinant: " + determinant);
                     } catch (NumberFormatException nfe) {
                        Alert nfeAlert = new Alert(Alert.AlertType.ERROR);
                        nfeAlert.setTitle("Invalid input");
                        nfeAlert.setHeaderText("Error: incorrect number format");
                        nfeAlert.setContentText("Please fill all areas with numbers");
                        nfeAlert.showAndWait();
                     }
                });
                process.getChildren().addAll(calculate, answer, reset);
            } catch (NumberFormatException nfe) {
                Alert nfeAlert = new Alert(Alert.AlertType.ERROR);
                nfeAlert.setTitle("Invalid Input");
                if (nfe.getMessage().equals("Number out of bounds")) {
                    nfeAlert.setHeaderText(nfe.getMessage());
                } else {
                    nfeAlert.setHeaderText("Error: incorrect number format");
                }
                nfeAlert.setContentText("Please enter a number between 2 and 10");
                nfeAlert.showAndWait();
            }
        });

        reset.setOnAction(e -> {
            grid.getChildren().clear();
            process.getChildren().clear();
            answer.setText("");
        });

        HBox chooseSize = new HBox(label, matrixSizeField);
        chooseSize.setAlignment(Pos.TOP_LEFT);
        chooseSize.setSpacing(10);

        VBox root = new VBox(chooseSize, matrixSize, grid, process);
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Matrix Determinant Calculator");
        stage.setScene(scene);
        stage.show();
    }
}