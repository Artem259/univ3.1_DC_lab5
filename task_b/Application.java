import java.util.concurrent.CyclicBarrier;

public class Application {
    private final int threadsAmount;
    private final int equalsRequired;
    private final int stringsSize;

    public Application(int threadsAmount, int equalsRequired, int stringsSize) {
        this.threadsAmount = threadsAmount;
        this.equalsRequired = equalsRequired;
        this.stringsSize = stringsSize;
    }

    public void start() {
        StringsController controller = new StringsController(threadsAmount, equalsRequired, stringsSize);
        Runnable runnable = controller::checkCondition;
        CyclicBarrier barrier1 = new CyclicBarrier(threadsAmount, runnable);
        CyclicBarrier barrier2 = new CyclicBarrier(threadsAmount);

        for(int i=0; i<threadsAmount; i++) {
            new Thread(new StringEditor(controller.stringRef(i), barrier1, barrier2, controller)).start();
        }
    }

    public static void main(String[] args) {
        new Application(4, 2, 10).start();
    }
}
