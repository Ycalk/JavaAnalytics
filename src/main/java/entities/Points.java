package entities;

public record Points(int activities, int exercises, int homework, int seminar) {
    public int getPoints(){
        return activities + exercises + homework;
    }
}
