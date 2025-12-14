import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a test instance for rectangle packing algorithms.
 * Each instance contains a container box and a set of rectangles to be packed.
 * Instances are generated with random rectangle sizes within specified bounds.
 *
 * <p>This class is designed for benchmarking and comparing different packing algorithms.
 * It ensures reproducible random generation by using a seed based on instance parameters.</p>
 *
 * <p>Example usage:
 * <pre>{@code
 * TestInstance instance = new TestInstance(
 *     1,            // instance ID
 *     100,          // box length (square container)
 *     50,           // number of rectangles
 *     2, 5,         // min and max width
 *     3, 8,         // min and max height
 *     12345L        // random seed
 * );
 * }</pre>
 *
 * @version 1.0
 * @see Rectangle
 * @see Box
 */
public class TestInstance {

    /**
     * Unique identifier for this test instance.
     */
    private final int instanceId;

    /**
     * The container box where rectangles must be packed.
     * Assumed to be square (length × length).
     */
    private final Box box;

    /**
     * List of rectangles to be packed into the container.
     * Each rectangle has random dimensions within specified bounds.
     */
    private final List<Rectangle> rectangles;

    /**
     * Minimum width allowed for rectangles in this instance.
     */
    private final int minWidth;

    /**
     * Maximum width allowed for rectangles in this instance.
     */
    private final int maxWidth;

    /**
     * Minimum height allowed for rectangles in this instance.
     */
    private final int minHeight;

    /**
     * Maximum height allowed for rectangles in this instance.
     */
    private final int maxHeight;

    /**
     * Packing density: total area of rectangles divided by container area.
     * Represents how "full" the container would be if all rectangles could fit perfectly.
     */
    private final double packingDensity;

    /**
     * Random number generator used for creating rectangles.
     * Uses a fixed seed for reproducible instance generation.
     */
    private final Random random;

    /**
     * Constructs a new test instance with the specified parameters.
     *
     * @param instanceId     unique identifier for this instance
     * @param boxLength      side length of the square container
     * @param numRectangles  number of rectangles to generate
     * @param minWidth       minimum width for rectangles (inclusive)
     * @param maxWidth       maximum width for rectangles (inclusive)
     * @param minHeight      minimum height for rectangles (inclusive)
     * @param maxHeight      maximum height for rectangles (inclusive)
     * @param seed           random seed for reproducible generation, or null for random seed
     *
     * @throws IllegalArgumentException if:
     *         - boxLength ≤ 0
     *         - numRectangles ≤ 0
     *         - minWidth ≤ 0 or minHeight ≤ 0
     *         - maxWidth < minWidth or maxHeight < minHeight
     *         - any rectangle dimension exceeds boxLength
     */
    public TestInstance(int instanceId, int boxLength, int numRectangles,
                        int minWidth, int maxWidth, int minHeight, int maxHeight,
                        Long seed) {

        validateParameters(boxLength, numRectangles, minWidth, maxWidth, minHeight, maxHeight);

        this.instanceId = instanceId;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;

        // Initialize random generator with seed or current time
        this.random = (seed != null) ? new Random(seed) : new Random(System.nanoTime() + instanceId);

        // Create square container
        this.box = new Box(boxLength, boxLength);

        // Generate rectangles
        this.rectangles = generateRectangles(numRectangles);

        // Calculate packing density
        this.packingDensity = calculatePackingDensity();
    }

    /**
     * Alternative constructor with automatic seed generation.
     * Uses a seed derived from instance parameters for reproducibility.
     *
     * @param instanceId     unique identifier for this instance
     * @param boxLength      side length of the square container
     * @param numRectangles  number of rectangles to generate
     * @param minWidth       minimum width for rectangles (inclusive)
     * @param maxWidth       maximum width for rectangles (inclusive)
     * @param minHeight      minimum height for rectangles (inclusive)
     * @param maxHeight      maximum height for rectangles (inclusive)
     */
    public TestInstance(int instanceId, int boxLength, int numRectangles,
                        int minWidth, int maxWidth, int minHeight, int maxHeight) {
        this(instanceId, boxLength, numRectangles,
                minWidth, maxWidth, minHeight, maxHeight,
                generateSeed(instanceId, boxLength, numRectangles));
    }

    /**
     * Validates all input parameters for correctness.
     *
     * @param boxLength      container side length
     * @param numRectangles  number of rectangles
     * @param minWidth       minimum rectangle width
     * @param maxWidth       maximum rectangle width
     * @param minHeight      minimum rectangle height
     * @param maxHeight      maximum rectangle height
     *
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private void validateParameters(int boxLength, int numRectangles,
                                    int minWidth, int maxWidth,
                                    int minHeight, int maxHeight) {

        if (boxLength <= 0) {
            throw new IllegalArgumentException("Box length must be positive");
        }
        if (numRectangles <= 0) {
            throw new IllegalArgumentException("Number of rectangles must be positive");
        }
        if (minWidth <= 0 || minHeight <= 0) {
            throw new IllegalArgumentException("Minimum dimensions must be positive");
        }
        if (maxWidth < minWidth) {
            throw new IllegalArgumentException("Maximum width must be >= minimum width");
        }
        if (maxHeight < minHeight) {
            throw new IllegalArgumentException("Maximum height must be >= minimum height");
        }
        if (maxWidth > boxLength || maxHeight > boxLength) {
            throw new IllegalArgumentException(
                    String.format("Rectangle dimensions (%d, %d) exceed container size %d",
                            maxWidth, maxHeight, boxLength)
            );
        }
    }

    /**
     * Generates a deterministic seed based on instance parameters.
     * Ensures the same parameters always produce the same instance.
     *
     * @param instanceId     instance identifier
     * @param boxLength      container size
     * @param numRectangles  number of rectangles
     * @return deterministic seed value
     */
    private static long generateSeed(int instanceId, int boxLength, int numRectangles) {
        // Combine parameters to create a unique seed
        return ((long) instanceId * 31L) ^
                ((long) boxLength * 17L) ^
                ((long) numRectangles * 23L);
    }

    /**
     * Generates random rectangles within the specified bounds.
     *
     * @param numRectangles number of rectangles to generate
     * @return list of generated rectangles
     */
    private List<Rectangle> generateRectangles(int numRectangles) {
        List<Rectangle> rectangles = new ArrayList<>(numRectangles);
        Rectangle.initializeLimits(minWidth, minHeight, maxWidth, maxHeight);

        for (int i = 0; i < numRectangles; i++) {
            int width = random.nextInt(minWidth, maxWidth + 1);
            int height = random.nextInt(minHeight, maxHeight + 1);

            // Ensure rectangle can be rotated (width <= box length and height <= box length)
            // This is already validated in constructor
            Rectangle rectangle = new Rectangle(width, height);

            rectangles.add(rectangle);
        }

        return rectangles;
    }

    /**
     * Calculates the packing density of this instance.
     * Density = total area of all rectangles / container area.
     *
     * @return packing density (0.0 to 1.0+)
     */
    private double calculatePackingDensity() {
        long totalRectangleArea = 0;
        for (Rectangle rect : rectangles) {
            totalRectangleArea += (long) rect.getWidth() * rect.getLength();
        }

        long containerArea = (long) box.getLength() * box.getWidth();
        return (double) totalRectangleArea / containerArea;
    }

    /**
     * Returns the unique identifier of this instance.
     *
     * @return instance ID
     */
    public int getInstanceId() {
        return instanceId;
    }

    /**
     * Returns the container box for this instance.
     *
     * @return container box
     */
    public Box getBox() {
        return box;
    }

    /**
     * Returns an unmodifiable view of the rectangles in this instance.
     *
     * @return list of rectangles (cannot be modified)
     */
    public List<Rectangle> getRectangles() {
        return new ArrayList<>(rectangles); // Defensive copy
    }

    /**
     * Returns the number of rectangles in this instance.
     *
     * @return rectangle count
     */
    public int getRectangleCount() {
        return rectangles.size();
    }

    /**
     * Returns the total area of all rectangles in this instance.
     *
     * @return total rectangle area
     */
    public long getTotalRectangleArea() {
        long totalArea = 0;
        for (Rectangle rect : rectangles) {
            totalArea += (long) rect.getWidth() * rect.getLength();
        }
        return totalArea;
    }

    /**
     * Returns the packing density of this instance.
     * A density greater than 1.0 indicates that rectangles cannot all fit
     * (total rectangle area exceeds container area).
     *
     * @return packing density
     */
    public double getPackingDensity() {
        return packingDensity;
    }

    /**
     * Returns the minimum width constraint for rectangles.
     *
     * @return minimum rectangle width
     */
    public int getMinWidth() {
        return minWidth;
    }

    /**
     * Returns the maximum width constraint for rectangles.
     *
     * @return maximum rectangle width
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * Returns the minimum height constraint for rectangles.
     *
     * @return minimum rectangle height
     */
    public int getMinHeight() {
        return minHeight;
    }

    /**
     * Returns the maximum height constraint for rectangles.
     *
     * @return maximum rectangle height
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Returns the container side length (box is square).
     *
     * @return container side length
     */
    public int getBoxLength() {
        return box.getLength(); // Assuming square container
    }

    /**
     * Creates a deep copy of this instance with a new ID.
     * Useful for testing algorithms on the same data with different seeds.
     *
     * @param newInstanceId new ID for the copied instance
     * @return a new TestInstance with identical data but new ID
     */
    public TestInstance copyWithNewId(int newInstanceId) {
        // Create a copy with the same parameters but new ID
        TestInstance copy = new TestInstance(
                newInstanceId,
                getBoxLength(),
                getRectangleCount(),
                minWidth, maxWidth,
                minHeight, maxHeight,
                (long) this.random.nextInt() // New random seed for the copy
        );

        // Note: This creates new random rectangles. For exact copy, we would
        // need to copy the rectangle list directly, which requires additional logic.
        return copy;
    }

    /**
     * Returns statistical information about rectangle sizes in this instance.
     *
     * @return string containing min, max, and average dimensions
     */
    public String getSizeStatistics() {
        if (rectangles.isEmpty()) {
            return "No rectangles";
        }

        int minW = Integer.MAX_VALUE, maxW = Integer.MIN_VALUE;
        int minH = Integer.MAX_VALUE, maxH = Integer.MIN_VALUE;
        long totalWidth = 0, totalHeight = 0;

        for (Rectangle rect : rectangles) {
            int w = rect.getWidth();
            int h = rect.getLength();

            minW = Math.min(minW, w);
            maxW = Math.max(maxW, w);
            minH = Math.min(minH, h);
            maxH = Math.max(maxH, h);

            totalWidth += w;
            totalHeight += h;
        }

        double avgWidth = (double) totalWidth / rectangles.size();
        double avgHeight = (double) totalHeight / rectangles.size();

        return String.format(
                "Width: %d-%d (avg %.1f), Height: %d-%d (avg %.1f)",
                minW, maxW, avgWidth, minH, maxH, avgHeight
        );
    }

    /**
     * Returns a string representation of this instance.
     * Format: "TestInstance[ID=#, Box=LxL, Rectangles=N, Density=0.00]"
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return String.format(
                "TestInstance[ID=%d, Box=%dx%d, Rectangles=%d, Density=%.3f]",
                instanceId, box.getLength(), box.getWidth(),
                rectangles.size(), packingDensity
        );
    }

    /**
     * Returns detailed information about this instance including statistics.
     *
     * @return detailed instance information
     */
    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Test Instance Details ===\n");
        sb.append("Instance ID: ").append(instanceId).append("\n");
        sb.append("Container: ").append(box.getLength())
                .append("x").append(box.getWidth()).append("\n");
        sb.append("Number of rectangles: ").append(rectangles.size()).append("\n");
        sb.append("Rectangle size bounds: ")
                .append(minWidth).append("-").append(maxWidth).append(" x ")
                .append(minHeight).append("-").append(maxHeight).append("\n");
        sb.append("Packing density: ").append(String.format("%.3f", packingDensity)).append("\n");
        sb.append("Size statistics: ").append(getSizeStatistics()).append("\n");

        if (rectangles.size() <= 20) { // Only list rectangles for small instances
            sb.append("\nRectangles:\n");
            for (int i = 0; i < rectangles.size(); i++) {
                Rectangle rect = rectangles.get(i);
                sb.append(String.format("  [%2d] %3d x %-3d (Area: %5d)\n",
                        i, rect.getWidth(), rect.getLength(), rect.getWidth() * rect.getLength()));
            }
        } else {
            sb.append("\n[Too many rectangles to list]\n");
        }

        return sb.toString();
    }
}