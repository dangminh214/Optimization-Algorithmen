import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocalSearchV2 {

    private List<Rectangle> rectangles;
    private int boxLength;
    private List<Box> boxes;
    private int boxArea;

    public LocalSearchV2(List<Rectangle> rectangles, int boxLength) {
        this.rectangles = sortRectanglesByAreaDesc(new ArrayList<>(rectangles));
        this.boxLength = boxLength;
        this.boxes = new ArrayList<>();
        this.boxArea = boxLength * boxLength;
    }

    /**
     * Sort rectangles DESCENDING by area
     */
    private List<Rectangle> sortRectanglesByAreaDesc(List<Rectangle> rectangles) {
        rectangles.sort((r1, r2) ->
                Integer.compare(r2.getArea(), r1.getArea()));
        return rectangles;
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    /**
     * FIRST FIT DECREASING
     * Creates a bad-but-valid initial solution
     */
    public void runFFD() {
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
        }

        System.out.println("Number of box need to use for the FFD: " + boxes.size());
    }

    /**
     * LOCAL SEARCH with progress bar and precise timing
     */
    public void runLocalSearch() {

        final int BAR_WIDTH = 30;
        final long UPDATE_INTERVAL_NS = 50_000_000L; // update every 0.05s

        long startTime = System.nanoTime();
        long lastUpdate = startTime;

        boolean improvement = true;
        int iteration = 0;

        System.out.println("Local Search running...");

        while (improvement) {
            improvement = false;
            iteration++;

            Iterator<Box> boxIterator = boxes.iterator();

            while (boxIterator.hasNext()) {
                Box sourceBox = boxIterator.next();
                List<Rectangle> rects = new ArrayList<>(sourceBox.getRectangles());

                for (Rectangle r : rects) {
                    if (tryMoveRectangle(r, sourceBox)) {
                        improvement = true;
                        if (sourceBox.isEmpty()) {
                            boxIterator.remove();
                        }
                        break;
                    }
                }

                if (improvement) break;
            }

            // ===== Progress bar update =====
            long now = System.nanoTime();
            if (now - lastUpdate >= UPDATE_INTERVAL_NS) {
                lastUpdate = now;

                double elapsedSec = (now - startTime) / 1_000_000_000.0;
                int filled = (int) ((elapsedSec * 2) % BAR_WIDTH); // animation
                StringBuilder bar = new StringBuilder("[");

                for (int i = 0; i < BAR_WIDTH; i++) {
                    bar.append(i < filled ? "#" : "-");
                }
                bar.append("] ");

                System.out.printf("\r%s%.4f s", bar, elapsedSec);
            }
        }

        double totalTime = (System.nanoTime() - startTime) / 1_000_000_000.0;

        System.out.print("\r"); // clear line
        System.out.println("Local Search finished.");
        System.out.println("Iterations: " + iteration);
        System.out.printf("Time: %.4f s%n", totalTime);
        System.out.println("Boxes after Local Search: " + boxes.size());
    }

    /**
     * Try to move rectangle r from sourceBox to another box
     */
    private boolean tryMoveRectangle(Rectangle r, Box sourceBox) {
        for (Box targetBox : boxes) {
            if (targetBox == sourceBox) continue;

            if (targetBox.canFit(r)) {
                sourceBox.removeRectangle(r);
                targetBox.addRectangle(r);
                return true;
            }
        }
        return false;
    }

    public List<Box> getBoxes() {
        return boxes;
    }
}
