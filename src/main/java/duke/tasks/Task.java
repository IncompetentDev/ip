package duke.tasks;

import java.util.Queue;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected char tag;

    protected static final String TOSTRING_FORMAT_STRING = "[%c][%c] %s";

    /**
     * Returns a String representing the description for the Task.
     *
     * @return description of Task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a character that appropriately represents the state of Task completion
     *
     * @return char 'X' if done, ' ' if not done.
     */
    public char getIsDone() {
        return (this.isDone ? 'X' : ' ');
    }

    /**
     * Sets a boolean value for isDone
     *
     * @param isDone boolean value representing completion of Task
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Return the tag assigned to the class.
     *
     * @return tag character of task type
     */
    public char getTag() {
        return this.tag;
    }

    @Override
    public String toString() {
        return String.format(Task.TOSTRING_FORMAT_STRING, this.getTag(), this.getIsDone(), this.getDescription());
    }

    /**
     * Writes, in order, the data of a Task to be written to the data file
     *
     * @param infoToWrite the FIFO Queue which will be written to the data file.
     */
    public void toDataFile(Queue<String> infoToWrite) {
        infoToWrite.add(String.valueOf(this.getTag()));
        infoToWrite.add(this.isDone ? "1" : "0");
        infoToWrite.add(this.getDescription());
    }
}