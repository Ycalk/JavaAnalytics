package entities;

public record Points(int activities, int exercises, int homework) {
    public int getPoints(){
        return activities + exercises + homework;
    }
}
