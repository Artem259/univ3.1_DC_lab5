public class LineEditor implements Runnable {
    private final Line line;
    private final int minI, maxI;
    private final MyCyclicBarrier barrier1, barrier2;
    private final LineController controller;

    public LineEditor(Line line, int minI, int maxI,
                      MyCyclicBarrier barrier1,
                      MyCyclicBarrier barrier2,
                      LineController controller) {
        this.line = line;
        this.minI = minI;
        this.maxI = maxI;
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("barrier-1");
                barrier1.await();
                if (controller.isStaticLineState()) {
                    break;
                }
                System.out.println("barrier--2");
                barrier2.await();
                for (int i=minI; i<maxI; i++) {
                    if (line.areFacingEachOther(i, i+1)) {
                        line.rotateAt(i);
                        line.rotateAt(i+1);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
