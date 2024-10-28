package models;

import models.externalData.Region;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentsData {
    public final String courseName;
    private final Map<String, List<Student>> groups = new HashMap<>();

    StudentsData(String courseName){
        this.courseName = courseName;
    }

    public void createStudent(String name, String group, Points points){
        var student = new Student(name, group, new Region(name), points);
        if (!groups.containsKey(group)){
            var newGroup = new ArrayList<Student>();
            newGroup.add(student);
            groups.put(group, newGroup);
        } else {
            groups.get(group).add(student);
        }
    }

    public List<Student> getStudents(String group){
        return groups.get(group);
    }

    public List<Student> getStudents(){
        var result = new ArrayList<Student>();
        for (var group : groups.keySet()){
            result.addAll(groups.get(group));
        }
        return result;
    }
}
