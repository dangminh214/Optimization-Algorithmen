public class Rectangle {
    private static int maxWidth;
    private static int maxHeight;
    private static int minWidth;
    private static int minHeight;

    private static boolean limitsInitialized = false;

    // Instance fields
    private int x;
    private int y;
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
    public Rectangle(int x, int y) {
        if (!limitsInitialized) {
            throw new IllegalStateException("Rectangle limits not initialized. Call initializeLimits() first.");
        }
        setX(x);
        setY(y);
        this.area = x*y;
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
    public void setX(int x) {
        validateX(x);
        this.x = x;

        // recalculate area
        this.area = x*y;
    }

    public void setY(int y) {
        validateY(y);
        this.y = y;

        // recalculate area
        this.area = x*y;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }

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
}