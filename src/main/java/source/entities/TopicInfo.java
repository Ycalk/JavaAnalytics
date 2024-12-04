package source.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "topics_info")
public class TopicInfo {

    @Id
    @Column(name = "t_name", nullable = false)
    private String name;

    @Column(name = "activities_max", nullable = false)
    private int activitiesMax;

    @Column(name = "exercises_max", nullable = false)
    private int exercisesMax;

    @Column(name = "homework_max", nullable = false)
    private int homeworkMax;

    @Column(name = "seminar_max", nullable = false)
    private int seminarMax;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivitiesMax() {
        return activitiesMax;
    }

    public void setActivitiesMax(int activitiesMax) {
        this.activitiesMax = activitiesMax;
    }

    public int getExercisesMax() {
        return exercisesMax;
    }

    public void setExercisesMax(int exercisesMax) {
        this.exercisesMax = exercisesMax;
    }

    public int getHomeworkMax() {
        return homeworkMax;
    }

    public void setHomeworkMax(int homeworkMax) {
        this.homeworkMax = homeworkMax;
    }

    public int getSeminarMax() {
        return seminarMax;
    }

    public void setSeminarMax(int seminarMax) {
        this.seminarMax = seminarMax;
    }
}
