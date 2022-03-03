package duke.tasks;

public enum TaskType {
    Todo("T"),
    Deadline("D"),
    Event("E");

    private String tag;

    /**
     * Give each TaskType enum an associated tag
     *
     * @param tag single char String, representing tag of TaskType
     */
    TaskType(String tag) {
        this.tag = tag;
    }

    public String getAbbreviation() {
        return tag;
    }

    /**
     * Creates the proper TaskType Enum from the given tag
     *
     * @param text single char String, the tag of TaskType
     * @return the Enum representing the full name of the type of task
     */
    public static TaskType fromString(String text) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.getAbbreviation().equals(text)) {
                return taskType;
            }
        }
        throw new IllegalArgumentException();
    }
}
