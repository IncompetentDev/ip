package duke.commands;

import duke.exceptions.DukeException;
import duke.exceptions.InvalidArgumentException;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.Storage;
import duke.Ui;

import java.util.HashMap;

public class MarkCommand extends Command {
    private static final String TASK_MARKED_MESSAGE_FORMAT =
            "Nice! I've marked this task as done:"
            + "\n%s";
    private static final String COMMAND_NAME = "mark";
    private static final String INVALID_INPUT = "The argument received is not a valid integer.";
    public static final String INVALID_TASK = "The task number given does not exist";

    private int index;
    private HashMap<String, String> arguments;

    /**
     * Initialises the arguments input by the user.
     *
     * @param parsedArguments parsed arguments representing a mapping of named arguments to the respective actual argument
     */
    public MarkCommand(HashMap<String, String> parsedArguments) {
        this.arguments = parsedArguments;
    }

    /**
     * Checks if the user arguments can be converted to integer
     * Keys: ("")
     *
     * @throws InvalidArgumentException if argument entered is not an integer
     */
    @Override
    protected void checkArguments() throws InvalidArgumentException {
        try {
            this.index = Integer.parseInt(arguments.get(""))-1;
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException(COMMAND_NAME, INVALID_INPUT);
        }
    }

    /**
     * Marks a task as done, with the task number specified by user.
     *
     * @param taskList the taskList to act on
     * @param ui the provided Ui to output on
     * @param storage the provided filename to update data to
     * @throws DukeException if any RunTimeExceptions are caught due to invalid user input or IO errors
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            checkArguments();
            Task taskToMark = taskList.get(index);
            taskToMark.setIsDone(true);
            String output = String.format(TASK_MARKED_MESSAGE_FORMAT,taskToMark.toString());
            ui.showOutput(output);
            storage.write(taskList);
        } catch (IndexOutOfBoundsException e) {
            // User specified task number does not correspond to any task.
            InvalidArgumentException exception = new InvalidArgumentException(COMMAND_NAME, INVALID_TASK);
            throw exception;
        }
    }
}
