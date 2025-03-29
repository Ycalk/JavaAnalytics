package repos;

import entities.Student;
import source.Region;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentsDataAnalytics {

    private final List<Student> data;

    public StudentsDataAnalytics(StudentsData data){
        this.data = data.getStudents();
    }

    public Map<String, Integer> getStudentsByRegion(){
        return groupStudents(GroupStudentsField.REGION);
    }

    public Map<String, Integer> getStudentsByCity(){
        return groupStudents(GroupStudentsField.CITY);
    }

    public Map<Region, Double> getAverageScoreByRegion(){
        return data.stream()
                .filter(student -> student.region() != null)
                .collect(Collectors.groupingBy(Student::region,
                        Collectors.averagingDouble(student -> student.totalPoints().getPoints())));

    }

    public Map<String, Double> getAverageScoreByMonth(){
        return data.stream()
                .filter(student -> student.bDate() != null)
                .collect(Collectors.groupingBy(student -> getMonthName(student.bDate()),
                        Collectors.averagingDouble(student -> student.totalPoints().getPoints())));
    }

    public Map<String, Integer> getMaximalScoreByMonth (){
        return data.stream()
                .filter(student -> student.bDate() != null)
                .collect(Collectors.groupingBy(
                        student -> getMonthName(student.bDate()),
                        Collectors.mapping(
                                student -> student.totalPoints().getPoints(),
                                Collectors.maxBy(Integer::compare)
                        )
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().orElse(0)
                ));
    }

    private static String getMonthName(String dateString) {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);

        try {
            var date = LocalDate.parse(dateString, formatter);
            return date.getMonth().name();
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "January";
        }
    }

    private Map<String, Integer> groupStudents(GroupStudentsField groupField){
        var studentsWithField = data.stream()
                .filter(student -> {
                    if (groupField == GroupStudentsField.REGION){
                        return student.region() != null;
                    } else {
                        return student.city() != null;
                    }
                }).toList();
        var minStudentsForField = (int) Math.ceil(studentsWithField.size() * 0.015);
        var studentCountByField = studentsWithField.stream()
                .collect(Collectors.groupingBy(student -> {
                        if (groupField == GroupStudentsField.REGION){
                            return student.region().name;
                        } else {
                            return student.city();
                        }
                    },Collectors.summingInt(_ -> 1)));
        var result = new HashMap<String, Integer>();
        for (var entry : studentCountByField.entrySet()){
            if (entry.getValue() >= minStudentsForField) {
                result.put(entry.getKey(), entry.getValue());
            } else {
                result.put("Другой", result.getOrDefault("Другой", 0) + 1);
            }
        }
        return result;
    }
}

enum GroupStudentsField{
    REGION,
    CITY
}
