public class Rectangle {
    private static int maxWidth;
    private static int maxHeight;
    private static int minWidth;
    private static int minHeight;

    private static boolean limitsInitialized = false;

    // Instance fields
    private int width;
    private int height;
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
    public Rectangle(int width, int height) {
        if (!limitsInitialized) {
            throw new IllegalStateException("Rectangle limits not initialized. Call initializeLimits() first.");
        }
        setWidth(width);
        setHeight(height);
        this.area = width * height;
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
        this.area = width * height;
    }

    public void setHeight(int height) {
        validateY(height);
        this.height = height;

        // recalculate area
        this.area = width * height;
    }

    // Getters
    public int getWidth() { return width; }
    public int getHeight() { return height; }

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
        for (int i = 0; i < this.height; i++) {          // rows
            for (int j = 0; j < this.width; j++) {       // columns
                // Top edge, bottom edge, left edge, right edge
                if (i == 0 || i == this.height - 1 ||
                        j == 0 || j == this.width - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");               // interior
                }
            }
            System.out.println();
        }
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }
}