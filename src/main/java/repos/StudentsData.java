package repos;

import entities.Points;
import entities.Student;
import entities.Topic;
import source.Region;
import source.VkApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentsData {
    public final String courseName;
    private final Map<String, List<Student>> groups = new HashMap<>();
    private static final VkApi vkApi = new VkApi();

    StudentsData(String courseName){
        this.courseName = courseName;
    }

    public void createStudent(String name, String group, List<Topic> coveredTopics){
        var userInfo = vkApi.getUserInfo(name);
        var student = new Student(name, group, Region.getRegion(userInfo.city), userInfo.city,
                getPoints(coveredTopics), coveredTopics, userInfo.bDate);
        if (!groups.containsKey(group)){
            var newGroup = new ArrayList<Student>();
            newGroup.add(student);
            groups.put(group, newGroup);
        } else {
            groups.get(group).add(student);
        }
    }

    private Points getPoints(List<Topic> topics){
        return new Points(
                topics.stream()
                        .mapToInt(topic -> topic.points().activities())
                        .sum(),
                topics.stream()
                        .mapToInt(topic -> topic.points().exercises())
                        .sum(),
                topics.stream()
                        .mapToInt(topic -> topic.points().homework())
                        .sum(),
                topics.stream()
                        .mapToInt(topic -> topic.points().seminar())
                        .sum());
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
