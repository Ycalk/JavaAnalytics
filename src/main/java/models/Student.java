package models;
import models.externalData.Region;

public record Student(String name, String group,
                      Region region, Points points) {
}
