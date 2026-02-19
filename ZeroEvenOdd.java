import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private final int n;
    private boolean lastIsZero = false;
    private int lastNumber = 0;
    private final java.io.PrintStream o = System.out;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
        // o.println("BS zero");
        while (lastNumber < n) {
            while (lastIsZero) {
                // o.println("W zero");
                this.wait();
            }
            if (lastNumber < n) {
                // o.println("P zero");
                printNumber.accept(0);
                lastIsZero = true;
                this.notifyAll();
            }
        }
        // o.println("D zero");
    }

    public synchronized void even(IntConsumer printNumber) throws InterruptedException {
        // o.println("BS even");
        var max = n % 2 == 0 ? n : n - 1;
        while (lastNumber < max) {
            while (!lastIsZero || this.lastNumber % 2 == 0) {
                // o.println("W even");
                this.wait();
            }
            // o.println("P even");
            printNumber.accept(++lastNumber);
            lastIsZero = false;
            this.notifyAll();
        }
        // o.println("D even");
    }

    public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
        // o.println("BS odd");
        var max = n % 2 == 1 ? n : n - 1;
        while (lastNumber < max) {
            while (!lastIsZero || this.lastNumber % 2 == 1) {
                // o.println("W odd");
                this.wait();
            }
            // o.println("P odd");
            printNumber.accept(++lastNumber);
            lastIsZero = false;
            this.notifyAll();
        }
        // o.println("D odd");
    }

    private interface Iterruptable {
        public void run() throws InterruptedException;
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
        final ZeroEvenOdd x = new ZeroEvenOdd(201);
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