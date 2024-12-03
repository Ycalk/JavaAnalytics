package entities;
import source.Region;

import java.util.List;

public record Student(String name, String group,
                      Region region, String city, Points totalPoints,
                      List<Topic> coveredTopics, String bDate) {
}
