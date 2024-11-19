package entities;
import source.Region;

import java.util.List;

public record Student(String name, String group,
                      Region region, Points totalPoints,
                      List<Topic> coveredTopics) {
}
