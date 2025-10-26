package assignment3.util;

import com.aitbek.assignment3.util.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PerformanceTrackerTest {

    @Test
    void testPerformanceTrackerBasicOperations() {
        PerformanceTracker tracker = new PerformanceTracker();

        tracker.startTimer();

        // Simulate some operations
        tracker.incrementComparisons(5);
        tracker.incrementUnionOperations();
        tracker.incrementFindOperations();
        tracker.incrementEdgeProcessing();

        try {
            Thread.sleep(10); // Sleep for 10ms to measure time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        tracker.stopTimer();

        // Verify counts
        assertEquals(5, tracker.getComparisonCount());
        assertEquals(1, tracker.getUnionOperations());
        assertEquals(1, tracker.getFindOperations());
        assertEquals(1, tracker.getEdgeProcessingCount());

        // Total operations should be sum of all
        assertEquals(8, tracker.getTotalOperations());

        // Elapsed time should be at least 10ms
        assertTrue(tracker.getElapsedTimeMillis() >= 10);
    }

    @Test
    void testPerformanceTrackerReset() {
        PerformanceTracker tracker = new PerformanceTracker();

        tracker.startTimer();
        tracker.incrementComparisons(3);
        tracker.stopTimer();

        // Start again should reset counters
        tracker.startTimer();
        tracker.incrementComparisons(2);
        tracker.stopTimer();

        // Should only count the last batch
        assertEquals(2, tracker.getComparisonCount());
    }

    @Test
    void testMultipleIncrements() {
        PerformanceTracker tracker = new PerformanceTracker();

        tracker.startTimer();

        for (int i = 0; i < 100; i++) {
            tracker.incrementComparisons(1);
            tracker.incrementUnionOperations();
            tracker.incrementFindOperations();
            tracker.incrementEdgeProcessing();
        }

        tracker.stopTimer();

        assertEquals(100, tracker.getComparisonCount());
        assertEquals(100, tracker.getUnionOperations());
        assertEquals(100, tracker.getFindOperations());
        assertEquals(100, tracker.getEdgeProcessingCount());
        assertEquals(400, tracker.getTotalOperations());
    }
}