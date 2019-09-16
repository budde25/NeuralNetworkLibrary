import java.util.function.Function;

/**
 * A Data structure for storing items in a Matrix
 * This class is immutable
 *
 * @author Ethan Budd
 *
 * @param <T> Any number, so that math can be preformed
 */
public interface MatrixADT <T extends Number> {

    /**
     * must be at least 1
     * @return the amount rows
     */
    int getRows();

    /**
     * must be at least 1
     * @return the amount columns
     */
    int getColumns();

    /**
     * gets the value at a specific row and column of the matrix
     * @param row the row
     * @param columns the column
     */
    T getValue(int row, int columns);

    /**
     * Adds each element of the matrix with complementary element
     * Throw IllegalStateException if sizes are not the same
     * @param a other matrix
     * @return a matrix where every element is added
     */
    Matrix add(Matrix a);

    /**
     * Adds each element of the matrix with number (Scalar)
     * @param a a number
     * @return a matrix where every element is added with the number
     */
    Matrix add(T a);

    /**
     * Subtract each element of the matrix with complementary element
     * Throw IllegalStateException if sizes are not the same
     * @param a first matrix
     * @return a matrix where every element is subtracted
     */
    Matrix subtract(Matrix a);

    /**
     * Subtracts each element of the matrix with number (Scalar)
     * @param a a number
     * @return a matrix where every element is subtracted with the number
     */
    Matrix subtract(T a);

    /**
     * Multiply each element of the matrix with complementary element
     * Throw IllegalStateException if sizes are not the same
     * @param a other matrix
     * @return a matrix where every element is multiplied
     */
    Matrix multiply(Matrix a);

    /**
     * Preforms a dot multiplication on the two Matrices
     * Throw IllegalStateException if columns size does not equal to a's rows
     * @param a other matrix
     * @return a matrix where it is dotProduct
     */
    Matrix dot(Matrix a);

    /**
     * @return array representation of matrix
     */
    T[] toArray();

    /**
     * applies a function to every element of the array
     * @return a matrix run through the function
     */
    Matrix map(Function<T, T> function);

    /**
     * Creates a a.size row, one column matix
     * @param a an array of Type T
     * @return a Matrix from the array a
     */
    Matrix fromArray(T[] a);

    /**
     * transposes the matrix
     * @return a transposed matrix
     */
    Matrix transposed();

    /**
     * Randomize each value of matrix
     * @return a randomized matrix
     */
    Matrix randomize();

    /**
     * @return true if every element is non null
     */
    boolean isValid();

    /**
     * @return a string representation of the matrix
     */
    String toString();

    /**
     * checks to see if two objects / matrices are equal
     * @param obj the other object
     * @return if they are equal
     */
    boolean equals(Object obj);


}
