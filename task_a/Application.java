public class Application {
    private final int lineLength;

    public Application(int lineLength) {
        this.lineLength = lineLength;
    }

    public void start() {
        LineController controller = new LineController(lineLength);
        Runnable runnable = () -> {
            Line l = controller.lineRef();
            if (l.areFacingEachOther(controller.lastLeftIndex(), controller.firstRightIndex())) {
                l.rotateAt(controller.lastLeftIndex());
                l.rotateAt(controller.firstRightIndex());
            }
            controller.checkLineState();
        };
        MyCyclicBarrier barrier1 = new MyCyclicBarrier(2, runnable);
        MyCyclicBarrier barrier2 = new MyCyclicBarrier(2);

        new Thread(new LineEditor(controller.lineRef(),
                0, controller.lastLeftIndex(),
                barrier1, barrier2, controller)).start();
        new Thread(new LineEditor(controller.lineRef(),
                controller.firstRightIndex(), controller.lineLength()-1,
                barrier1, barrier2, controller)).start();
    }

    public static void main(String[] args) {
        new Application(100).start();
    }
}
