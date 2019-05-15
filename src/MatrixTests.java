public class MatrixTests {

    public static void main(String[] args) {
        System.out.println("test testMatrix() passed: " + testMatrix());
        System.out.println("test testMatrixProduct() passed: " + testMatrixProduct());
        System.out.println("test testTranspose() passed: " + testTranspose());
    }

    public static boolean testMatrix() {
        boolean testPassed = true;

        Matrix mat = new Matrix(2 , 2);

        if (mat.isValid()) testPassed = false;

        mat.setValue(0, 0, 1);
        mat.setValue(0, 1, 2);
        mat.setValue(1, 0, 3);
        mat.setValue(1, 1, 4);

        if (!mat.isValid()) testPassed = false;

       if (mat.getValue(0, 0) != 1) testPassed = false;
       if (mat.getValue(0, 1) != 2) testPassed = false;
       if (mat.getValue(1, 0) != 3) testPassed = false;
       if (mat.getValue(1, 1) != 4) testPassed = false;

       mat.multiply(2);

        if (mat.getValue(0, 0) != 2) testPassed = false;
        if (mat.getValue(0, 1) != 4) testPassed = false;
        if (mat.getValue(1, 0) != 6) testPassed = false;
        if (mat.getValue(1, 1) != 8) testPassed = false;

       return testPassed;
    }

    public static boolean testMatrixProduct() {
        boolean testPassed = true;

        Matrix mat1 = new Matrix(2, 3);
        Matrix mat2 = new Matrix(3, 2);

        mat1.setValue(0, 0, 1);
        mat1.setValue(0, 1, 2);
        mat1.setValue(0, 2, 3);
        mat1.setValue(1, 0, 4);
        mat1.setValue(1, 1, 5);
        mat1.setValue(1, 2, 6);

        mat2.setValue(0, 0, 7);
        mat2.setValue(0, 1, 10);
        mat2.setValue(1, 0, 8);
        mat2.setValue(1, 1, 11);
        mat2.setValue(2, 0, 9);
        mat2.setValue(2, 1, 12);

        System.out.println(mat1.matrixProduct(mat2));

        return testPassed;
    }

    public static boolean testTranspose() {
        boolean testPassed = true;

        Matrix mat = new Matrix(2, 3);

        mat.setValue(0, 0, 1);
        mat.setValue(0, 1, 2);
        mat.setValue(0, 2, 3);
        mat.setValue(1, 0, 4);
        mat.setValue(1, 1, 5);
        mat.setValue(1, 2, 6);

        System.out.println(mat);
        System.out.println(mat.transpose());

        return testPassed;
    }


}
