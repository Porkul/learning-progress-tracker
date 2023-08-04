package tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class PopularityStatisticsDto {
    private List<String> mostPopularCourses = new ArrayList<>();
    private List<String> leastPopularCourses = new ArrayList<>();
}
