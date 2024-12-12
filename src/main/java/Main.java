import repos.Parser;
import repos.StudentsData;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    private static void init(){
        System.setOut(new PrintStream(
                new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
    }

    public static void main(String[] args){
        var data = StudentsData.fromDataBase("Basic Programming");
    }
}
