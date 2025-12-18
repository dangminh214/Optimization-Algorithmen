import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Greedy {

    private List<Rectangle> rectangles;
    private int boxLength;
    private List<Box> boxes;

    public Greedy(List<Rectangle> rectangles, int boxLength) {
        this.rectangles = new ArrayList<>(rectangles); // copy
        this.boxLength = boxLength;
        this.boxes = new ArrayList<>();
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    /**
     * Run Greedy algorithm
     * @param strategy "AREA_DESC" or "LENGTH_DESC"
     */
    public void run(String strategy) {
        // Sort rectangles according to strategy
        long startTime = System.nanoTime();
        switch (strategy) {
            case "AREA_DESC" -> rectangles.sort(Comparator.comparingInt(Rectangle::getArea).reversed());
            case "LENGTH_DESC" -> rectangles.sort(Comparator.comparingInt(Rectangle::getWidth).reversed());
            default -> throw new IllegalArgumentException("Unknown strategy");
        }

        System.out.println("Greedy (" + strategy + ") running...");

        final int BAR_WIDTH = 30;
        int total = rectangles.size();
        int processed = 0;

        boxes.clear();

        for (Rectangle r : rectangles) {
            boolean placed = false;
            for (Box box : boxes) {
                if (box.canFit(r)) {
                    box.addRectangle(r);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                Box newBox = new Box(boxLength);
                newBox.addRectangle(r);
                boxes.add(newBox);
            }

            // Progress bar
            processed++;
            double progress = (double) processed / total;
            int filled = (int) (BAR_WIDTH * progress);
            StringBuilder bar = new StringBuilder("[");
            for (int i = 0; i < BAR_WIDTH; i++) {
                bar.append(i < filled ? "#" : "-");
            }
            bar.append("] ");
            System.out.printf("\r%s%.4fs", bar, (System.nanoTime() - startTime) / 1_000_000_000.0);
        }

        double totalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
        System.out.print("\r"); // clear line
        System.out.println("Greedy (" + strategy + ") finished.");
        System.out.printf("Time: %.4f s%n", totalTime);
        System.out.println("Number of boxes used: " + boxes.size());
    }
}
