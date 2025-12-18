import java.util.List;

public class Utils {
    public static void printBoxes(List<Box> boxes) {
        System.out.println("Number of boxes = " + boxes.size());

        int i = 1;
        for (Box box : boxes) {
            System.out.println("Box " + i +
                    " | Rectangles: " + box.getRectangles().size() +
                    " | Used area: " + box.getUsedArea());
            i++;
        }
    }
}
