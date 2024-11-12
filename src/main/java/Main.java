import repos.Parser;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args){
        System.setOut(new PrintStream(
                new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
        var parser = new Parser("basicProgramming.csv", "Basic Programming");
        parser.parseData();
        var data = parser.getData();
    }
}
