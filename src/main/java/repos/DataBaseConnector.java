package repos;

import entities.Points;
import entities.Topic;
import source.DataBase;
import source.entities.StudentScore;
import source.entities.TopicInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseConnector {
    private static final DataBase dataBase = new DataBase();

    private DataBaseConnector(){}

    public static void saveStudent(entities.Student student){
        var region = getRegion(student);
        var topics = getTopics(student.coveredTopics());
        var studentToSave = convert(student, region);
        var scores = getScore(topics, studentToSave, student.coveredTopics());
        dataBase.saveStudent(region, studentToSave, topics, scores);
    }

    private static source.entities.Region getRegion(entities.Student student){
        var region = new source.entities.Region();
        if (student.region() == null){
            return null;
        }
        region.setName(student.region().name);
        region.setRating(student.region().rating.floatValue());
        return region;
    }

    public static List<entities.Student> getStudents(){
        var students = dataBase.getStudents();
        var result = new ArrayList<entities.Student>();
        for (var s : students){
            var coveredTopics = getCoveredTopics(s.getScores());
            var totalPoints = new Points(
                    coveredTopics.stream().mapToInt(t -> t.points().activities()).sum(),
                    coveredTopics.stream().mapToInt(t -> t.points().exercises()).sum(),
                    coveredTopics.stream().mapToInt(t -> t.points().homework()).sum(),
                    coveredTopics.stream().mapToInt(t -> t.points().seminar()).sum()
            );
            var bDate = s.getBDate() == null ? null : formatter.format(s.getBDate());
            var student = new entities.Student(s.getName(), s.getGroup(),
                    source.Region.fromSource(s.getRegion()), s.getCity(),
                    totalPoints, coveredTopics, bDate);
            result.add(student);
        }


        return result;
    }

    private static List<Topic> getCoveredTopics(List<source.entities.StudentScore> scores){
        var result = new ArrayList<Topic>();
        for (var s : scores){
            var topicInfo = s.getTopic();
            var topic = new Topic(
                    topicInfo.getName(),
                    new Points(s.getActivities(), s.getExercises(), s.getHomework(), s.getSeminar()),
                    new Points(topicInfo.getActivitiesMax(), topicInfo.getExercisesMax(), topicInfo.getHomeworkMax(), topicInfo.getSeminarMax())
            );
            result.add(topic);
        }
        return result;
    }

    private static List<TopicInfo> getTopics(List<Topic> topics){
        var result = new ArrayList<TopicInfo>();
        for (var t : topics){
            var convertedTopic = new source.entities.TopicInfo();
            convertedTopic.setName(t.name());
            convertedTopic.setActivitiesMax(t.maxPoints().activities());
            convertedTopic.setExercisesMax(t.maxPoints().exercises());
            convertedTopic.setHomeworkMax(t.maxPoints().homework());
            convertedTopic.setSeminarMax(t.maxPoints().seminar());
            result.add(convertedTopic);
        }
        return result;
    }

    public static source.entities.Student convert(
            entities.Student student,
            source.entities.Region region){
        var result = new source.entities.Student();
        result.setName(student.name());
        result.setGroup(student.group());
        result.setCity(student.city());
        result.setBDate(convertDate(student.bDate()));
        result.setRegion(region);
        return result;
    }

    public static List<StudentScore> getScore(
            List<TopicInfo> topic,
            source.entities.Student student,
            List<Topic> coveredTopics){
        var result = new ArrayList<StudentScore>();
        for (var i = 0; i < topic.size(); i++){
            var score = new source.entities.StudentScore();
            score.setStudent(student);
            score.setTopic(topic.get(i));
            score.setActivities(coveredTopics.get(i).points().activities());
            score.setExercises(coveredTopics.get(i).points().exercises());
            score.setHomework(coveredTopics.get(i).points().homework());
            score.setSeminar(coveredTopics.get(i).points().seminar());
            result.add(score);
        }
        return result;
    }

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private static Date convertDate(String bDate){
        try {
            return formatter.parse(bDate);
        } catch (Exception e){
            return null;
        }
    }
}