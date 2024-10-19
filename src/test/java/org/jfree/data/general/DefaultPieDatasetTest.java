

package org.jfree.data.general;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.junit.Test;

import java.io.*;
import java.util.EventObject;

import static org.junit.Assert.*;

public class DefaultPieDatasetTest {

    @Test
    public void testClear() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        DatasetChangeListener listener = new DatasetChangeListener() {
            @Override
            public void datasetChanged(DatasetChangeEvent event) {
                // This will be set to true if a change event is triggered
                eventTriggered = true;
            }
        };
        dataset.addChangeListener(listener);

        // Verify that clearing an empty dataset does not trigger a change event
        eventTriggered = false;
        dataset.clear();
        assertFalse(eventTriggered);

        // Add an item to the dataset
        dataset.setValue("A", 1.0);
        assertEquals(1, dataset.getItemCount());

        // Clear the dataset and confirm that a change event is triggered and the dataset becomes empty
        eventTriggered = false;
        dataset.clear();
        assertTrue(eventTriggered);
        assertEquals(0, dataset.getItemCount());
    }

    @Test
    public void testGetKey() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 1.0);
        dataset.setValue("B", 2.0);
        dataset.setValue("C", 3.0);

        // Verify that retrieving the key by its index returns the correct key
        assertEquals("A", dataset.getKey(0));
        assertEquals("B", dataset.getKey(1));
        assertEquals("C", dataset.getKey(2));

        // Ensure that requesting a key with an out-of-bounds index throws the appropriate exception
        try {
            dataset.getKey(3);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    @Test
    public void testGetIndex() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 1.0);
        dataset.setValue("B", 2.0);
        dataset.setValue("C", 3.0);

        // Verify that retrieving the index of a key returns the correct index
        assertEquals(0, dataset.getIndex("A"));
        assertEquals(1, dataset.getIndex("B"));
        assertEquals(2, dataset.getIndex("C"));

        // Ensure that requesting the index for a non-existing key returns -1
        assertEquals(-1, dataset.getIndex("D"));

        // Ensure that passing a null key throws an exception
        try {
            dataset.getIndex(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 1.0);
        dataset.setValue("B", 2.0);
        dataset.setValue("C", 3.0);

        // Clone the dataset
        DefaultPieDataset clonedDataset = (DefaultPieDataset) dataset.clone();

        // Verify that the cloned dataset is not the same instance but contains identical data
        assertNotSame(dataset, clonedDataset);
        assertEquals(dataset.getItemCount(), clonedDataset.getItemCount());
        for (int i = 0; i < dataset.getItemCount(); i++) {
            assertEquals(dataset.getKey(i), clonedDataset.getKey(i));
            assertEquals(dataset.getValue(i), clonedDataset.getValue(i));
        }
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("A", 1.0);
        dataset.setValue("B", 2.0);
        dataset.setValue("C", 3.0);

        // Serialize the dataset
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(dataset);
        out.close();

        // Restore the dataset
        ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(buffer.toByteArray()));
        DefaultPieDataset restoredDataset = (DefaultPieDataset) in.readObject();
        in.close();

        // Verify that the restored dataset is identical to the original
        assertEquals(dataset.getItemCount(), restoredDataset.getItemCount());
        for (int i = 0; i < dataset.getItemCount(); i++) {
            assertEquals(dataset.getKey(i), restoredDataset.getKey(i));
            assertEquals(dataset.getValue(i), restoredDataset.getValue(i));
        }
    }

    @Test
    public void testBug212() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Verify that attempting to retrieve a value from an empty dataset with an invalid index throws an exception
        try {
            dataset.getValue(0);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }

        // Add an item to the dataset
        dataset.setValue("A", 1.0);

        // Verify that the value can be retrieved by index
        assertEquals(1.0, dataset.getValue(0));

        // Verify that requesting an out-of-bounds index still throws an exception
        try {
            dataset.getValue(1);
            fail("Expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    // Helper variable to track if an event was triggered
    private boolean eventTriggered;
}