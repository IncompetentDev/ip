package duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;

public class Event extends Task {

    private static final String EVENT_TOSTRING_FORMAT_STRING = "[%c][%c] %s (at: %s)";
    private static final String DATE_FORMAT = "d/M/y";
    private static final String TIME_FORMAT = "HHmm";
    private static final String AT_DATE_STRING_FORMAT = "%s %s-%s";

    protected LocalDateTime atDateTimeStart;
    protected LocalDateTime atDateTimeEnd;

    /**
     * Creates an Event task with a specified description, a specified date and time representing when the event starts and end, and sets Event task as not done
     * It is assumed that events will have the same start and end date, only the time will differ.
     *
     * @param description description of the Task to be created
     * @param atDateTimeStart Date and time of when event starts
     * @param atDateTimeEnd Date and time of when event ends
     */
    public Event(String description, LocalDateTime atDateTimeStart, LocalDateTime atDateTimeEnd) {
        this.description = description;
        this.isDone = false;
        this.atDateTimeStart = atDateTimeStart;
        this.atDateTimeEnd = atDateTimeEnd;
        this.tag = 'E';
    }

    @Override
    public String toString() {
        return String.format(EVENT_TOSTRING_FORMAT_STRING, this.getTag(), this.getIsDone(), this.getDescription(), this.getAtDateTimeAsString());
    }

    /**
     * Writes, in order, the data of this Deadline to be written to the data file
     * Calls the base Task's toDataFile while adding the atDateTime information for this Event task.
     *
     * @param infoToWrite the FIFO queue which will be written to the data file.
     */
    @Override
    public void toDataFile(Queue<String> infoToWrite) {
        super.toDataFile(infoToWrite);
        infoToWrite.add(getAtDateTimeAsString());
    }

    /**
     * Returns a string representation of this Event's start and end DateTime.
     *
     * @return a String representing the start and end DateTime of this event, into a format similar to how the user would input the DateTime.
     */
    public String getAtDateTimeAsString() {
        DateTimeFormatter atDateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
        DateTimeFormatter atTimeFormat = DateTimeFormatter.ofPattern(TIME_FORMAT);
        String getAtDateString = this.atDateTimeStart.format(atDateFormat);
        String getAtTimeStartString = this.atDateTimeStart.format(atTimeFormat);
        String getAtTimeEndString = this.atDateTimeEnd.format(atTimeFormat);
        String getAtDateTimeString = String.format(AT_DATE_STRING_FORMAT, getAtDateString, getAtTimeStartString, getAtTimeEndString);
        return getAtDateTimeString;
    }
}
