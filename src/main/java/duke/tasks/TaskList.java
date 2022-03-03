package duke.tasks;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    /**
     * Initialises a TaskList with no elements.
     */
    public TaskList() {
        super();
    }

    /**
     * Initialises a TaskList with the appropriate Collection type.
     *
     * @param taskList the ArrayList Collection representing a list of tasks
     */
    public TaskList(ArrayList<Task> taskList) {
        super(taskList);
    }

}
