package duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;

public class Deadline extends Task {

    private static final String DEADLINE_TOSTRING_FORMAT_STRING = "[%c][%c] %s (by: %s)";
    private static final String BY_DATETIME_FORMAT = "d/M/y HHmm";

    protected LocalDateTime byDateTime;

    /**
     * Creates a Deadline task with a specified description, a specified date and time representing when the task is due, and sets the Deadline task as not done
     *
     * @param description description of the Task to be created
     * @param byDateTime date and time of the deadline
     */
    public Deadline(String description, LocalDateTime byDateTime) {
        this.description = description;
        this.isDone = false;
        this.byDateTime = byDateTime;
        this.tag = 'D';
    }

    /**
     * Returns the byDateTime associated with deadline
     *
     * @return a date and time representing the byDateTime of this Deadline task
     */
    public LocalDateTime getByDateTime() {
        return this.byDateTime;
    }

    @Override
    public String toString() {
        return String.format(DEADLINE_TOSTRING_FORMAT_STRING, this.getTag(), this.getIsDone(), this.getDescription(), this.getByDateTimeAsString());
    }

    /**
     * Writes, in order, the data of this Deadline to be written to the data file
     * Calls the base Task's toDataFile method while adding the byDateTime information for this Deadline task.
     *
     * @param infoToWrite the FIFO queue which will be written to the data file.
     */
    @Override
    public void toDataFile(Queue<String> infoToWrite) {
        super.toDataFile(infoToWrite);
        infoToWrite.add(getByDateTimeAsString());
    }

    /**
     * Returns the date and time of deadline
     *
     * @return the date and time of this deadline in the same format as the recognised user input for date time
     */
    public String getByDateTimeAsString() {
        DateTimeFormatter byDateTimeFormat = DateTimeFormatter.ofPattern(BY_DATETIME_FORMAT);
        String getByDateTimeString = this.byDateTime.format(byDateTimeFormat);
        return getByDateTimeString;
    }
}
