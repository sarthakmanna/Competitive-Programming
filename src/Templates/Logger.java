package Templates;

import java.io.PrintStream;
import java.util.Arrays;

public class Logger {
    public static final boolean isDevEnv = resolveIfDevEnv();
    public boolean noOutputPhase;
    public PrintStream stream;

    public Logger(PrintStream ps) {
        stream = ps;
    }

    public Logger() {
        this(System.err);
        stream = System.err;
    }

    public Logger(boolean noOutput) {
        this();
        noOutputPhase = isDevEnv && noOutput;
    }

    private static boolean resolveIfDevEnv() {
        try {
            return System.getenv().get("USERDOMAIN") != null
                    && System.getenv().get("USERDOMAIN").equals("LAPTOP-DSSUKMC1");
        } catch (Exception | Error e) {
            return false;
        }
    }

    public void println() {
        if (!isDevEnv) return;
        stream.println();
    }

    public void println(char... values) {
        print(values);
        println();
    }

    public void println(int... values) {
        print(values);
        println();
    }

    public void println(long... values) {
        print(values);
        println();
    }

    public void println(double... values) {
        print(values);
        println();
    }

    public void println(String... values) {
        print(values);
        println();
    }

    public void println(Object... values) {
        print(values);
        println();
    }

    public void print(Object object) {
        if (!isDevEnv) return;
        stream.print(object);
    }

    public void print(char... values) {
        if (!isDevEnv) return;
        print(Arrays.toString(values));
    }

    public void print(int... values) {
        if (!isDevEnv) return;
        print(Arrays.toString(values));
    }

    public void print(long... values) {
        if (!isDevEnv) return;
        print(Arrays.toString(values));
    }

    public void print(double... values) {
        if (!isDevEnv) return;
        print(Arrays.toString(values));
    }

    public void print(String... values) {
        if (!isDevEnv) return;
        print(Arrays.toString(values));
    }

    public void print(Object... objects) {
        if (!isDevEnv) return;
        print(Arrays.deepToString(objects));
    }

    public void flush() {
        stream.flush();
    }
}
