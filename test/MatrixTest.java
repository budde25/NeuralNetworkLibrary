import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void add() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void subtract() {
    }

    @Test
    void testSubtract() {
    }

    @Test
    void multiply() {
    }

    @Test
    void testMultiply() {
    }

    @Test
    void setValue() {
    }

    @Test
    void getValue() {
    }

    @Test
    void transpose() {
    }

    @Test
    void matrixProduct() {
    }

    @Test
    void randomizeValues() {
    }

    @Test
    void randomizeValue() {
    }

    @Test
    void map() {
    }

    @Test
    void fromArray() {
    }

    @Test
    void toArray() {
    }

    @Test
    void isValid() {
        Matrix mat = new Matrix(3, 8);
        assertTrue(mat.isValid());
    }

    @Test
    void testEquals() {
        Matrix mat1 = new Matrix(1, 1);
        Matrix mat2 = new Matrix(1,1);
        mat1.setValue(0,0,1.0);
        mat2.setValue(0,0,1.0);

        assertEquals(mat1, mat2);

        mat2.setValue(0,0, 2.0);

        assertNotEquals(mat1, mat2);
    }
}