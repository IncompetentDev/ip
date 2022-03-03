package duke.exceptions;

/**
 * Exception that is thrown for any FileIOExceptions while reading the data file (in Storage)
 */
public class FileReadException extends DukeException {
    private static final String FILE_READ_ERR_MESSAGE = "Data file does not exist or could not be read from.";

    public FileReadException() {
        super(FILE_READ_ERR_MESSAGE);
    }

}
