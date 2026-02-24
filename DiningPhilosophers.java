class DiningPhilosophers  {

    private static final java.io.PrintStream o = System.out;
    static {
        details();
    }

    public DiningPhilosophers() {
        
    }


    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        o.println("Philosopher: %d".formatted(philosopher));
        runIt(pickRightFork, philosopher, "pickRightFork");
        runIt(pickLeftFork, philosopher, "pickLeftFork");
        runIt(eat, philosopher, "eat");
        runIt(putRightFork, philosopher, "putRightFork");
        runIt(putLeftFork, philosopher, "putLeftFork");
    }

    private void runIt(Runnable r, int philosopher, String function) {
        o.println("%d Before %s".formatted(philosopher, function));
        r.run();
        o.println("%d After  %s".formatted(philosopher, function));
    }

    private static void details() {
        o.println("Java Version:    " + System.getProperty("java.version"));
        o.println("JVM Name:        " + System.getProperty("java.vm.name"));
        o.println("JVM Vendor:      " + System.getProperty("java.vm.vendor"));
        o.println("JVM Info:        " + System.getProperty("java.vm.info"));
        o.println("OS Architecture: " + System.getProperty("os.arch"));
        o.println();
        
    }

    public static void main(String[] args) {
    }
}