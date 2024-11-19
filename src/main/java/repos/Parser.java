package repos;

import entities.Points;
import entities.Topic;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

enum PointsType {
    ACTIVITIES, EXERCISES, HOMEWORK, SEMINAR
}

public class Parser {
    public final String fileName;
    private final StudentsData data;

    public Parser(String fileName, String courseName){
        this.fileName = fileName;
        this.data = new StudentsData(courseName);
    }

    public StudentsData getData(){
        return data;
    }

    private static PointsType convert(String str){
        return switch (str) {
            case "Акт" -> PointsType.ACTIVITIES;
            case "Упр" -> PointsType.EXERCISES;
            case "ДЗ" -> PointsType.HOMEWORK;
            case "Сем" -> PointsType.SEMINAR;
            default -> null;
        };
    }

    public void parseData(){
        var data = readFile();
        var topics = getTopics(data);
        topics = topics.stream().skip(1).toList();
        for (var i = 3; i < data.size(); i++){
            addStudent(data.get(i), topics);
        }
    }

    private List<String[]> readFile() {
        var result = new ArrayList<String[]>();
        try {
            for (var line : Files.readAllLines(Path.of(Objects.requireNonNull(
                    Objects.requireNonNull(getClass()
                            .getClassLoader()
                            .getResource(fileName))
                            .toURI())),
                    StandardCharsets.UTF_8)) {
                result.add(line.split(";"));
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static List<TopicParseHelper> getTopics(List<String[]> data){
        var result = new ArrayList<TopicParseHelper>();
        var topics = data.getFirst();
        var topicsPoints = data.get(1);
        var topicsMaxPoints = data.get(2);
        for (var i = 0; i < topics.length; i++){
            var currentTopic = topics[i];
            if (currentTopic.startsWith("\uFEFF")) {
                currentTopic = currentTopic.substring(1);
            }
            if (currentTopic.isEmpty()) {
                continue;
            }
            var newTopic = new TopicParseHelper(currentTopic);
            result.add(newTopic);
            for (var j = i; j < topics.length; j++){
                if (j != i && (!topics[j].isEmpty() || !topics[j].isBlank())){
                    break;
                }
                var type = convert(topicsPoints[j]);
                if (type != null){
                    newTopic.addPointsIndex(type, j, Integer.parseInt(topicsMaxPoints[j]));
                }
            }
        }
        return result;
    }

    private void addStudent(String[] studentData,
                            List<TopicParseHelper> topics) {
        var coveredTopics = new ArrayList<Topic>();

        for (var topic : topics) {
            var points = getPointsForTopic(studentData, topic);
            var maxPoints = topic.getMaxPoints();

            coveredTopics.add(new Topic(topic.name, points, maxPoints));
        }

        data.createStudent(studentData[0], studentData[1], coveredTopics);
    }

    private Points getPointsForTopic(String[] studentData, TopicParseHelper topic) {
        var activities = getPointValue(studentData, topic, PointsType.ACTIVITIES);
        var exercises = getPointValue(studentData, topic, PointsType.EXERCISES);
        var homework = getPointValue(studentData, topic, PointsType.HOMEWORK);
        var seminar = getPointValue(studentData, topic, PointsType.SEMINAR);

        return new Points(activities, exercises, homework, seminar);
    }

    private int getPointValue(String[] studentData, TopicParseHelper topic, PointsType type) {
        if (topic.getPointsTypes().contains(type)) {
            return Integer.parseInt(studentData[topic.getPointIndex(type)]);
        }
        return 0;
    }
}

class TopicParseHelper {
    public final String name;
    private final Map<PointsType, Integer> pointsIndexes = new HashMap<>();
    private final Map<PointsType, Integer> maxPoints = new HashMap<>();

    public TopicParseHelper(String name){
        this.name = name;
    }

    public void addPointsIndex(PointsType type, int index, int maxPoints){
        pointsIndexes.put(type, index);
        this.maxPoints.put(type, maxPoints);
    }

    public List<PointsType> getPointsTypes(){
        return new ArrayList<>(pointsIndexes.keySet());
    }

    public int getPointIndex(PointsType type){
        return pointsIndexes.get(type);
    }

    public Points getMaxPoints(){
        return new Points(
                getMaxPoints(PointsType.ACTIVITIES),
                getMaxPoints(PointsType.EXERCISES),
                getMaxPoints(PointsType.HOMEWORK),
                getMaxPoints(PointsType.SEMINAR)
        );
    }

    private int getMaxPoints(PointsType type){
        if (!maxPoints.containsKey(type)){
            return 0;
        }
        return maxPoints.get(type);
    }
}
