package source.entities;

import java.io.Serializable;
import java.util.Objects;

public class StudentScoreId implements Serializable {

    private int student;
    private String topic;

    public StudentScoreId() {}

    public StudentScoreId(int student, String topic) {
        this.student = student;
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentScoreId that = (StudentScoreId) o;
        return student == that.student && Objects.equals(topic, that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, topic);
    }
}
