package duke.exceptions;

/**
 * Exception that is thrown for any FileIOExceptions while writing to the data file (in Storage)
 */
public class FileWriteException extends DukeException {
    private static final String FILE_IO_ERR_MESSAGE = "Error writing to files while %s.";

    public FileWriteException(String action) {
        super(String.format(FILE_IO_ERR_MESSAGE, action));
    }
}
