package com.htc.eleven.autotest;

/**
 * Created by eleven on 17-9-19.
 */

public abstract class Condition {
    private String id;
    private int index;
    private String description;

    public Condition(String id, int index, String description) {
        setId(id);
        setIndex(index);
        setDescription(description);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
