package duke.commands;

import duke.Parser;
import duke.exceptions.DukeException;
import duke.exceptions.InvalidArgumentException;
import java.time.format.DateTimeParseException;

import duke.tasks.Event;
import duke.tasks.TaskList;
import duke.Ui;
import duke.Storage;

import java.time.LocalDateTime;

import java.util.HashMap;

public class EventCommand extends Command {
    private static final String TASK_ADDED_MESSAGE_FORMAT = "added: %s";
    private static final String COMMAND_NAME = "deadline";
    private static final String EMPTY_ARGUMENTS = "Event must have a description!";
    private static final String EMPTY_ATDATE = "Event must have a date for /at!";
    private static final String INCORRECT_ATDATE = "The date and time entered for /at must be a valid date time!";

    private HashMap<String, String> arguments;

    /**
     * Initialises the arguments input by the user.
     *
     * @param parsedArguments parsed arguments representing a mapping of named arguments to the respective actual argument
     */
    public EventCommand(HashMap<String, String> parsedArguments) {
        this.arguments = parsedArguments;
    }

    /**
     * Checks if the user has input arguments (not necessarily valid) for the Event's description and date and time.
     * Keys: ("", "/at")
     *
     * @throws InvalidArgumentException if the values associated with the keys are empty or null
     */
    @Override
    protected void checkArguments() throws InvalidArgumentException {
        String errorMsg = "";
        String description = arguments.get("");
        String byDate = arguments.get("/by");
        boolean isDescriptionEmpty = (description==null || description.equals(""));
        boolean isDateEmpty = (description==null || description.equals(""));
        if (isDescriptionEmpty) {
            errorMsg += EMPTY_ARGUMENTS+"\n";
        }
        if (isDateEmpty) {
            errorMsg += EMPTY_ATDATE+"\n";
        }
        if (!errorMsg.equals("")) {
            throw new InvalidArgumentException(COMMAND_NAME,errorMsg.trim());
        }
    }

    /**
     * Creates an Event task and adds it to taskList
     *
     * @param taskList the taskList to act on
     * @param ui the provided Ui to output on
     * @param storage the provided filename to update data to
     * @throws DukeException if any RunTimeExceptions are caught due to invalid user input or IO errors
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        checkArguments();
        String description = arguments.get("");
        String atDateTime = arguments.get("/at");
        try {
            LocalDateTime[] atDateTimeParsed = Parser.parseAtDateTime(atDateTime);
            LocalDateTime atDateTimeStartParsed = atDateTimeParsed[0];
            LocalDateTime atDateTimeEndParsed = atDateTimeParsed[1];

            if (atDateTimeStartParsed.isAfter(atDateTimeEndParsed)) {
                InvalidArgumentException exception = new InvalidArgumentException(COMMAND_NAME, INCORRECT_ATDATE);
                throw exception;
            }

            Event eventTask = new Event(description, atDateTimeStartParsed, atDateTimeEndParsed);
            taskList.add(eventTask);
            String output = String.format(TASK_ADDED_MESSAGE_FORMAT, eventTask.toString());
            ui.showOutput(output);
            storage.write(taskList);
        } catch (IllegalStateException e) {
            // User given DateTime did not match the first regex validation
            InvalidArgumentException exception = new InvalidArgumentException(COMMAND_NAME, INCORRECT_ATDATE);
            throw exception;
        } catch (DateTimeParseException e) {
            // User given DateTime cannot be parsed into a valid date
            InvalidArgumentException exception = new InvalidArgumentException(COMMAND_NAME, INCORRECT_ATDATE);
            throw exception;
        }
    }
}
