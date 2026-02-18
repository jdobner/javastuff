import java.util.function.IntConsumer;


class ZeroEvenOdd {
    private final int n;
    private boolean lastIsZero = false;
    private int last = 0;
    private final int maxEven;  
    private final int maxOdd; 
    private final java.io.PrintStream o = System.out;
    
    public ZeroEvenOdd(int n) {
        this.n = n;
        this.maxOdd = n % 2 == 1 ? n : n - 1;
        this.maxEven = n % 2 == 0 ? n : n - 1;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {                
        o.println("BS zero");
        synchronized(this) {
            while (last < n) {
                while (lastIsZero) {
                    o.println("W zero");
                    this.wait();
                }
                // if (last == n) {
                //     this.notifyAll();
                //     return;
                // }
                if(last < n) {
                    o.println("P zero");
                    printNumber.accept(0);
                    lastIsZero = true;
                    this.notifyAll();
                }
            }
            o.println("D zero");
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {                
        o.println("BS even");
        synchronized(this) {
            while (last < maxEven) {
                while (!lastIsZero || this.last % 2 == 0) {
                    o.println("W even");
                    this.wait();
                }
                // if (last == n) {
                //     this.notifyAll();
                //     return;
                // }
                o.println("P even");
                printNumber.accept(++last);
                lastIsZero = false;
                this.notifyAll();
            }
            o.println("D even");
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        o.println("BS odd");
        synchronized(this) {
            while (last < maxOdd) {
                while (!lastIsZero || this.last % 2 == 1) {
                    o.println("W odd");
                    this.wait();
                }
                o.println("P odd");
                printNumber.accept(++last);
                lastIsZero = false;
                this.notifyAll();
            }
            o.println("D odd");
        }
    }

    
    
    public static void main(String[] args) {
        ZeroEvenOdd x = new ZeroEvenOdd(7);
        
        var p = new IntConsumer() {
            StringBuilder sb = new StringBuilder();
            @Override
            public void accept(int value) {
                sb.append(value);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    x.zero(p);
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(p.sb);
            }       
       }).start();

       new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    x.odd(p);
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(p.sb);
            }       
       }).start();

       new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    x.even(p);
                } catch(Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(p.sb);
            }       
       }).start();

    }
}