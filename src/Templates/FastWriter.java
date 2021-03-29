package Templates;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FastWriter {
    BufferedWriter bw;

    public FastWriter(OutputStream os) {
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    public void print(Object s) {
        try {
            bw.write(s.toString());
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public void println() {
        print("\n");
    }

    public void println(Object s) {
        print(s);
        println();
    }

    public void flush() {
        try {
            bw.flush();
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public void close() {
        try {
            bw.close();
        } catch (Exception e) {
            System.exit(1);
        }
    }
}