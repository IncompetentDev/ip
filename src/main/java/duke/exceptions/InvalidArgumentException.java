package duke.exceptions;

public class InvalidArgumentException extends DukeException {

    private static final String INVALID_COMMAND_MESSAGE = "☹ OOPS!!! The command entered for %s ran into an error. The error: \n%s";

    /**
     * Exception to represent inappropriate arguments to the respective commands
     * e.g. indexoutofbounds for mark 5 when task 5 does not exist
     * e.g. missing arguments for description or date for a Task
     *
     * @param inputCommand the user input command
     * @param errorMessage the error message to show
     */
    public InvalidArgumentException(String inputCommand, String errorMessage) {
        super(String.format(INVALID_COMMAND_MESSAGE, inputCommand, errorMessage));
    }
}
