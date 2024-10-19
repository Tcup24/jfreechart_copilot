package org.jfree.data.function;

import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class PolynomialFunction2DTest {

    @Test
    public void testConstructor() {
        // Valid coefficients
        double[] coefficients = {1.0, 2.0, 3.0};
        PolynomialFunction2D function = new PolynomialFunction2D(coefficients);
        assertArrayEquals(coefficients, function.getCoefficients());

        // Null coefficients
        assertThrows(IllegalArgumentException.class, () -> {
            new PolynomialFunction2D(null);
        });
    }

    @Test
    public void testGetCoefficients() {
        double[] coefficients = {1.0, 2.0, 3.0};
        PolynomialFunction2D function = new PolynomialFunction2D(coefficients);
        double[] retrievedCoefficients = function.getCoefficients();
        assertArrayEquals(coefficients, retrievedCoefficients);

        // Ensure modifying the returned array does not affect the original coefficients
        retrievedCoefficients[0] = 10.0;
        assertNotEquals(retrievedCoefficients[0], function.getCoefficients()[0]);
    }

    @Test
    public void testGetOrder() {
        double[] coefficients = {1.0, 2.0, 3.0};
        PolynomialFunction2D function = new PolynomialFunction2D(coefficients);
        assertEquals(2, function.getOrder());
    }

    @Test
    public void testEquals() {
        double[] coefficients1 = {1.0, 2.0, 3.0};
        double[] coefficients2 = {1.0, 2.0, 3.0};
        double[] coefficients3 = {1.0, 2.0, 4.0};

        PolynomialFunction2D function1 = new PolynomialFunction2D(coefficients1);
        PolynomialFunction2D function2 = new PolynomialFunction2D(coefficients2);
        PolynomialFunction2D function3 = new PolynomialFunction2D(coefficients3);

        assertEquals(function1, function2);
        assertNotEquals(function1, function3);
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        double[] coefficients = {1.0, 2.0, 3.0};
        PolynomialFunction2D function1 = new PolynomialFunction2D(coefficients);

        // Serialize the object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(function1);
        out.flush();
        byte[] serializedData = byteOut.toByteArray();

        // Deserialize the object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(serializedData);
        ObjectInputStream in = new ObjectInputStream(byteIn);
        PolynomialFunction2D function2 = (PolynomialFunction2D) in.readObject();

        assertEquals(function1, function2);
    }

    @Test
    public void testHashCode() {
        double[] coefficients1 = {1.0, 2.0, 3.0};
        double[] coefficients2 = {1.0, 2.0, 3.0};

        PolynomialFunction2D function1 = new PolynomialFunction2D(coefficients1);
        PolynomialFunction2D function2 = new PolynomialFunction2D(coefficients2);

        assertEquals(function1.hashCode(), function2.hashCode());
    }
}