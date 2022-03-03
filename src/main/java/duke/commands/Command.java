package duke.commands;

import duke.exceptions.DukeException;
import duke.tasks.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exceptions.InvalidArgumentException;

/**
 * An abstract class representing a Command. Commands should necessarily implement execute(). They may implement checkEmpty() to validate the required named arguments.
 * Commands may also implement their own private behaviours to ease readability.
 */
public abstract class Command {
    protected boolean isExit;

    /**
     * Classes should override and implement this as needed, namely those that requires additional user arguments
     * Should use this method to preprocess any arguments or check for presence of named arguments
     *
     * @throws InvalidArgumentException any exception that may occur while parsing
     */
    protected void checkArguments() throws InvalidArgumentException {

    }

    /**
     * A command must execute an action.
     *
     * @param taskList the taskList to act on
     * @param ui the provided Ui to output on
     * @param storage the provided filename to update data to
     * @throws DukeException if any error occurs while running the Command
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;

    public boolean isExit() {
        return this.isExit;
    }
}
