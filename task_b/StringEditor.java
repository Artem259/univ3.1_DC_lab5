import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class StringEditor implements Runnable {
    private final RandString string;
    private final CyclicBarrier barrier1, barrier2;
    private final StringsController controller;

    public StringEditor(RandString string, CyclicBarrier barrier1, CyclicBarrier barrier2, StringsController controller) {
        this.string = string;
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
        this.controller = controller;
    }

    @Override
    public void run() {
        while(true) {
            try {
                System.out.println("barrier-1");
                barrier1.await();
                if(controller.isLastCheckSuccessful()) {
                    break;
                }
                System.out.println("barrier--2");
                barrier2.await();
                string.replaceChar();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
