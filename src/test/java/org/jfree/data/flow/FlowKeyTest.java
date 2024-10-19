package org.jfree.data.flow;

import org.junit.jupiter.api.Test;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FlowKeyTest {

    @Test
    public void testEquals() {
        FlowKey key1 = new FlowKey(1, "field2", "field3");
        FlowKey key2 = new FlowKey(1, "field2", "field3");
        assertEquals(key1, key2);

        key2 = new FlowKey(1, "field2", "differentField");
        assertNotEquals(key1, key2);

        key2 = new FlowKey(1, "differentField", "field3");
        assertNotEquals(key1, key2);

        key2 = new FlowKey(2, "field2", "field3");
        assertNotEquals(key1, key2);
    }

    @Test
    public void testCloning() throws CloneNotSupportedException {
        FlowKey key1 = new FlowKey(1, "field2", "field3");
        FlowKey key2 = (FlowKey) key1.clone();
        assertNotSame(key1, key2);
        assertEquals(key1, key2);
        assertTrue(key1.getClass() == key2.getClass());
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        FlowKey key1 = new FlowKey(1, "field2", "field3");

        // Serialize the object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(key1);
        out.flush();
        byte[] serializedData = byteOut.toByteArray();

        // Deserialize the object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(serializedData);
        ObjectInputStream in = new ObjectInputStream(byteIn);
        FlowKey key2 = (FlowKey) in.readObject();

        assertEquals(key1, key2);
    }
}