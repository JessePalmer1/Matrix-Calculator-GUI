# Matrix-Calculator-GUI
This interface, created from javaFX, allows users to calculate various operations on matrices, such as row reduction, addition, subtraction, multiplication, and the determinant.

The complete code is in MatrixOperationsGUI.java.
A complete build ready to run is in MatrixOperationsGradleBuild

Basic Implementation:
The start method (launched by main) runs a switch statement from the drop down box, which will call the appropriate matrix operation.

Each operation has its own method that displays the GUI components needed, and each of these methods calls the respective operation method from the private inner class Operations.

Various other methods are implemented to help with reusability, such as setMatrix and displayMatrix.

This project stemmed from my interest in creating a recursive method to find the determinant of a matrix using the cofactor expansion algorithm. 
