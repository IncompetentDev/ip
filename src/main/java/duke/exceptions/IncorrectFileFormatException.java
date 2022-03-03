package duke.exceptions;

/**
 * Exception that is thrown for any parsing errors while reading the data file (in Storage)
 */
public class IncorrectFileFormatException extends DukeException {
    private static final String INVALID_COMMAND_MESSAGE = "Data file provided has wrong format";

    public IncorrectFileFormatException() {
        super(INVALID_COMMAND_MESSAGE);
    }

}
