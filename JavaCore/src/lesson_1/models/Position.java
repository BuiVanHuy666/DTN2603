package lesson_1.models;

import lesson_1.enums.PositionName;

public class Position {
    private int id;
    private PositionName name;

    public Position(int i, PositionName positionName) {
        this.id = i;
        this.name = positionName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PositionName getName() {
        return name;
    }

    public void setName(PositionName name) {
        this.name = name;
    }
}
