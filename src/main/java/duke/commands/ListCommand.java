package duke.commands;

import duke.exceptions.DukeException;
import duke.tasks.TaskList;
import duke.Storage;
import duke.Ui;

public class ListCommand extends Command {
    private static final String LIST_PRE_MESSAGE_FORMAT = "Here are the tasks in your list";
    private static final String LIST_MESSAGE_FORMAT =  "%d. %s";

    /**
     * Lists out all tasks and their corresponding information.
     *
     * @param taskList the taskList to act on
     * @param ui the provided Ui to output on
     * @param storage the provided filename to update data to
     * @throws DukeException if any RunTimeExceptions are caught
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ui.showOutput(LIST_PRE_MESSAGE_FORMAT);
        for (int i = 0; i<taskList.size(); i++) {
            String taskInfo = taskList.get(i).toString();
            String formattedTaskInfo = String.format(LIST_MESSAGE_FORMAT, i+1, taskInfo);
            ui.showOutput(formattedTaskInfo);
        }
    }
}
