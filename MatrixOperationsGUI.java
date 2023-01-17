import javafx.application.Application;
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
import javafx.scene.control.ComboBox;
import javafx.geometry.Pos;

public class MatrixOperationsGUI extends Application {

    /**
     * This is the main method that launches the application
     * @param args a String array
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method houses all the components that run the application
     */
    public void start(Stage stage) {
        //operationSelectionBox contains a drop down selection of the various possible operations
        ComboBox<String> operationSelectionBox = new ComboBox<>();
        operationSelectionBox.getItems().addAll("Determinant", "Add", "Subtract", "Multiply");
        operationSelectionBox.setMaxWidth(130);
        operationSelectionBox.setPromptText("Select Operation");

        //firstContinuationBox contains the nodes returned from calling the correct operation method
        VBox firstContinuationBox = new VBox();

        //operationSelectBtn will add the appropriate method to firstContinuationBox when pressed
        Button operationSelectBtn = new Button("Select");

        //selectOperationBox houses the components that together ask the user to choose an operation
        HBox selectOperationBox = new HBox(operationSelectionBox, operationSelectBtn);
        selectOperationBox.setPadding(new Insets(10));
        selectOperationBox.setSpacing(10);

        operationSelectBtn.setOnAction(e -> {
            firstContinuationBox.getChildren().clear();

            //Depending on the operation selected, the appropriate method will be called and added to firstContinuationBox
            switch (operationSelectionBox.getValue()) {
                case "Determinant":
                    firstContinuationBox.getChildren().add(determinantGUI());
                    break;
                case "Add":
                    firstContinuationBox.getChildren().add(sumGUI());
                    break;
                case "Subtract":
                    firstContinuationBox.getChildren().add(differenceGUI());
                    break;
                case "Multiply":
                    firstContinuationBox.getChildren().add(productGUI());
                    break;
                default:
                    break;
            }
        });

        //root contains all the nodes that will be set to the scene
        VBox root = new VBox(selectOperationBox, firstContinuationBox);
        Scene scene = new Scene(root, 700, 700);
        stage.setTitle("Matrix Operations Calculator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method implements the GUI aspects needed to set a new matrix from user input
     * @param isSquare a boolean indicating whether the matrix to be created is square
     * @param continuation a VBox of the GUI components that will be made visible once the matrix is created
     * @return a VBox of the GUI Components needed
     */
    public static VBox setMatrix(boolean isSquare, VBox continuation) {
        //These TextFields will hold the user input of the matrix dimensions
        TextField matrixSizeFld = new TextField();
        TextField numRowsFld = new TextField();
        TextField numColsFld = new TextField();

        //setMatrixBtn will display a GridPane of TextFields corresponding to the dimensions input
        Button setMatrixBtn = new Button("Set Matrix");

        //These boxes house the Labels and TextFields that together ask the user for the matrix dimensions
        HBox enterSizeBox = new HBox();
        HBox enterColsBox = new HBox();

        //This is the VBox that will be returned
        VBox setMatrixBox = new VBox();
        setMatrixBox.setSpacing(5);

        //The user will be prompted differently depending on if the matrix to be created is square
        if (isSquare) {
            Label enterSizeLabel = new Label("Enter matrix sixe (1 - 10): ");
            enterSizeLabel.setFont(new Font(13));
            matrixSizeFld.setMaxWidth(25);
            enterSizeBox.getChildren().addAll(enterSizeLabel, matrixSizeFld);
            setMatrixBox.getChildren().add(enterSizeBox);
        } else {
            Label enterRowsLabel = new Label("Enter the number of rows (1-10): ");
            Label enterColsLabel = new Label("Enter the number of columns (1-10): ");
            enterRowsLabel.setFont(new Font(13));
            enterColsLabel.setFont(new Font(13));
            numColsFld.setMaxWidth(25);
            numRowsFld.setMaxWidth(25);
            enterSizeBox.getChildren().addAll(enterRowsLabel, numRowsFld);
            enterColsBox.getChildren().addAll(enterColsLabel, numColsFld);
            setMatrixBox.getChildren().addAll(enterSizeBox, enterColsBox);
        }

        //grid is a GridPane of textFields that will be displayed once setMatrixBtn is pressed
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        //This button adds TextFields to grid according to the dimensions input
        setMatrixBtn.setOnAction(e -> {
            try {
                grid.getChildren().clear();
                int rows, cols;
                if (isSquare) {
                    rows = Integer.parseInt(matrixSizeFld.getCharacters().toString());
                    cols = rows;
                } else {
                    rows = Integer.parseInt(numRowsFld.getCharacters().toString());
                    cols = Integer.parseInt(numColsFld.getCharacters().toString());
                }
                if (rows < 1 || rows > 10 || cols < 1 || cols > 10) {
                    throw (new NumberFormatException("Number out of bounds"));
                }
                for(int i = 0; i < rows; i++) {
                    for(int j = 0; j < cols; j++) {
                        TextField temp = new TextField();
                        temp.setMaxWidth(35);
                        grid.add(temp, j, i);
                    }
                }
                //continuation contains the various other nodes that will be displayed once setMatrixBtn is pressed
                continuation.setVisible(true);
            } catch (NumberFormatException nfe) {
                Alert nfeAlert = new Alert(Alert.AlertType.ERROR);
                nfeAlert.setTitle("Invalid Input");
                if (nfe.getMessage().equals("Number out of bounds")) {
                    nfeAlert.setHeaderText(nfe.getMessage());
                } else {
                    nfeAlert.setHeaderText("Error: incorrect number format");
                }
                nfeAlert.setContentText("Please enter a number between 1 and 10");
                nfeAlert.showAndWait();
            }
        });
        setMatrixBox.getChildren().addAll(setMatrixBtn, grid);
        return setMatrixBox;
    }

    /**
     * This method implements the GUI aspects needed to input and calculate the determinant of a matrix
     * @return a VBox of the GUI components needed
     */
    public static VBox determinantGUI() {
        //processBox is the VBox that will be returned
        VBox processBox = new VBox();
        processBox.setPadding(new Insets(10));
        processBox.setSpacing(10);

        //continuationBox contains the nodes that are to be shown when setMatrixBtn is pressed
        VBox continuationBox = new VBox();
        continuationBox.setSpacing(10);
        continuationBox.setVisible(false);

        //setMatrixBox contains the nodes received from the call setMatrix
        VBox setMatrixBox = setMatrix(true, continuationBox);

        //matrixGrd is the GridPane of TextFields pulled from the setMatrixBox
        GridPane matrixGrd = (GridPane)(setMatrixBox.getChildren().get(2));

        //answerLbl is the label that will display the result
        Label answerLbl = new Label();

        //calculateBtn will calculate the determinant when pressed
        Button calculateBtn = new Button("Calculate Determinant");

        //resetBtn will reset the GUI to before setMatrixBtn is pressed
        Button resetBtn = new Button("Reset");
        
        continuationBox.getChildren().addAll(calculateBtn, answerLbl, resetBtn);
        processBox.getChildren().addAll(setMatrixBox, continuationBox);

        //This button sets the matrixGrd to a 2D int array which is then used to calculate the determinant and display the answer
        calculateBtn.setOnAction(e -> {
             try {
                int[][] matrix = new int[matrixGrd.getColumnCount()][matrixGrd.getRowCount()];
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        matrix[i][j] = Integer.parseInt(((TextField)(matrixGrd.getChildren().get((i * (matrix[i].length)) + j))).getCharacters().toString());
                    }
                }
                int determinant = Operations.determinant(matrix, 0);
                answerLbl.setText("Determinant: " + determinant);
             } catch (NumberFormatException nfe) {
                Alert nfeAlert = new Alert(Alert.AlertType.ERROR);
                nfeAlert.setTitle("Invalid input");
                nfeAlert.setHeaderText("Error: incorrect number format");
                nfeAlert.setContentText("Please fill all areas with numbers");
                nfeAlert.showAndWait();
             }
        });

        //This button clears matrixGrd and sets the continuationBox visibility to false
        resetBtn.setOnAction(e -> {
            matrixGrd.getChildren().clear();
            continuationBox.setVisible(false);
            answerLbl.setText("");
        });
        return processBox;
    }
    
    /**
     * This method implements the GUI aspects needed to input and calculate the sum of two matrices
     * @return a VBox of the GUI components needed
     */
    public static VBox sumGUI() {
        //processBox is the box that will be returned
        VBox processBox = new VBox();
        processBox.setSpacing(10);
        processBox.setPadding(new Insets(10));

        //continuationBox is the box that will be made visible when setMatrixBtn is pressed
        VBox continuationBox = new VBox();
        continuationBox.setSpacing(10);
        continuationBox.setVisible(false);

        //setMatricesBox is a box that holds the nodes from the call to setMatrix
        HBox setMatricesBox = new HBox(setMatrix(false, continuationBox), setMatrix(false, continuationBox));
        setMatricesBox.setSpacing(20);

        //Adding labels to the setMatricesBox for readability
        ((VBox)setMatricesBox.getChildren().get(0)).getChildren().add(0, new Label("Matrix 1:"));
        ((VBox)setMatricesBox.getChildren().get(1)).getChildren().add(0, new Label("Matrix 2:"));
        ((Label)((VBox)setMatricesBox.getChildren().get(0)).getChildren().get(0)).setAlignment(Pos.CENTER);
        ((Label)((VBox)setMatricesBox.getChildren().get(1)).getChildren().get(0)).setAlignment(Pos.CENTER);
        ((VBox)setMatricesBox.getChildren().get(0)).setSpacing(5);
        ((VBox)setMatricesBox.getChildren().get(1)).setSpacing(5);

        //These GridPanes hold the textFields from setMatricesBox
        GridPane matrix1Grd = (GridPane)((VBox)(setMatricesBox.getChildren().get(0))).getChildren().get(4);
        GridPane matrix2Grd = (GridPane)((VBox)(setMatricesBox.getChildren().get(1))).getChildren().get(4);

        //calculateSumBtn will calculate the sum when pressed
        Button calculateSumBtn = new Button("Calculate Sum");

        //resetBtn will reset the GUI to before setMatrixBtn is pressed
        Button resetBtn = new Button("Reset");

        //answerBox will hold the nodes that together display the result
        HBox answerBox = new HBox();
        answerBox.setSpacing(5);

        continuationBox.getChildren().addAll(calculateSumBtn, resetBtn);
        processBox.getChildren().addAll(setMatricesBox, continuationBox);

        //This button will set the matrices to 2D arrays which are used to calculate the answer and display it
        calculateSumBtn.setOnAction(e -> {
            try {
                //This check makes sure a duplicate answerBox is not added to processBox if calculate is pressed a second time
                if (processBox.getChildren().contains(answerBox)) {
                    processBox.getChildren().remove(2);
                }

                answerBox.getChildren().clear();

                //This checks to make sure the matrices are of appropriate dimensions and can be added
                if (matrix1Grd.getColumnCount() != matrix2Grd.getColumnCount() || matrix1Grd.getRowCount() != matrix2Grd.getRowCount()) {
                    throw (new NumberFormatException("Cannot calculate sum with given dimensions"));
                }
                
                //These 2D arrays hold the values pulled from the textField GridPanes
                int[][] matrix1 = new int[matrix1Grd.getRowCount()][matrix1Grd.getColumnCount()];
                for (int i = 0; i < matrix1.length; i++) {
                    for (int j = 0; j < matrix1[i].length; j++) {
                        matrix1[i][j] = Integer.parseInt(((TextField)(matrix1Grd.getChildren().get(((i * (matrix1[i].length))) + j))).getCharacters().toString());
                    }
                }
                int[][] matrix2 = new int[matrix2Grd.getRowCount()][matrix2Grd.getColumnCount()];
                for (int i = 0; i < matrix2.length; i++) {
                    for (int j = 0; j < matrix2[i].length; j++) {
                        matrix2[i][j] = Integer.parseInt(((TextField)(matrix2Grd.getChildren().get((i * (matrix2[i].length)) + j))).getCharacters().toString());
                    }
                }
                
                int[][] sumAsMatrix = Operations.sum(matrix1, matrix2);
                answerBox.getChildren().addAll(new Label("Sum: "), displayMatrix(sumAsMatrix));
                processBox.getChildren().add(answerBox);
             } catch (NumberFormatException nfe) {
                Alert nfeAlert = new Alert(Alert.AlertType.ERROR);
                if (nfe.getMessage().equals("Cannot calculate sum with given dimensions")) {
                    nfeAlert.setHeaderText("Error: incompatible dimensions");
                    nfeAlert.setContentText("Matrices of these dimensions canot be added");
                } else {
                    nfeAlert.setHeaderText("Error: incorrect number format");
                    nfeAlert.setContentText("Please fill all areas with numbers");
                }
                nfeAlert.setTitle("Invalid input");
                nfeAlert.showAndWait();
             }
        });

        //resetBtn clears the GridPanes, the answerBox, and sets the continuationBox visibility to false
        resetBtn.setOnAction(e -> {
          matrix1Grd.getChildren().clear();  
          matrix2Grd.getChildren().clear();  
          answerBox.getChildren().clear();
          continuationBox.setVisible(false);
        });

        return processBox;
    }

    /**
     * This method implements the GUI aspects needed to input and calculate the difference of two matrices
     * @return a VBox of the GUI components needed
     */
    public static VBox differenceGUI() {
        //Implementation is the same as sumGUI except for variable names and the Operations call
        VBox processBox = new VBox();
        processBox.setSpacing(10);
        processBox.setPadding(new Insets(10));

        VBox continuationBox = new VBox();
        continuationBox.setSpacing(10);
        continuationBox.setVisible(false);

        HBox setMatricesBox = new HBox(setMatrix(false, continuationBox), setMatrix(false, continuationBox));
        setMatricesBox.setSpacing(20);

        ((VBox)setMatricesBox.getChildren().get(0)).getChildren().add(0, new Label("Matrix 1:"));
        ((VBox)setMatricesBox.getChildren().get(1)).getChildren().add(0, new Label("Matrix 2:"));
        ((Label)((VBox)setMatricesBox.getChildren().get(0)).getChildren().get(0)).setAlignment(Pos.CENTER);
        ((Label)((VBox)setMatricesBox.getChildren().get(1)).getChildren().get(0)).setAlignment(Pos.CENTER);
        ((VBox)setMatricesBox.getChildren().get(0)).setSpacing(5);
        ((VBox)setMatricesBox.getChildren().get(1)).setSpacing(5);

        GridPane matrix1Grd = (GridPane)((VBox)(setMatricesBox.getChildren().get(0))).getChildren().get(4);
        GridPane matrix2Grd = (GridPane)((VBox)(setMatricesBox.getChildren().get(1))).getChildren().get(4);

        Button calculateDifferenceBtn = new Button("Calculate Difference");
        Button resetBtn = new Button("Reset");
        HBox answerBox = new HBox();
        answerBox.setSpacing(5);

        continuationBox.getChildren().addAll(calculateDifferenceBtn, resetBtn);
        processBox.getChildren().addAll(setMatricesBox, continuationBox);

        calculateDifferenceBtn.setOnAction(e -> {
            try {
                if (processBox.getChildren().contains(answerBox)) {
                    processBox.getChildren().remove(2);
                }
                answerBox.getChildren().clear();

                if (matrix1Grd.getColumnCount() != matrix2Grd.getColumnCount() || matrix1Grd.getRowCount() != matrix2Grd.getRowCount()) {
                    throw (new NumberFormatException("Cannot calculate difference with given dimensions"));
                }

                int[][] matrix1 = new int[matrix1Grd.getRowCount()][matrix1Grd.getColumnCount()];
                for (int i = 0; i < matrix1.length; i++) {
                    for (int j = 0; j < matrix1[i].length; j++) {
                        matrix1[i][j] = Integer.parseInt(((TextField)(matrix1Grd.getChildren().get(((i * (matrix1[i].length))) + j))).getCharacters().toString());
                    }
                }
                int[][] matrix2 = new int[matrix2Grd.getRowCount()][matrix2Grd.getColumnCount()];
                for (int i = 0; i < matrix2.length; i++) {
                    for (int j = 0; j < matrix2[i].length; j++) {
                        matrix2[i][j] = Integer.parseInt(((TextField)(matrix2Grd.getChildren().get((i * (matrix2[i].length)) + j))).getCharacters().toString());
                    }
                }
                int[][] differenceAsMatrix = Operations.difference(matrix1, matrix2);

                answerBox.getChildren().addAll(new Label("Difference: "), displayMatrix(differenceAsMatrix));
                processBox.getChildren().add(answerBox);
             } catch (NumberFormatException nfe) {
                Alert nfeAlert = new Alert(Alert.AlertType.ERROR);
                if (nfe.getMessage().equals("Cannot calculate difference with given dimensions")) {
                    nfeAlert.setHeaderText("Error: incompatible dimensions");
                    nfeAlert.setContentText("Matrices of these dimensions canot be subtracted");
                } else {
                    nfeAlert.setHeaderText("Error: incorrect number format");
                    nfeAlert.setContentText("Please fill all areas with numbers");
                }
                nfeAlert.setTitle("Invalid input");
                nfeAlert.showAndWait();
             }
        });
        
        resetBtn.setOnAction(e -> {
            matrix1Grd.getChildren().clear();  
            matrix2Grd.getChildren().clear();  
            answerBox.getChildren().clear();
            continuationBox.setVisible(false);
          });

        return processBox;
    }

    /**
     * This method implements the GUI aspects needed to input and calculate the product of two matrices
     * @return a VBox of the GUI components needed
     */
    public static VBox productGUI() {
        //Implementation is the same as sumGUI and differenceGUI except for the variable names, Operations call, and the check for the appropriate matrix sizes
        VBox processBox = new VBox();
        processBox.setSpacing(10);
        processBox.setPadding(new Insets(10));

        VBox continuationBox = new VBox();
        continuationBox.setSpacing(10);
        continuationBox.setVisible(false);

        HBox setMatricesBox = new HBox(setMatrix(false, continuationBox), setMatrix(false, continuationBox));
        setMatricesBox.setSpacing(20);

        ((VBox)setMatricesBox.getChildren().get(0)).getChildren().add(0, new Label("Matrix 1:"));
        ((VBox)setMatricesBox.getChildren().get(1)).getChildren().add(0, new Label("Matrix 2:"));
        ((Label)((VBox)setMatricesBox.getChildren().get(0)).getChildren().get(0)).setAlignment(Pos.CENTER);
        ((Label)((VBox)setMatricesBox.getChildren().get(1)).getChildren().get(0)).setAlignment(Pos.CENTER);
        ((VBox)setMatricesBox.getChildren().get(0)).setSpacing(5);
        ((VBox)setMatricesBox.getChildren().get(1)).setSpacing(5);

        GridPane matrix1Grd = (GridPane)((VBox)(setMatricesBox.getChildren().get(0))).getChildren().get(4);
        GridPane matrix2Grd = (GridPane)((VBox)(setMatricesBox.getChildren().get(1))).getChildren().get(4);

        Button calculateProductBtn = new Button("Calculate Product");
        Button resetBtn = new Button("Reset");
        HBox answerBox = new HBox();
        answerBox.setSpacing(5);

        continuationBox.getChildren().addAll(calculateProductBtn, resetBtn);
        processBox.getChildren().addAll(setMatricesBox, continuationBox);

        calculateProductBtn.setOnAction(e -> {
            try {
                if (processBox.getChildren().contains(answerBox)) {
                    processBox.getChildren().remove(2);
                }
                answerBox.getChildren().clear();

                if (matrix1Grd.getColumnCount() != matrix2Grd.getRowCount()) {
                    throw (new NumberFormatException("Cannot calculate product with given dimensions"));
                }
                int[][] matrix1 = new int[matrix1Grd.getRowCount()][matrix1Grd.getColumnCount()];
                for (int i = 0; i < matrix1.length; i++) {
                    for (int j = 0; j < matrix1[i].length; j++) {
                        matrix1[i][j] = Integer.parseInt(((TextField)(matrix1Grd.getChildren().get(((i * (matrix1[i].length))) + j))).getCharacters().toString());
                    }
                }
                int[][] matrix2 = new int[matrix2Grd.getRowCount()][matrix2Grd.getColumnCount()];
                for (int i = 0; i < matrix2.length; i++) {
                    for (int j = 0; j < matrix2[i].length; j++) {
                        matrix2[i][j] = Integer.parseInt(((TextField)(matrix2Grd.getChildren().get((i * (matrix2[i].length)) + j))).getCharacters().toString());
                    }
                }
                int[][] productAsMatrix = Operations.product(matrix1, matrix2);

                answerBox.getChildren().addAll(new Label("Product: "), displayMatrix(productAsMatrix));
                processBox.getChildren().add(answerBox);
             } catch (NumberFormatException nfe) {
                Alert nfeAlert = new Alert(Alert.AlertType.ERROR);
                if (nfe.getMessage().equals("Cannot calculate product with given dimensions")) {
                    nfeAlert.setHeaderText("Error: incompatible dimensions");
                    nfeAlert.setContentText("Matrices of these dimensions canot be added");
                } else {
                    nfeAlert.setHeaderText("Error: incorrect number format");
                    nfeAlert.setContentText("Please fill all areas with numbers");
                }
                nfeAlert.setTitle("Invalid input");
                nfeAlert.showAndWait();
             }
        });
        
        resetBtn.setOnAction(e -> {
            matrix1Grd.getChildren().clear();  
            matrix2Grd.getChildren().clear();  
            answerBox.getChildren().clear();
            continuationBox.setVisible(false);
          });

        return processBox;
    }

    /**
     * This method displays a 2D int array in a readable format of a matrix
     * @param matrix a 2d int array
     * @return an HBox of the components that display the matrix
     */
    public static HBox displayMatrix(int[][] matrix) {
        //These spacing boxes are meant as fillers that will be adjusted based on the size of the matrix, so it is displayed correctly
        VBox matrixSpacingBox = new VBox();
        VBox leftBracketSpacingBox = new VBox();
        VBox rightBracketSpacingBox = new VBox();
        matrixSpacingBox.setSpacing(matrix.length * 8.5);

        //These labels house the brackets that will surround the displayed matrix
        Label leftBracket = new Label("[");
        Label rightBracket = new Label("]");

        //The size of the brackets will be adjusted based on the number of rows of the matrix
        leftBracket.setFont(new Font(matrix.length * 23));
        rightBracket.setFont(new Font(matrix.length * 23));

        //matrixAsGrd will house the labels that are entries in the matrix
        GridPane matrixAsGrd = new GridPane();
        matrixAsGrd.setHgap(5);
        matrixAsGrd.setVgap(3);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Label temp = new Label("" + matrix[i][j]);
                matrixAsGrd.add(temp, j, i);
            }
        }

        //Labels are added to the spacingBoxes so that depending on the size of the matrix, the nodes will line up due to the adjusted spacing
        matrixSpacingBox.getChildren().addAll(new Label(""), matrixAsGrd);
        leftBracketSpacingBox.getChildren().addAll(new Label(""), leftBracket);
        rightBracketSpacingBox.getChildren().addAll(new Label(""), rightBracket);

        //displayMatrixBox puts the nodes into one box to be returned
        HBox displayMatrixBox = new HBox(leftBracketSpacingBox, matrixSpacingBox, rightBracketSpacingBox);
        displayMatrixBox.setSpacing(5);
        return displayMatrixBox;
    }

    /**
     * @author Palmer
     * @version 1.0
     * This inner class houses the methods used by MatrixOperationsGUI.java to calculate various matrix operations
     */
    static private class Operations {

        /**
         * The method called to find the determinant of a matrix, using recursion and cofactor expansion
         * Because of the nature of the cofactor expansion algorithm, It runs in O(n!) time
         * @param matrix the nxn matrix being analyzed
         * @param col the column being analyzed
         * @return an int
         */
        public static int determinant(int[][] matrix, int col) {

            //if the matrix is a 1x1 matrix, the determinant is the number itself
            if (matrix.length == 1) {
                return matrix[0][0];
            }

            //if the matrix is a 2x2 matrix, the determinant can be found using a simple algorithm and no further decomposition is needed
            if (matrix.length == 2) {
                return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            }

            //if the column being analyzed is the last column in the matrix, there is no need to call det again on the next column
            if (col == matrix.length-1) {
                return (int)Math.pow(-1, col+2) * matrix[0][col] * determinant(modifyMatrix(matrix, col), 0);
            }

            //else, returns the cofactor expansion algorithm for finding the determinant, adding each value using recursive calls iterating through each column
            return (int)Math.pow(-1, col+2) * matrix[0][col] * determinant(modifyMatrix(matrix, col), 0) + determinant(matrix, col+1);
        }
        
        /**
         * The method is a private method called by determinant to modify the matrix making it smaller
         * @param matrix the initial nxn matrix input
         * @param col the column and row being removed from the matrix
         * @return an (n-1)x(n-1) matrix
         */
        private static int[][] modifyMatrix(int[][] matrix, int col) {

            //creates a new 2d int array of dimensions (n-1)x(n-1)
            int[][] newMatrix = new int[matrix.length-1][matrix.length-1];
            int rowPointer = 0;
            int colPointer = 0;

            //sets the smaller matrix, eliminating the row of index 0 and column of index col
            for(int i = 1; i<matrix.length; i++) {
                for(int j = 0; j<matrix.length; j++) {
                    if (j == col) {
                        continue;
                    }
                    newMatrix[rowPointer][colPointer] = matrix[i][j];
                    colPointer++;
                }
                colPointer = 0;
                rowPointer++;
            }
            return newMatrix;
        }
        
        /**
         * This method calculates the difference of two matrices
         * @param matrix1 a 2D int array (first matrix) of dimension nxm
         * @param matrix2 a 2D int array (second matrix) of dimension nxm
         * @return the difference, a 2D int array of dimension nxm
         */
        public static int[][] difference(int[][] matrix1, int[][] matrix2) {
            int[][] difference = new int[matrix1.length][matrix1[0].length];
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1[i].length; j++) {
                    difference[i][j] = matrix1[i][j] - matrix2[i][j];
                }
            }
            return difference;
        }

        /**
         * This method calculates the sum of two matrices
         * @param matrix1 a 2D int array (first matrix) of dimension nxm
         * @param matrix2 a 2D int array (second matrix) of dimension nxm
         * @return the sum, a 2D int array of dimension nxm
         */
        public static int[][] sum(int[][] matrix1, int[][] matrix2) {
            int[][] sum = new int[matrix1.length][matrix1[0].length];
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1[i].length; j++) {
                    sum[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }
            return sum;
        }

        /**
         * This method calculates the product of two matrices
         * @param matrix1 a 2D int array (first matrix) of dimension nxm
         * @param matrix2 a 2D int array (second matrix) of dimension mxk
         * @return the product, a 2D int array of dimension nxk
         */
        public static int[][] product(int[][] matrix1, int[][] matrix2) {
            int[][] product = new int[matrix1.length][matrix2[0].length];
            for (int i = 0; i < matrix1.length; i++) {
                for(int j = 0; j < matrix2[0].length; j++) {
                    int num = 0;
                    for(int k = 0; k < matrix1[i].length; k++) {
                        num += matrix1[i][k] * matrix2[k][j];
                    }
                    product[i][j] = num;
                }
            }
            return product;
        }
    }
}