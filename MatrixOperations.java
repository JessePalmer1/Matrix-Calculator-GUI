/*
 * @author Palmer
 * @Version 1.0
 * This class implements multiple Matrix Operations
 */
import java.util.Arrays;
import java.util.Scanner;
import javafx.application.Application;
public class MatrixOperations {

/*
 * IDEAS FOR IMPROVEMENTS: MAKE THE CREATE MATRIX METHOD CREATE NON-SQUARE MATRICES AS WELL SO THAT YOU CAN MULTIPLY NON-SQUARE MATRICES
 */

    /**
     * Main method user uses to operate on matrices
     * @param args a String array
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter matrix operation (determinant, multiply, add, subtract): ");
        String operation = input.nextLine().toLowerCase();
        switch (operation) {
            case "determinant":
                Application.launch(MatrixDetGUI.class, args);
                break;
            case "multiply":
                Multiply();
                break;
            case "add":
                add();
                break;
            case "substract":
                subtract();
                break;
            default:
                break;
        }
        input.close();
    }

    /**
     * This method multiplies two matrices and prints the product
     * @return a 2D int array
     */
    public static int[][] Multiply() {
        int[][] matrix1 = createMatrix(false);
        int[][] matrix2 = createMatrix(false);
        if (matrix1[0].length != matrix2.length) {
            System.out.println("Cannot multiply matrices of these dimensions");
            return null;
        }
        int[][] product = new int[matrix1.length][matrix2[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for(int j = 0; j < matrix1.length; j++) {
                int num = 0;
                for(int k = 0; k < matrix1[i].length; k++) {
                    num += matrix1[i][k] * matrix2[k][j];
                }
                product[i][j] = num;
            }
        }
        System.out.println("Matrix 1: " +  Arrays.deepToString(matrix1)
            + "\nMatrix2: " + Arrays.deepToString(matrix2)
            + "\nProduct: " + Arrays.deepToString(product));
        return product;
    }

    /**
     * This method adds two matrices
     * @return a 2D int array
     */
    public static int[][] add() {
        int[][] matrix1 = createMatrix(false);
        int[][] matrix2 = createMatrix(false);
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            System.out.println("Cannot add matrices of these dimensions");
            return null;
        }
        int[][] sum = new int[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j ++) {
                sum[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        System.out.println("Matrix 1: " +  Arrays.deepToString(matrix1)
            + "\nMatrix2: " + Arrays.deepToString(matrix2)
            + "\nSum: " + Arrays.deepToString(sum));
        return sum;
    }
    
    /**
     * This method subtracts two matrices
     * @return a 2D int array
     */
    public static int[][] subtract() {
        int[][] matrix1 = createMatrix(false);
        int[][] matrix2 = createMatrix(false);
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            System.out.println("Cannot subtract matrices of these dimensions");
            return null;
        }
        int[][] difference = new int[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j ++) {
                difference[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        System.out.println("Matrix 1: " +  Arrays.deepToString(matrix1)
            + "\nMatrix2: " + Arrays.deepToString(matrix2)
            + "\ndifference: " + Arrays.deepToString(difference));
        return difference;
    }

    /**
     * This method implements a recursive method that finds the determinant of an nxn matrix using Laplace, or cofactor expansion. It runs O(n!) time
     */
    public static void Determinant() {
        int[][] matrix = createMatrix(true);
        System.out.println(Arrays.deepToString(matrix));
        int determinant = det(matrix, 0);
        System.out.println("Determinant of Matrix: " + determinant);
    }

    /**
     * This method creates a matrix from user input
     * @return a 2D int array
     */
    public static int[][] createMatrix(boolean isSquare) {
        Scanner input = new Scanner(System.in);
        int rows, cols;
        if (isSquare) {
            System.out.print("Enter the matrix size: ");
            rows = input.nextInt();
            cols = rows;
        } else {
            System.out.print("Enter the number of rows: ");
            rows = input.nextInt();
            System.out.print("Enter the number of columns: ");
            cols = input.nextInt();
        }
        if (rows <= 0 || cols <= 0) {
            System.out.println("Matrix does not exist under given dimensions");
            return null;
        }
        int[][] matrix = new int[rows][cols];
        System.out.printf("%dx%d matrix initialized\n", rows, cols);
        for(int i = 1; i <= rows; i++) {
            System.out.println("Enter values for row " + i);
            for(int j = 0; j < cols; j++) {
                matrix[i - 1][j] = input.nextInt();
            }
        }
        return matrix;
    }

    /**
     * The method called to find the determinant
     * @param matrix the nxn matrix being analyzed
     * @param col the column being analyzed
     * @return an int
     */
    public static int det(int[][] matrix, int col) {

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
            return (int)Math.pow(-1, col+2) * matrix[0][col] * det(modifyMatrix(matrix, col), 0);
        }

        //else, returns the cofactor expansion algorithm for finding the determinant, adding each value using recursive calls iterating through each column
        return (int)Math.pow(-1, col+2) * matrix[0][col] * det(modifyMatrix(matrix, col), 0) + det(matrix, col+1);
    }
    
    /**
     * The method called by det to modify the matrix making it smaller
     * @param matrix the initial nxn matrix input
     * @param col the column and row being removed from the matrix
     * @return an (n-1)x(n-1) matrix
     */
    private static int[][] modifyMatrix(int[][] matrix, int col) {
        int[][] newMatrix = new int[matrix.length-1][matrix.length-1];
        int rowPointer = 0;
        int colPointer = 0;
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
}