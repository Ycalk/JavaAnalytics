package repos;

public class Parser {
    public final String fileName;
    private final StudentsData data;

    Parser(String fileName, String courseName){
        this.fileName = fileName;
        this.data = new StudentsData(courseName);
    }

    public void parseData(){
        throw new UnsupportedOperationException("Not implemented");
    }
}
