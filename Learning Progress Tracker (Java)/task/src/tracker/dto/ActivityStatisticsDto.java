package tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ActivityStatisticsDto {
    private List<String> highestActivityCourses = new ArrayList<>();
    private List<String> lowestActivityCourses = new ArrayList<>();

    private int highestSubmissionCount;
    private int lowestSubmissionCount;
}
