package tracker.model;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum Course {
    JAVA("Java", 600),
    DSA("DSA", 400),
    DATABASES("Databases", 480),
    SPRING("Spring", 550);
    private final String name;
    private final int requiredPoints;
}
