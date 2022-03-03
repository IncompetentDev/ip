package duke.tasks;

public class Todo extends Task {

    /**
     * Creates a Todo task with a specified description and sets the Todo task as not done
     *
     * @param description Description of the Task to be created
     */
    public Todo(String description) {
        this.description = description;
        this.isDone = false;
        this.tag = 'T';
    }
}
