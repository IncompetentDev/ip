package duke;

import duke.commands.ByeCommand;
import duke.commands.Command;
import duke.commands.DeadlineCommand;
import duke.commands.DeleteCommand;
import duke.commands.EventCommand;
import duke.commands.FindCommand;
import duke.commands.ListCommand;
import duke.commands.MarkCommand;
import duke.commands.TodoCommand;
import duke.commands.UnmarkCommand;
import duke.exceptions.DukeException;
import duke.exceptions.EmptyCommandException;
import duke.exceptions.IncorrectFileFormatException;
import duke.exceptions.InvalidCommandException;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.TaskType;
import duke.tasks.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * A collection of static methods to parse various user inputs.
 */
public class Parser {

    // Used to extract initial command
    private static final Pattern COMMAND_FORMAT = Pattern.compile("(\\S+)(.*)");

    //Used to parse DateTime.
    private static final Pattern ATDATETIME_FORMAT = Pattern.compile("(\\d+\\/\\d+\\/\\d+) (\\d{4})-(\\d{4})");
    private static final String DATETIME_FORMAT = "d/M/y HHmm";

    /**
     * Parses a user given DateTime String into a DateTime format suitable for a Deadline task
     *
     * @param byDateTime the user input representing the by date/time (for Deadline)
     * @return date/time representing the deadline for a task
     * @throws DateTimeParseException if the given user input does not match the date format in DATETIME_FORMAT
     */
    public static LocalDateTime parseByDateTime(String byDateTime) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        LocalDateTime date = LocalDateTime.parse(byDateTime, formatter);
        return date;
    }

    /**
     * Parses a user given DateTime String into a DateTime format suitable for an Event object
     *
     * @param atDateTime the user input representing the at date/time (for Event)
     * @return the start and end of an Event in an array, located in index 0 and 1 respectively.
     * @throws DateTimeParseException if the given user input does not match the date format in DATETIME_FORMAT
     * @throws IllegalStateException if the given user input does not match the regex format in ATDATETIME_FORMAT
     */
    public static LocalDateTime[] parseAtDateTime(String atDateTime) throws DateTimeParseException, IllegalStateException {
        Matcher dateMatcher = ATDATETIME_FORMAT.matcher(atDateTime);
        dateMatcher.matches();

        String atDateString = dateMatcher.group(1);
        String atTimeStartString = dateMatcher.group(2);
        String atTimeEndString = dateMatcher.group(3);

        String atDateTimeStartString = atDateString + " " + atTimeStartString;
        String atDateTimeEndString = atDateString + " " + atTimeEndString;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        LocalDateTime atDateTimeStart = LocalDateTime.parse(atDateTimeStartString, formatter);
        LocalDateTime atDateTimeEnd = LocalDateTime.parse(atDateTimeEndString, formatter);

        LocalDateTime[] atDateTimes = {atDateTimeStart, atDateTimeEnd};
        return atDateTimes;
    }

    /**
     * Parses a line representing a Task from the data file
     *
     * @param dataLine a line of data from the data file
     * @return the Task parsed from the data file, or null if invalid
     */
    public static Task parseDataLine(String dataLine) throws IncorrectFileFormatException {
        // Data Format: <task type> | <0/1 representing unmarked/marked> | <description> | <date if applicable>
        String[] splitData = dataLine.split(" \\| ");
        Task taskToAdd = createTask(splitData);
        return taskToAdd;
    }

    /**
     * Parses the user command and returns the proper Command object.
     *
     * @param userInput input from user
     */
    public static Command parse(String userInput) throws EmptyCommandException, InvalidCommandException {
        // Initiate search, while checking if command is empty
        Matcher commandMatcher = COMMAND_FORMAT.matcher(userInput);
        checkForCommand(commandMatcher);
        // Create the appropriate Command object. Pass in unparsed arguments, will be handled by individual Command classes.
        String inputCommand = commandMatcher.group(1);
        String inputArguments = commandMatcher.group(2).strip();
        return createCommand(inputCommand, inputArguments);
    }

    /**
     * Checks if the userInput in CommandMatcher matches the regex command format
     *
     * @param commandMatcher containing the user input and pattern regex to validate against
     * @throws EmptyCommandException if command is empty
     */
    private static void checkForCommand(Matcher commandMatcher) throws EmptyCommandException {
        if (!commandMatcher.matches()) {
            throw new EmptyCommandException();
        }
    }

    /**
     * Creates a Command object given an inputCommand and parses inputArguments where necessary
     *
     * @param inputCommand user given command
     * @param inputArguments user given arguments
     * @return the user input Command
     * @throws InvalidCommandException if none of the commands are valid
     */
    private static Command createCommand(String inputCommand, String inputArguments) throws InvalidCommandException {
        HashMap<String, String> parsedArguments;
        switch(inputCommand) {
        case "bye":
            return new ByeCommand();
        case "list":
            return new ListCommand();
        case "mark":
            parsedArguments = argumentParser(inputArguments);
            return new MarkCommand(parsedArguments);
        case "unmark":
            parsedArguments = argumentParser(inputArguments);
            return new UnmarkCommand(parsedArguments);
        case "todo":
            parsedArguments = argumentParser(inputArguments);
            return new TodoCommand(parsedArguments);
        case "deadline":
            parsedArguments = argumentParser(inputArguments);
            return new DeadlineCommand(parsedArguments);
        case "event":
            parsedArguments = argumentParser(inputArguments);
            return new EventCommand(parsedArguments);
        case "delete":
            parsedArguments = argumentParser(inputArguments);
            return new DeleteCommand(parsedArguments);
        case "find":
            parsedArguments = argumentParser(inputArguments);
            return new FindCommand(parsedArguments);
        default:
            throw new InvalidCommandException(inputCommand);
        }
    }

    /**
     * Parses user input arguments into a dictionary form containing named parameters
     *
     * @param inputArguments the unparsed user input
     * @return HashMap<String, String> mapping parameter name to the parameter value
     */
    private static HashMap<String, String> argumentParser(String inputArguments) {
        HashMap<String, String> parsedArguments = new HashMap<String, String>();
        String parameterName = "";
        String parameter = "";

        String[] argumentArray = inputArguments.split(" ", 0);
        for (String argument: argumentArray) {
            if (argument.matches("^\\/\\w+$")) {
                parameter = parameter.strip();
                parsedArguments.put(parameterName, parameter);
                parameterName = argument;
                parameter = "";
                continue;
            }
            parameter += " "+argument;
        }
        parsedArguments.put(parameterName, parameter.strip());
        return parsedArguments;
    }

    /**
     * Creates a Task based on a split String representing the line from the data file.
     * The string is split based on the delimiters present in the data file.
     *
     * @param splitData representing the line from the data file separated by a predefined delimiter
     * @return a Task if the data given is valid, null otherwise.
     * @throws IncorrectFileFormatException if the data does not conform to the standard
     */
    private static Task createTask(String[] splitData) throws IncorrectFileFormatException {
        TaskType taskType = TaskType.fromString(splitData[0]);
        boolean isDone = splitData[1].equals("1");
        Task taskToCreate;
        String description;
        switch (taskType) {
        case Todo:
            description = splitData[2];
            taskToCreate = new Todo(description);
            taskToCreate.setIsDone(isDone);
            break;
        case Event:
            description = splitData[2];
            LocalDateTime[] atDateTimes = Parser.parseAtDateTime(splitData[3]);
            LocalDateTime atDateTimeStart = atDateTimes[0];
            LocalDateTime atDateTimeEnd = atDateTimes[1];
            taskToCreate = new Event(description, atDateTimeStart, atDateTimeEnd);
            taskToCreate.setIsDone(isDone);
            break;
        case Deadline:
            description = splitData[2];
            LocalDateTime byDateTime = Parser.parseByDateTime(splitData[3]);
            taskToCreate = new Deadline(description, byDateTime);
            taskToCreate.setIsDone(isDone);
            break;
        default:
            throw new IncorrectFileFormatException();
        }
        return taskToCreate;
    }
}