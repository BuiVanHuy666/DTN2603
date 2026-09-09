package lesson_1.models;

import lesson_1.enums.PositionName;
import lombok.Getter;
import lombok.Setter;

public class Position {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private PositionName name;

    public Position(int i, PositionName positionName) {
        this.id = i;
        this.name = positionName;
    }
}
