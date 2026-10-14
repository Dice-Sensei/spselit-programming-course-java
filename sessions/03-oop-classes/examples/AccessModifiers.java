class Counter {
    private int count;

    private void log() {
        System.out.println("count is now " + count);
    }

    public void increment() {
        count++;
        log();
    }

    public int getCount() {
        return count;
    }
}

public class AccessModifiers {
    public static void main(String[] args) {
        Counter c = new Counter();
        c.increment();
        c.increment();
        System.out.println(c.getCount());
        // c.count = 10;   // would not compile: count is private
        // c.log();        // would not compile: log() is private
    }
}
