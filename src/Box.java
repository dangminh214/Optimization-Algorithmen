import java.util.Vector;

public class Box {
    private final int length;
    private int area;
    private Vector<Rectangle> insideRectangles;

    // Constructor
    public Box(int length) {
        this.length = length;
        calculateArea();
        this.insideRectangles = new Vector<>();
    }

    public int getLength() {
        return this.length;
    }

    // Calculate area based on length and width
    private void calculateArea() {
        this.area = this.length * this.length;
    }

    // Method to remove rectangle by index
    public Rectangle removeRectangle(int index) {
        if (index < 0 || index >= insideRectangles.size()) {
            throw new IndexOutOfBoundsException("Invalid rectangle index: " + index);
        }
        return insideRectangles.remove(index);
    }

    // Method to calculate total area of all rectangles in the box
    public int calculateTotalRectanglesArea() {
        int totalArea = 0;
        for (Rectangle rect : insideRectangles) {
            totalArea += (rect.getWidth() * rect.getHeight());
        }
        return totalArea;
    }

    // Method to calculate remaining empty area in the box
    public int calculateEmptyArea() {
        return this.area - calculateTotalRectanglesArea();
    }

    // Override toString for better representation
    @Override
    public String toString() {
        return String.format("Box[L=%d, W=%d, Area=%d, Rectangles=%d, EmptyArea=%d]",
                length, length, area, insideRectangles.size(), calculateEmptyArea());
    }

    // Utility method to display box contents
    public void displayContents() {
        System.out.println("=== Box Information ===");
        System.out.println("Dimensions: " + length + " x " + length);
        System.out.println("Total Area: " + area);
        System.out.println("Number of Rectangles: " + insideRectangles.size());
        System.out.println("Total Rectangles Area: " + calculateTotalRectanglesArea());
        System.out.println("Empty Area: " + calculateEmptyArea());

        if (!insideRectangles.isEmpty()) {
            System.out.println("\nRectangles in box:");
            for (int i = 0; i < insideRectangles.size(); i++) {
                Rectangle rect = insideRectangles.get(i);
                System.out.printf("  [%d] Rectangle: %d x %d (Area: %d)%n",
                        i, rect.getWidth(), rect.getHeight(), rect.getWidth() * rect.getHeight());
            }
        }
    }

    public void draw() {
        for (int i = 0; i < this.length; i++) {          // rows
            for (int j = 0; j < this.length; j++) {       // columns
                // Top edge, bottom edge, left edge, right edge
                if (i == 0 || i == this.length - 1 ||
                        j == 0 || j == this.length - 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");               // interior
                }
            }
            System.out.println();
        }
    }
}