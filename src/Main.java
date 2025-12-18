public static void main(String[] args) {
    TestInstance testInstance = new TestInstance(1, 20, 10, 2, 10, 3, 15, 12345L);

    Greedy greedy = new Greedy(testInstance.getRectangles(), testInstance.getBoxLength());
    greedy.run("AREA_DESC"); // base on area desc
    Utils.printBoxes(greedy.getBoxes());

    Greedy greedy2 = new Greedy(testInstance.getRectangles(), testInstance.getBoxLength());
    greedy2.run("LENGTH_DESC"); // base on length desc
    Utils.printBoxes(greedy2.getBoxes());
}
