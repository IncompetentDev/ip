package duke.commands;

import duke.exceptions.DukeException;
import duke.tasks.TaskList;
import duke.Ui;
import duke.Storage;

import duke.exceptions.InvalidArgumentException;
import duke.tasks.Todo;

import java.util.HashMap;

public class TodoCommand extends Command {
    private static final String TASK_ADDED_MESSAGE_FORMAT = "added: %s";
    private static final String COMMAND_NAME = "todo";
    private static final String EMPTY_ARGUMENTS = "Todo must have a description!";

    private HashMap<String, String> arguments;
    /**
     * Initialises the arguments input by the user for this class.
     * @param parsedArguments arguments from Parser
     */
    public TodoCommand(HashMap<String, String> parsedArguments) {
        this.arguments = parsedArguments;
    }

    /**
     * Checks if the user has input arguments (not necessarily valid) for the Todo's description.
     * Keys: ("")
     *
     * @throws InvalidArgumentException if the values associated with the keys are empty or null
     */
    @Override
    protected void checkArguments() throws InvalidArgumentException {
        if (arguments.get("").equals("")) {
            throw new InvalidArgumentException(COMMAND_NAME, EMPTY_ARGUMENTS);
        }
    }

    /**
     * Creates a Todo task and adds it to taskList
     *
     * @param taskList the taskList to act on
     * @param ui the provided Ui to output on
     * @param storage the provided filename to update data to
     * @throws DukeException if any RunTimeExceptions are caught due to invalid user input or IO errors
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        checkArguments();
        String description = arguments.get("");
        Todo todoTask = new Todo(description);
        taskList.add(todoTask);
        String output = String.format(TASK_ADDED_MESSAGE_FORMAT, todoTask.toString());
        ui.showOutput(output);
        storage.write(taskList);
    }
}
