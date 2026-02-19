import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private final int n;
    private final Semaphore z = new Semaphore(1);
    private final Semaphore o = new Semaphore(0);
    private final Semaphore e = new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            z.acquire();
            printNumber.accept(0);
            if ((i & 1) == 1)
                o.release();
            else
                e.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            o.acquire();
            printNumber.accept(i);
            z.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            e.acquire();
            printNumber.accept(i);
            z.release();
        }
    }

    @FunctionalInterface
    interface InterruptibleRunnable {
        void run() throws InterruptedException;
    }

    static Runnable wrap(InterruptibleRunnable r) {
        return () -> {
            try {
                r.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // clean exit
            }
        };
    }

    public static void main(String[] args) throws Exception {
        final ZeroEvenOdd x = new ZeroEvenOdd(1);
        final var sb = new StringBuilder();
        final IntConsumer p = sb::append;

        var zero = new Thread(wrap(() -> x.zero(p)), "zero");
        var odd = new Thread(wrap(() -> x.odd(p)), "odd");
        var even = new Thread(wrap(() -> x.even(p)), "even");

        zero.start();
        odd.start();
        even.start();

        zero.join();
        odd.join();
        even.join();

        System.out.println(sb);

    }
}