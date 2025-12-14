public class Rectangle {
    private static int maxWidth;
    private static int maxHeight;
    private static int minWidth;
    private static int minHeight;

    private static boolean limitsInitialized = false;

    // Instance fields
    private int width;
    private int length;
    private int area;

    public static void initializeLimits(int userMinWidth, int userMinHeight, int userMaxWidth, int userMaxHeight) {
        if (limitsInitialized) {
            throw new IllegalStateException("Limits have already been initialized");
        }

        if (userMinWidth >= userMaxWidth) {
            throw new IllegalArgumentException("minX must be less than maxX");
        }
        if (userMinHeight >= userMaxHeight) {
            throw new IllegalArgumentException("minY must be less than maxY");
        }

        minWidth = userMinWidth;
        minHeight = userMinHeight;
        maxWidth = userMaxWidth;
        maxHeight = userMaxHeight;
        limitsInitialized = true;
    }

    // Constructor
    public Rectangle(int width, int length) {
        if (!limitsInitialized) {
            throw new IllegalStateException("Rectangle limits not initialized. Call initializeLimits() first.");
        }
        setWidth(width);
        setLength(length);
        this.area = width * length;
    }

    // Validation methods
    private static void validateLimitsInitialized() {
        if (!limitsInitialized) {
            throw new IllegalStateException("Limits not initialized");
        }
    }

    public static void validateX(int x) {
        validateLimitsInitialized();
        if (x < minWidth || x > maxWidth) {
            throw new IllegalArgumentException(
                    String.format("x must be between %d and %d, got %d", minWidth, maxWidth, x)
            );
        }
    }

    public static void validateY(int y) {
        validateLimitsInitialized();
        if (y < minHeight || y > maxHeight) {
            throw new IllegalArgumentException(
                    String.format("y must be between %d and %d, got %d", minHeight, maxHeight, y)
            );
        }
    }

    // Setters với validation
    public void setWidth(int width) {
        validateX(width);
        this.width = width;

        // recalculate area
        this.area = width * length;
    }

    public void setLength(int length) {
        validateY(length);
        this.length = length;

        // recalculate area
        this.area = width * length;
    }

    // Getters
    public int getWidth() { return width; }
    public int getLength() { return length; }

    public static int getMinWidth() {
        validateLimitsInitialized();
        return minWidth;
    }
    public static int getMaxWidth() {
        validateLimitsInitialized();
        return maxWidth;
    }
    public static int getMinHeight() {
        validateLimitsInitialized();
        return minHeight;
    }
    public static int getMaxHeight() {
        validateLimitsInitialized();
        return maxHeight;
    }

    public void draw() {
        for (int i = 0; i < this.length; i++) {          // rows
            for (int j = 0; j < this.width; j++) {       // columns
                // Top edge, bottom edge, left edge, right edge
                if (i == 0 || i == this.length - 1 ||
                        j == 0 || j == this.width - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");               // interior
                }
            }
            System.out.println();
        }
    }

}