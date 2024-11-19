import repos.Parser;
import source.Config;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    private static void init(){
        Config.init();
        System.setOut(new PrintStream(
                new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
    }

    public static void main(String[] args){
        init();
        var parser = new Parser("basicProgramming.csv", "Basic Programming");
        parser.parseData();
        var data = parser.getData();
    }
}
