import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class Matrix {

    private int rows;
    private int columns;
    private ArrayList<ArrayList<Double>> data;
    private Random random;

    /**
     * Default constructor
     * @param rows number of rows in the matrix
     * @param cols number of columns in the matrix
     */
    Matrix(int rows, int cols) {
        this.rows = rows;
        this.columns = cols;
        this.random = new Random();

        this.data = new ArrayList<>(rows);

        // Initialize every value of this matrix to null
        for (int i = 0; i < rows; i++) {
            data.add(new ArrayList<>(cols));
            for (int j = 0; j < cols; j++) {
                data.get(i).add(null);
            }
        }
    }

    /**
     * Constructor with a seed
     * @param rows number of rows in the matrix
     * @param cols number of columns in the matrix
     * @param seed a seed for the random generator
     */
    Matrix(int rows, int cols, long seed) {
        this(rows, cols);
        this.random .setSeed(seed);
    }

    /**
     * Adds each number in the matrix by a number
     * @param number int number
     * @return a new matrix with new values
     */
    public Matrix add(int number) {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(i, j, getValue(i, j) + number);
            }
        }
        return result;
    }

    /**
     * Adds each number in a matrix by the complement in another matrix
     * @param matrix other matrix
     * @return a new matrix with new values
     */
    public Matrix add(Matrix matrix) {
        if (rows != matrix.rows || columns != matrix.columns)
            throw new IllegalStateException("Error Matrix sizes are not the same");

        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(i, j, getValue(i, j) + matrix.getValue(i, j));
            }
        }
        return result;
    }

    /**
     * Subtracts each number in the matrix by a number
     * @param number int number
     * @return a new matrix with new values
     */
    public Matrix subtract(int number) {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(i, j, getValue(i, j) - number);
            }
        }
        return result;
    }

    /**
     * Subtracts each number in a matrix by the complement in another matrix
     * @param matrix other matrix
     * @return a new matrix with new values
     */
    public Matrix subtract(Matrix matrix) {
        if (rows != matrix.rows || columns != matrix.columns)
            throw new IllegalStateException("Error Matrix sizes are not the same");

        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(i, j, getValue(i, j) - matrix.getValue(i, j));
            }
        }
        return result;
    }

    /**
     * Multiplies each number in the matrix by a number
     * @param number integer number
     * @return a new matrix with new values
     */
    public Matrix multiply(double number) {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(i, j, getValue(i, j) * number);
            }
        }
        return result;
    }

    /**
     * Multiplies each number in the matrix by a number in the complements matrix
     * @param matrix another matrix
     * @return a new matrix with new values
     */
    public Matrix multiply(Matrix matrix) {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(i, j, getValue(i, j) * matrix.getValue(i, j));
            }
        }
        return result;
    }

    /**
     * sets a value at a specific point
     * @param row row
     * @param col column
     * @param number value to set
     */
    public void setValue(int row, int col, double number) {
        if (row > rows || col > columns)
            throw new ArrayIndexOutOfBoundsException("Error: columns or row does not exit");

        data.get(row).set(col, number);
    }

    /**
     * gets a value at a specific point
     * @param row row
     * @param col column
     * @return value
     */
    public Double getValue(int row, int col) {
        if (row > rows || col > columns)
            throw new ArrayIndexOutOfBoundsException("Error: columns or row does not exit");

        return data.get(row).get(col);
    }

    /**
     * Creates a matrix complement
     * @return a transposed matrix of the original
     */
    public Matrix transpose() {
        Matrix result = new Matrix(columns, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(j, i, getValue(i, j));
            }
        }
        return result;
    }

    /**
     * gets a matrix product between two complementary matrix's
     * @param matrix the second matrix
     * @return a matrix with the matrix product of the two matrix's
     */
    public Matrix matrixProduct(Matrix matrix) {
        if (columns != matrix.rows)
            throw new IllegalStateException("Error: size is not complementary");

        Matrix result = new Matrix(rows, matrix.columns);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.columns; j++) {
                result.setValue(i, j, dotProduct(i, j, matrix));
            }
        }
        return result;
    }

    /**
     * helper method for obtaining each value for the Matrix product
     * @param row the current row
     * @param col the current column
     * @param matrix the other matrix
     * @return the dot product of the row and column
     */
    private double dotProduct(int row, int col, Matrix matrix) {
        double sum = 0;
        for (int i = 0; i < columns; i++) {
            sum += getValue(row, i) * matrix.getValue(i, col);
        }
        return sum;
    }

    /**
     * Randomizes entire matrix between -1 and 1
     */
    public void randomizeValues() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                setValue(i, j, random.nextDouble() * 2 - 1);
            }
        }
    }

    /**
     * Randomizes single point to a value between -1 and 1
     * @param row the rowSS
     * @param col the column
     */
    public void randomizeValue(int row, int col) {
        setValue(row, col, random.nextDouble() * 2 -1);
    }

    public Matrix map(Function<Double, Double> function) {
        Matrix result = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setValue(i, j, function.apply(getValue(i, j)));
            }
        }
        return result;
    }

    /**
     * Imports a single column array into a matrix
     * @param array ArrayList
     * @return a single column array
     */
    public static Matrix fromArray(ArrayList<Double> array){
        Matrix matrix = new Matrix(array.size(), 1);
        for (int i = 0; i < array.size(); i++) {
            matrix.setValue(i, 0, array.get(i));
        }
        return matrix;
    }

    public ArrayList<Double> toArray() {
        ArrayList<Double> output = new ArrayList<>();
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                output.add(getValue(i, j));
            }
        }
        return output;
    }

    /**
     * checks to see if every value of the array is initialized with a non null value
     * @return true if valid matrix, false otherwise
     */
    public Boolean isValid() {
        boolean hasNull = false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
               if (getValue(i, j) == null) {
                   hasNull = true;
               }
            }
        }

        return !hasNull;
    }

    /**
     * Custom matrix toString showing the matrix
     * @return matrix ArrayList
     */
    @Override
    public String toString() {
        return data.toString();
    }

    /**
     * Prints the Matrix is a nicer view
     */
    public void print() {
        for (int i = 0; i < rows; i++) {
            System.out.println(data.get(i));
        }
    }

    /**
     * Checks that two matrix's are equal
     * @param matrix another matrix
     * @return true if equal, false otherwise
     */
    public Boolean equals(Matrix matrix) {

        if (rows != matrix.rows || columns != matrix.columns)
            return false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!getValue(i, j).equals(matrix.getValue(i, j))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * gets the rows
     * @return number of rows
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * gets the columns
     * @return number of columns
     */
    public Integer getColumns() {
        return columns;
    }
}
