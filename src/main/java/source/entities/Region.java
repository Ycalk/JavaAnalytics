package source.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "regions")
public class Region {

    @Id
    @Column(name = "region_name", nullable = false)
    private String name;

    @Column(name = "rating", nullable = false)
    private float rating;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
