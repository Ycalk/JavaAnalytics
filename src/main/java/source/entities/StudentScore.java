package source.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "student_scores")
@IdClass(StudentScoreId.class)
public class StudentScore {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "topic_name", referencedColumnName = "name", nullable = false)
    private TopicInfo topic;

    @Column(name = "activities", nullable = false)
    private int activities;

    @Column(name = "exercises", nullable = false)
    private int exercises;

    @Column(name = "homework", nullable = false)
    private int homework;

    @Column(name = "seminar", nullable = false)
    private int seminar;

    // Getters and setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TopicInfo getTopic() {
        return topic;
    }

    public void setTopic(TopicInfo topic) {
        this.topic = topic;
    }

    public int getActivities() {
        return activities;
    }

    public void setActivities(int activities) {
        this.activities = activities;
    }

    public int getExercises() {
        return exercises;
    }

    public void setExercises(int exercises) {
        this.exercises = exercises;
    }

    public int getHomework() {
        return homework;
    }

    public void setHomework(int homework) {
        this.homework = homework;
    }

    public int getSeminar() {
        return seminar;
    }

    public void setSeminar(int seminar) {
        this.seminar = seminar;
    }
}